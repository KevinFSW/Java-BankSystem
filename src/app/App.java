package app;

import applicationlayer.BankSystemApp;

/**
 * 银行系统结构分析： 
 * 1、应用层，直接对外的层，提供各种对外的功能 
 *     登录，查询，取款，存款，转账，开户，销户，退出系统等
 * 2、业务层，为应用层提供功能实现和接口 
 *     提供校验账户名，密码，查询数据库，修改数据库等的接口 
 * 3、数据层，各种数据库操作的实现层，提供操作数据库的接口方法
 *     对数据库的查询，添加，修改，保存，加载等
 */
public class App {
    public static void main(String[] args) throws Exception {
        BankSystemApp bankSystemApp = new BankSystemApp(); 
        bankSystemApp.bankSystemAppStart();
    }
}