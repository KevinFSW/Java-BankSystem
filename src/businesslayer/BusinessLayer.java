package businesslayer;

import datalayer.DataLayer;
import entry.DataEntry;

/**
 * 业务层，为应用层提供功能实现。
 * 提供校验账户名，密码，查询余额，存取款等的方法
 */
public class BusinessLayer{

    String user = null;
    String passwd = null;

    private static final BusinessLayer businessLayer = new BusinessLayer();

    private BusinessLayer(){

    }

    public static BusinessLayer getBusinessLayer(){
        return businessLayer;//单例，同一时刻只能一个人操作（类似于一台取款机只能登录一个人）
    }

    /**
     * 获取当前操作的用户
     * @return
     */
    public String getCurrentUser(){
        return this.user;
    }

    /**
     * 初始化数据库
     * @return
     */
    public boolean initData(){
        return (DataLayer.initData(DataLayer.OPT_LOGIN) && DataLayer.initData(DataLayer.OPT_INFO));
    }

    /**
     * 把数据库存入文件
     * @return
     */
    public boolean saveData(){
        return (DataLayer.saveData(DataLayer.OPT_LOGIN) && DataLayer.saveData(DataLayer.OPT_INFO));
    }

    /**
     * 用户登录
     * @param user
     * @param passwd
     * @return
     */
    public boolean userLogin(String user, String passwd){
        if(this.user == null && DataLayer.confirmUser(user, passwd)){
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
        if(user == this.user){
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
        return DataLayer.add(user, passwd, DataLayer.OPT_LOGIN);
    }

    /**
     * 注销
     * @param user
     * @param passwd
     * @return
     */
    public boolean userRemove(String user, String passwd){
        DataEntry info = DataLayer.getInfo(user);
        return (DataLayer.remove(user, passwd, DataLayer.OPT_LOGIN) & 
            DataLayer.remove(user, info, DataLayer.OPT_INFO));
    }

    /**
     * 查询余额
     * @return
     */
    public String userGetBalance(){
        if(this.user == null || !DataLayer.hasUser(this.user, DataLayer.OPT_INFO)){
            return null;
        }

        DataEntry info = DataLayer.getInfo(this.user);

        return info.toStringArray()[0];
    }

    /**
     * 系统给指定用户存款（私有）
     * @param user
     * @param money
     */
    private void addBalance(String user, String money){
        int opt = DataLayer.OPT_INFO;
        if(DataLayer.hasUser(user, opt)){
            DataEntry oldInfo = DataLayer.getInfo(user);
            String[] infoArray = oldInfo.toStringArray();
            infoArray[0] = "" + (Integer.parseInt(infoArray[0]) + Integer.parseInt(money));
            DataEntry newInfo = new DataEntry(infoArray);
            DataLayer.modify(user, oldInfo, newInfo, opt);
        }
        else{
            DataLayer.add(user, new DataEntry(money, "RMB"), opt);
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

        addBalance(this.user, money);

        return true;
    }

    /**
     * 取款
     * @param money
     * @return
     */
    public boolean userSubBalance(String money){
        String balance = this.userGetBalance();
        if(balance == null || Integer.parseInt(balance) < Integer.parseInt(money)){
            return false;
        }

        int opt = DataLayer.OPT_INFO;
        DataEntry oldInfo = DataLayer.getInfo(this.user);
        String[] infoArray = oldInfo.toStringArray();
        infoArray[0] = "" + (Integer.parseInt(balance) - Integer.parseInt(money));
        DataEntry newInfo = new DataEntry(infoArray);
        DataLayer.modify(this.user, oldInfo, newInfo, opt);
        return true;
    }

    /**
     * 转账 = 取款 + 存款
     * @param money
     * @param other
     * @return
     */
    public boolean userSubBalanceToOther(String money, String other){
        if(DataLayer.hasUser(other, DataLayer.OPT_LOGIN) && this.userSubBalance(money)){
            addBalance(other, money);
            return true;
        }

        return false;
    }


}
