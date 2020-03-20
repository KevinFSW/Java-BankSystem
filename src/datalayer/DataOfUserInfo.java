package datalayer;

import java.util.HashMap;

import entry.DataEntry;

/**
 * 操作用户余额等信息需要用到的数据库和接口
 */
public class DataOfUserInfo {

    /**
     * 数据库，保存用户名和用户当前余额,
     * 
     * 静态的，内存中只有一份,
     * 
     * 使用HashMap方便去重,
     * 
     * key无序无重复，value无序可重复,
     * 
     * HashMap< username, entry >,
     * username是唯一的，entry可以重复
     */
    private volatile static HashMap<String, DataEntry> userInfoData = null;

    /**
     * 后期学完IO，在这里从文件加载初始化数据库
     * @return
     */
    public synchronized static boolean initData(){
        userInfoData = Load.loadUserInfo();
        if(userInfoData != null){
            return true;
        }

        return false;
    }

    /**
     * 后期学完IO，在这里把数据库存入文件
     * @return
     */
    public synchronized static boolean saveData(){
        return Save.saveUserInfo(userInfoData);
    }

    /**
     * 添加数据
     * @param user
     * @param entry
     */
    public synchronized static void add(String user, DataEntry entry){
        userInfoData.put(user, entry);
    }

    /**
     * 修改数据
     * @param user
     * @param oldEntry
     * @param newEntry
     * @return true 修改成功
     */
    public synchronized static boolean modify(String user, DataEntry oldEntry, DataEntry newEntry){
        return userInfoData.replace(user, oldEntry, newEntry);
    }

    /**
     * 删除数据
     * @param user
     * @param entry
     * @return true 删除成功
     */
    public synchronized static boolean remove(String user, DataEntry entry){
        return userInfoData.remove(user, entry);
    }

    /**
     * 查询用户是否存在
     * @param user
     * @return
     */
    public static boolean hasUser(String user){
        return userInfoData.containsKey(user);
    }

    /**
     * 查询数据
     * @param user
     * @return DataEntry
     */
    public static DataEntry getInfo(String user){
        return userInfoData.get(user);
    }
}
