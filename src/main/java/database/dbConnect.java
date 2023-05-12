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
    protected Connection connection = null;
    protected Statement statement = null;
    protected ResultSet resultSet = null;
    private int flag = 0;
    private int id;

    public dbConnect(){
        try {
            connection.isValid(3500);
        } catch (Exception e) {
            try {
                connection = DriverManager.getConnection(dbLink);
                statement = connection.createStatement();
            } catch (SQLException ex) {
                System.out.println("Could not create connection with link " + dbLink);
                ex.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void close() {
        try {
            if(resultSet != null) resultSet.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void addPatient(Patient patient)
    {
        try {
//            dbConnect connect = new dbConnect();

            statement.executeUpdate("INSERT INTO patient (patientID , firstName,lastName ,symptoms, dateOfBirth , mobile , gender , age ) values ('"+ patient.getId() + "','" + patient.getFirstName() + "','" +patient.getLastName() +"','"+patient.getSymptoms()+"','" +patient.getDateOfBirth()+"','" + patient.getMobile()+"','"  + patient.getGender() +"','"+patient.getAge()+ "');\n");
//            System.out.println("oise " + patient.getId());
        }
        catch (Exception e ){
                System.out.println(e);
        }
    }
}


