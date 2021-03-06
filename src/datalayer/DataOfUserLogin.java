package datalayer;

import java.util.HashMap;
import java.util.Set;

/**
 * 用户登录，注册需要用到的数据库和接口
 */
public class DataOfUserLogin{

    /**
     * 数据库，保存用户名和密码,
     * 
     * 静态的，内存中只有一份,
     * 
     * 使用HashMap方便去重,
     * 
     * key无序无重复，value无序可重复,
     * 
     * HashMap< username, password >,
     * username是唯一的，password可以重复
     */
    private volatile static HashMap<String, String> userLoginData = null;

    private static final DataOfUserLogin dataOfUserLogin = new DataOfUserLogin();

    private DataOfUserLogin(){
        this.initData();
    }

    public static DataOfUserLogin getDataOfUserLoginInstance(){
        return dataOfUserLogin;
    }

    /**
     * 后期学完IO，在这里从文件加载初始化数据库
     * @return
     */
    public synchronized boolean initData(){
        if(userLoginData != null){
            return false;
        }

        userLoginData = Load.loadUserLogin();
        if(userLoginData != null){
            return true;
        }

        return false;
    }

    /**
     * 后期学完IO，在这里把数据库存入文件
     * @return
     */
    public synchronized boolean saveData(){
        if(userLoginData == null){
            return false;
        }
        boolean ret = Save.saveUserLogin(userLoginData);
        if(ret){
            userLoginData = null;
        }
        return ret;
    }

    /**
     * 添加数据
     * @param user
     * @param passwd
     */
    public synchronized void add(String user, String passwd){
        userLoginData.put(user, passwd);
    }

    /**
     * 修改数据
     * @param user
     * @param oldPasswd
     * @param newPasswd
     * @return true 修改成功
     */
    public synchronized boolean modify(String user, String oldPasswd, String newPasswd){
        return userLoginData.replace(user, oldPasswd, newPasswd);
    }

    /**
     * 删除数据
     * @param user
     * @param passwd
     * @return true 删除成功
     */
    public synchronized boolean remove(String user, String passwd){
        return userLoginData.remove(user, passwd);
    }

    /**
     * 查询用户是否存在
     * @param user
     * @return
     */
    public boolean hasUser(String user){
        return userLoginData.containsKey(user);
    }

    /**
     * 匹配数据
     * @param user
     * @param passwd
     * @return true 数据正确
     */
    public boolean confirmUser(String user, String passwd){
        return (userLoginData.get(user) != null && userLoginData.get(user).equals(passwd));
    }

    /**
     * 获取全部用户
     * @return
     */
    public String[] getAllUser(){
        Set<String> user = userLoginData.keySet();
        String[] userArray = new String[user.size()];
        return user.toArray(userArray);
    }

    /**
     * 获取密码
     * @param user
     * @return String
     */
    public String getPasswd(String user){
        return userLoginData.get(user);
    }

}
