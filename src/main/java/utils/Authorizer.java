package utils;

import database.dbConnectLoginInfo;
import datamodel.LoginInfo;

public class Authorizer {
    public static boolean authorize(String username, String password){
        LoginInfo loginInfo =
                new dbConnectLoginInfo().getLoginInfo();
        return username.equals(loginInfo.getUsername())
                && password.equals(loginInfo.getPassword());
    }
}
