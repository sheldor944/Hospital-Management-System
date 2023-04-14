package datamodel;

public class Authorizer {
    public static boolean authorize(String username, String password){
        return username.equals("admin") && password.equals("admin");
    }
}
