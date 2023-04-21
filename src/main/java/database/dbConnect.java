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
import java.time.Instant;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.security.spec.ECField;
import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;

public class dbConnect {
    private static final String dbLink = "jdbc:sqlite:src/main/resources/database/database.db";
    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;
    private int flag = 0;
    private int id;

    public dbConnect(){
        try {
            connection.isValid(3500);
        } catch (Exception e) {
            try {
                connection = DriverManager.getConnection(dbLink);
                statement = connection.createStatement();
                System.out.println("Database connected successfully");

//                testDatabase();
            } catch (SQLException ex) {
                System.out.println("Could not create connection with link " + dbLink);
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    protected void close() {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void testDatabase(){
        try {
            resultSet = statement.executeQuery("SELECT * FROM PATIENT");
            int found = 0;
            while(resultSet.next()){
                found = 1;
                break;
            }
            if(found != 0) System.out.println("At least one record is present");
            else System.out.println("No such record is present.");
        } catch (SQLException e) {
            System.out.println("Could not get the stuff");
            System.exit(1);
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


