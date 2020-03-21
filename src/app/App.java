package app;

import applicationlayer.BankSystemApp;

public class App {
    public static void main(String[] args) throws Exception {
        BankSystemApp bankSystemApp = new BankSystemApp(); 
        bankSystemApp.bankSystemAppStart();
    }
}