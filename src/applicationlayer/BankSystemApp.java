package applicationlayer;

import java.util.Scanner;

import businesslayer.BusinessLayer;

public class BankSystemApp {

    public void bankSystemAppStart(){
        BusinessLayer businessLayer = BusinessLayer.getBusinessLayer();
        businessLayer.initData();
        Scanner input = new Scanner(System.in);
        loop0:while(true){
            try {
                if(businessLayer.getCurrentUser() == null){
                    System.out.println("登录：1   注册：2   销户：3  退出：4");
                }
                else{
                    System.out.println("查询：5   取款：6   存款：7   转账：8   退出：4");
                }
    
                int mode = Integer.parseInt(input.nextLine());
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
        String passwd = input.nextLine();

        return businessLayer.userAdd(user, passwd);
    }

    private boolean userRemove(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入账户：");
        String user = input.nextLine();
        System.out.println("请输入密码：");
        String passwd = input.nextLine();

        return businessLayer.userRemove(user, passwd);
    }

    private boolean userLogout(String user, BusinessLayer businessLayer){
        return businessLayer.userLogout(user);
    }

    private String userGetBalance(BusinessLayer businessLayer){
        return businessLayer.userGetBalance();
    }

    private boolean userSubBalance(Scanner input, BusinessLayer businessLayer){
        System.out.println("请输入取款金额：");
        String money = input.nextLine();
        return businessLayer.userSubBalance(money);
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
        return businessLayer.userSubBalanceToOther(money, other);
    }

}