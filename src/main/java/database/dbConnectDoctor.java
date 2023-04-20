package database;

import datamodel.Doctor;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;

public class dbConnectDoctor extends dbConnect{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public ObservableList<Doctor> getObservableList(ObservableList doctorObservableList)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acme", "root", "");
            statement = connection.createStatement();
        }
        catch (Exception e )
        {
            System.out.println(e);
        }
        try{
            resultSet = statement.executeQuery("Select * From doctor;");
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String id = resultSet.getString("id");
                String department = resultSet.getString("department");

                doctorObservableList.add(new Doctor(id , name , name , new Date() , 33,"mael" , "dibo na "  , new Date() , "" , department , description));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return doctorObservableList ;
    }

}
