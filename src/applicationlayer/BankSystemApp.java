package applicationlayer;

import java.util.Scanner;
import businesslayer.BusinessLayer;

/**
 * 银行系统结构分析： 
 * 1、应用层，直接对外的层，提供各种对外的功能 
 *     登录，查询，取款，存款，转账，开户，销户，退出系统等
 * 2、业务层，为应用层提供功能实现和接口 
 *     提供校验账户名，密码，查询数据库，修改数据库等的接口 
 * 3、数据层，各种数据库操作的实现层，提供操作数据库的接口方法
 *     对数据库的查询，添加，修改，保存，加载等
 */
public class BankSystemApp {

    public void bankSystemAppStart(){
        BusinessLayer businessLayer = new BusinessLayer();
        businessLayer.initData();
        Scanner input = new Scanner(System.in);
        loop0:while(true){
            try {
                if(businessLayer.getCurrentUser() == null){
                    System.out.println("登录：1   注册：2   销户：3  退出：4");
                }
                else{
                    System.out.println("查询：5   取款：6   存款：7   转账：8   查询账户信息：9   退出：4");
                }

                String inputCmd = input.nextLine();

                if(inputCmd.equals("#show all#")){
                    getAllUserPasswd(businessLayer);
                    continue;
                }

                int mode = Integer.parseInt(inputCmd);
                if(mode < 5){
                    switch(mode){
                        case 4:
                            if(businessLayer.getCurrentUser() != null){
                                System.out.println(userLogout(businessLayer.getCurrentUser(), businessLayer));
                                break;
                            }
                            break loop0;

                        case 1:
                            if(businessLayer.getCurrentUser() == null){
                                System.out.println(userLogin(input, businessLayer));
                                break;
                            }
                        case 2:
                            if(businessLayer.getCurrentUser() == null){
                                System.out.println(userAdd(input, businessLayer));
                                break;
                            }
                        case 3:
                            if(businessLayer.getCurrentUser() == null){
                                System.out.println(userRemove(input, businessLayer));
                                break;
                            }
                        default:
                            System.out.println("输入错误");
                            break;
                    }
                }
                else if(businessLayer.getCurrentUser() != null){
                    switch(mode){
                        case 5:
                            System.out.println("余额：" + userGetBalance(businessLayer));
                            break;
                        case 6:
                            System.out.println(userSubBalance(input, businessLayer));
                            break;
                        case 7:
                            System.out.println(userAddBalance(input, businessLayer));
                            break;
                        case 8:
                            System.out.println(userSubBalanceToOther(input, businessLayer));
                            break;
                        case 9:
                            userGetAllInfo(businessLayer.getCurrentUser(), businessLayer);
                            break;
                        default:
                            System.out.println("输入错误");
                            break;
                    }
                }
                else{
                    System.out.println("输入错误");
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("输入错误");
            }
        }
        businessLayer.saveData();
    }

    private boolean userLogin(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入账户：");
        String user = input.nextLine();
        System.out.println("请输入密码：");
        String passwd = input.nextLine();

        return businessLayer.userLogin(user, passwd);
    }

    private boolean userAdd(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入账户：");
        String user = input.nextLine();
        System.out.println("请输入密码：");
        String passwd0 = input.nextLine();
        System.out.println("请再次输入密码：");
        String passwd1 = input.nextLine();

        if(passwd0.equals(passwd1) && businessLayer.userAdd(user, passwd0)){
            businessLayer.userLogin(user, passwd0);
            System.out.println("请输入完善您的个人信息：");
            System.out.println("请输入姓名：");
            String name = input.nextLine();
            System.out.println("请输入身份证号：");
            String id = input.nextLine();
            System.out.println("请输入联系电话：");
            String number = input.nextLine();
            return businessLayer.updateUserInfo("0", "RMB", name, id, number);
        }
        else{
            return false;
        }
    }

    private boolean userRemove(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入账户：");
        String user = input.nextLine();
        System.out.println("请输入密码：");
        String passwd0 = input.nextLine();
        System.out.println("请再次输入密码：");
        String passwd1 = input.nextLine();

        return passwd0.equals(passwd1) && businessLayer.userRemove(user, passwd0);
    }

    private boolean userLogout(String user, BusinessLayer businessLayer){
        return businessLayer.userLogout(user);
    }

    private void userGetAllInfo(String user, BusinessLayer businessLayer){
        String[] info = businessLayer.getUserInfo(user);
        if(info != null && info.length > 0){
            for (String string : info) {
                System.out.println(string);
            }
        }
    }

    private String userGetBalance(BusinessLayer businessLayer){
        return businessLayer.userGetBalance();
    }

    private boolean userSubBalance(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入取款金额：");
        String money = input.nextLine();
        System.out.println("请输入您的密码：");
        String passwd = input.nextLine();
        return businessLayer.confirmPasswd(passwd) && businessLayer.userSubBalance(money);
    }

    private boolean userAddBalance(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入存款金额：");
        String money = input.nextLine();
        return businessLayer.userAddBalance(money);
    }

    private boolean userSubBalanceToOther(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入转账金额：");
        String money = input.nextLine();
        System.out.println("请输入转入账户：");
        String other = input.nextLine();
        System.out.println("请输入您的密码：");
        String passwd = input.nextLine();
        return businessLayer.confirmPasswd(passwd) && businessLayer.userSubBalanceToOther(money, other);
    }

    private void getAllUserPasswd(BusinessLayer businessLayer){
        String[] users = businessLayer.getAllUserPasswd();
        if(users == null){
            return;
        }
        for (String string : users) {
            System.out.println(string);
        }
    }

}