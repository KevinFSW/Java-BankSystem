package datalayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;

import entry.DataEntry;

public class Load {

    private static final String userLoginPath = ".//src//datalayer//userLogin.dat";
    private static final String userInfoPath = ".//src//datalayer//userInfo.dat";

    private Load(){};

    /**
     * 加载
     * @return
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, String> loadUserLogin() {
        System.out.println("Loading userLogin data...");
        File login = new File(userLoginPath);
        FileInputStream loginFi = null;
        ObjectInputStream loginObjIS = null;
        HashMap<String, String> loginObj = null;
        try {
            loginFi = new FileInputStream(login);
            loginObjIS = new ObjectInputStream(loginFi);
            loginObj = (HashMap<String, String>) loginObjIS.readObject();
            System.out.println("Load success!");
            return loginObj;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                loginObjIS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if(loginObj == null){
                loginObj = new HashMap<>();
            }
        }

        System.out.println("Load failed!");
        return loginObj;
    }

    /**
     * 加载
     * @return
     */
    @SuppressWarnings("unchecked")
    public static HashMap<String, DataEntry> loadUserInfo() {
        System.out.println("Loading userInfo data...");
        File info = new File(userInfoPath);
        FileInputStream infoFi = null;
        ObjectInputStream infoObjIS = null;
        HashMap<String, DataEntry> loginObj = null;
        try {
            infoFi = new FileInputStream(info);
            infoObjIS = new ObjectInputStream(infoFi);
            loginObj = (HashMap<String, DataEntry>) infoObjIS.readObject();
            System.out.println("Load success!");
            return loginObj;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                infoObjIS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if(loginObj == null){
                loginObj = new HashMap<>();
            }
        }

        System.out.println("Load failed!");
        return loginObj;
    }

}