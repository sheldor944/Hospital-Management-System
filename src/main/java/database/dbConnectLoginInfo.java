package database;

import datamodel.LoginInfo;

import java.sql.SQLException;

public class dbConnectLoginInfo extends dbConnect {
    public LoginInfo getLoginInfo(){
        LoginInfo loginInfo = null;

        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM LOGIN_INFO"
            );

            while(resultSet.next()) {
                loginInfo = new LoginInfo(
                        resultSet.getString("USERNAME"),
                        resultSet.getString("PASSWORD")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }

        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo){
        try {
            statement.executeUpdate(
                    "UPDATE LOGIN_INFO SET "
                    + "USERNAME = '" + loginInfo.getUsername() + "', "
                    + "PASSWORD = '" + loginInfo.getPassword() + "' "
                    + "WHERE rowid = '1'"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
