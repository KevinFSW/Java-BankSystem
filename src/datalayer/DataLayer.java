package datalayer;

import entry.DataEntry;

/**
 * 数据层，各种数据库操作的实现层，提供操作数据库的方法。 对数据库的查询，添加，修改，删除，保存，加载等
 */
public class DataLayer{

    /**
     * 操作登录相关
     */
    public static final int OPT_LOGIN = 0;

    /**
     * 操作信息相关
     */
    public static final int OPT_INFO = 1;

    private static final DataOfUserLogin dataOfUserLogin = DataOfUserLogin.getDataOfUserLoginInstance();
    private static final DataOfUserInfo dataOfUserInfo = DataOfUserInfo.getDataOfUserInfoInstance();

    private static final DataLayer dataLayer = new DataLayer();

    private DataLayer(){}

    public static DataLayer getDataLayerInstance(){
        return dataLayer;
    }

    /**
     * 初始化数据库
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return
     */
    public synchronized boolean initData(int opt){
        if(opt == DataLayer.OPT_LOGIN)
        {
            return dataOfUserLogin.initData();
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            return dataOfUserInfo.initData();
        }

        return false;
    }

    /**
     * 把数据库存入文件
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return
     */
    public synchronized boolean saveData(int opt){
        if(opt == DataLayer.OPT_LOGIN)
        {
            return dataOfUserLogin.saveData();
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            return dataOfUserInfo.saveData();
        }
        
        return false;
    }

    /**
     * 添加数据
     * @param user
     * @param data
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return boolean
     */
    public synchronized boolean add(String user, Object data, int opt) throws ClassCastException{
        if(opt == DataLayer.OPT_LOGIN)
        {
            dataOfUserLogin.add(user, (String)data);
            return true;
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            dataOfUserInfo.add(user, (DataEntry)data);
            return true;
        }

        return false;
    }

    /**
     * 修改数据
     * @param user
     * @param oldData
     * @param newData
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return true 修改成功
     */
    public synchronized boolean modify(String user, Object oldData, Object newData, int opt) throws ClassCastException{
        if(opt == DataLayer.OPT_LOGIN)
        {
            return dataOfUserLogin.modify(user, (String)oldData, (String)newData);
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            return dataOfUserInfo.modify(user, (DataEntry)oldData, (DataEntry)newData);
        }

        return false;
    }

    /**
     * 删除数据
     * @param user
     * @param data
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return true 删除成功
     */
    public synchronized boolean remove(String user, Object data, int opt) throws ClassCastException{
        if(opt == DataLayer.OPT_LOGIN)
        {
            return dataOfUserLogin.remove(user, (String)data);
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            return dataOfUserInfo.remove(user, (DataEntry)data);
        }

        return false;
    }

    /**
     * 查询用户是否存在
     * @param user
     * @param opt DataLayer.OPT_LOGIN or DataLayer.OPT_INFO
     * @return
     */
    public boolean hasUser(String user, int opt){
        if(opt == DataLayer.OPT_LOGIN)
        {
            return dataOfUserLogin.hasUser(user);
        }
        else if(opt == DataLayer.OPT_INFO)
        {
            return dataOfUserInfo.hasUser(user);
        }

        return false;
    }

    /**
     * 查询数据
     * @param user
     * @return String
     */
    public DataEntry getInfo(String user){
        return dataOfUserInfo.getInfo(user);
    }

    /**
     * 查询数据
     * @param user
     * @param passwd
     * @return boolean
     */
    public boolean confirmUser(String user, String passwd){
        return dataOfUserLogin.confirmUser(user, passwd);
    }

}
