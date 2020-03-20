package datalayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import entry.DataEntry;

public class Save {

    private static final String userLoginPath = ".//src//datalayer//userLogin.dat";
    private static final String userInfoPath = ".//src//datalayer//userInfo.dat";

    /**
     * 文件复制
     * @param source
     * @param destination
     * @param force 简单的强制复制
     * @return
     */
    private static Boolean fileCopy(String source, String destination, boolean force) {
        System.out.print(source + "--->");
        System.out.println(destination);
        File s = new File(source);
        File d = new File(destination);
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if (d.exists()) {
            if (force) {
                d.delete();
            } else {
                return false;
            }
        }
        try {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(d, true);
            byte[] buffer = new byte[32];
            int rlen = fi.read(buffer);
            while (rlen != -1) {
                fo.write(buffer, 0, rlen);
                rlen = fi.read(buffer);
            }

            fo.flush();

            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                fi.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                fo.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return false;
    }

    public static boolean saveUserLogin(HashMap<String, String> data) {
        System.out.println("Saving userLogin data...");
        File login = new File(userLoginPath);
        if (login.exists()) {
            System.out.println("Create userLogin data backup...");
            fileCopy(userLoginPath, userLoginPath + ".bk", true);
        }
        FileOutputStream loginFo = null;
        ObjectOutputStream loginObjOS = null;
        try {
            loginFo = new FileOutputStream(login, true);
            loginObjOS = new ObjectOutputStream(loginFo);
            loginObjOS.writeObject(data);
            System.out.println("Save success!");
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                loginObjOS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("Save failed!");
        return false;
    }

    public static boolean saveUserInfo(HashMap<String, DataEntry> data) {
        System.out.println("Saving userInfo data...");
        File info = new File(userInfoPath);
        if (info.exists()) {
            System.out.println("Create userInfo data backup...");
            fileCopy(userInfoPath, userInfoPath + ".bk", true);
        }
        FileOutputStream infoFo = null;
        ObjectOutputStream infoObjOS = null;
        try {
            infoFo = new FileOutputStream(info);
            infoObjOS = new ObjectOutputStream(infoFo);
            infoObjOS.writeObject(data);
            System.out.println("Save success!");
            return true;
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                infoObjOS.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        System.out.println("Save failed!");
        return false;
    }

}