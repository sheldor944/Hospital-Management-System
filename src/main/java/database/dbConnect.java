package database;
import datamodel.Doctor;
import datamodel.Patient;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import database.dbConnect;
import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;
import java.security.spec.ECField;
import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class dbConnect {



    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private int flag = 0;
    private int id;

    public dbConnect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acme","root","");
            statement = connection.createStatement();
            System.out.println("kitare bala ni ");

        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    public void addPatient(Patient patient)
    {
        try {
//            dbConnect connect = new dbConnect();

            statement.executeUpdate("INSERT INTO patient (patientID , firstName,lastName ,symptoms, dateOfBirth , mobile , gender , age ) values ('"+ patient.getId() + "','" + patient.getFirstName() + "','" +patient.getLastName() +"','"+patient.getSymptoms()+"','" +patient.getDateOfBirth()+"','" + patient.getMobile()+"','"  + patient.getGender() +"','"+patient.getAge()+ "');\n");
            System.out.println("oise " + patient.getId());
        }
        catch (Exception e ){
                System.out.println(e);
        }
    }
    public void addDoctorToDB(Doctor doctor )
    {
        try{
            statement.executeUpdate("insert into doctor (name , description , department ) values ('" + doctor.getFirstName() +"','" + doctor.getDepartment() +"','" + doctor.getDepartment() +"');\n");
        }
        catch (Exception e )
        {
            System.out.println(e);
        }
    }

}


