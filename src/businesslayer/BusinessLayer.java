package businesslayer;

import datalayer.DataLayer;
import entry.DataEntry;

/**
 * 业务层，为应用层提供功能实现。
 * 提供校验账户名，密码，查询余额，存取款等的方法
 */
public class BusinessLayer{

    private String user = null;
    private String passwd = null;

    private static final DataLayer dataLayer = DataLayer.getDataLayerInstance();
    
    public BusinessLayer(){

    }
    
    /**
     * 获取当前操作的用户
     * @return
     */
    public String getCurrentUser(){
        return this.user;
    }

    /**
     * 确认密码（敏感操作需要重新确认密码）
     * @param passwd
     * @return
     */
    public boolean confirmPasswd(String passwd){
        if(this.passwd == null || !this.passwd.equals(passwd)){
            return false;
        }

        return true;
    }

    /**
     * 初始化数据库
     * @return
     */
    public boolean initData(){
        return (dataLayer.initData(DataLayer.OPT_LOGIN) && dataLayer.initData(DataLayer.OPT_INFO));
    }

    /**
     * 把数据库存入文件
     * @return
     */
    public boolean saveData(){
        return (dataLayer.saveData(DataLayer.OPT_LOGIN) && dataLayer.saveData(DataLayer.OPT_INFO));
    }

    /**
     * 用户登录
     * @param user
     * @param passwd
     * @return
     */
    public boolean userLogin(String user, String passwd){
        if(this.user == null && dataLayer.confirmUser(user, passwd)){
            this.user = user;
            this.passwd = passwd;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 退出登录
     * @param user
     * @return
     */
    public boolean userLogout(String user){
        if(user.equals(this.user)){
            this.user = null;
            this.passwd = null;
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * 注册
     * @param user
     * @param passwd
     * @return
     */
    public boolean userAdd(String user, String passwd){
        return dataLayer.add(user, passwd, DataLayer.OPT_LOGIN);
    }

    /**
     * 注销
     * @param user
     * @param passwd
     * @return
     */
    public boolean userRemove(String user, String passwd){
        DataEntry info = dataLayer.getInfo(user);
        return (dataLayer.remove(user, passwd, DataLayer.OPT_LOGIN) & 
            dataLayer.remove(user, info, DataLayer.OPT_INFO));
    }

    /**
     * 查询余额
     * @return
     */
    public String userGetBalance(){
        if(this.user == null || !dataLayer.hasUser(this.user, DataLayer.OPT_INFO)){
            return null;
        }

        DataEntry info = dataLayer.getInfo(this.user);

        return info.toStringArray()[0] + info.toStringArray()[1];
    }

    /**
     * 系统给指定用户存款（私有）
     * @param user
     * @param money
     */
    private boolean addBalance(String user, String money){
        int opt = DataLayer.OPT_INFO;
        if(dataLayer.hasUser(user, opt)){
            DataEntry oldInfo = dataLayer.getInfo(user);
            String[] infoArray = oldInfo.toStringArray();
            infoArray[0] = "" + (Integer.parseInt(infoArray[0]) + Integer.parseInt(money));
            DataEntry newInfo = new DataEntry(infoArray);
            return dataLayer.modify(user, oldInfo, newInfo, opt);
        }
        else{
            return dataLayer.add(user, new DataEntry(money, "RMB"), opt);
        }
    }

    /**
     * 存款
     * @param money
     * @return
     */
    public boolean userAddBalance(String money){
        if(this.user == null){
            return false;
        }

        return addBalance(this.user, money);
    }

    /**
     * 取款
     * @param money
     * @return
     */
    public boolean userSubBalance(String money){
        if(this.user == null){
            return false;
        }
        String balance = this.userGetBalance();
        if(balance == null || Integer.parseInt(balance) < Integer.parseInt(money)){
            return false;
        }

        int opt = DataLayer.OPT_INFO;
        DataEntry oldInfo = dataLayer.getInfo(this.user);
        String[] infoArray = oldInfo.toStringArray();
        infoArray[0] = "" + (Integer.parseInt(balance) - Integer.parseInt(money));
        DataEntry newInfo = new DataEntry(infoArray);
        return dataLayer.modify(this.user, oldInfo, newInfo, opt);
    }

    /**
     * 转账 = 取款 + 存款
     * @param money
     * @param other
     * @return
     */
    public boolean userSubBalanceToOther(String money, String other){
        if(this.user == null){
            return false;
        }
        if(dataLayer.hasUser(other, DataLayer.OPT_LOGIN) && this.userSubBalance(money)){
            return addBalance(other, money);
        }

        return false;
    }

    /**
     * 更新用户的全部信息
     * @param x
     * @return
     */
    public boolean updateUserInfo(String ...x){
        if(this.user == null){
            return false;
        }
        int opt = DataLayer.OPT_INFO;
        if(dataLayer.hasUser(this.user, opt)){
            DataEntry oldInfo = dataLayer.getInfo(user);
            DataEntry newInfo = new DataEntry(x);
            return dataLayer.modify(this.user, oldInfo, newInfo, opt);
        }
        else{
            return dataLayer.add(this.user, new DataEntry(x), opt);
        }
    }

    /**
     * 获取用户的全部信息
     * @param x
     * @return
     */
    public String[] getUserInfo(String user){
        if(this.user == null || !this.user.equals(user)){
            return null;
        }
        int opt = DataLayer.OPT_INFO;
        if(dataLayer.hasUser(user, opt)){
            return dataLayer.getInfo(user).toStringArray();
        }
        else{
            return null;
        }
    }


}
