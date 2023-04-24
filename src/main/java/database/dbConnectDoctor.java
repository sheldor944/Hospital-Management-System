package database;

import datamodel.Doctor;
import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import java.io.StringWriter;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class dbConnectDoctor extends dbConnect{

    public void addDoctorToDB(Doctor doctor)
    {
        try{
            statement.executeUpdate(
                "INSERT INTO DOCTOR "
                + "VALUES"
                + "("
                    + "'" + doctor.getId() + "', "
                    + "'" + doctor.getFirstName() + "', "
                    + "'" + doctor.getLastName() + "', "
                    + "'" + doctor.getDateOfBirth() + "', "
                    + "'" + doctor.getAge() + "', "
                    + "'" + doctor.getGender() + "', "
                    + "'" + doctor.getMobile() + "', "
                    + "'" + doctor.getJoiningDate() + "', "
                    + "'" + doctor.getPost() + "', "
                    + "'" + doctor.getDepartment() + "', "
                    + "'" + doctor.getDescription() + "'"
                + ")"
            );
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }
    }

    public void increaseDoctorCount(){
        try {
            statement.executeUpdate(
                    "UPDATE STATISTICS "
                    + "SET COUNT = COUNT + 1 "
                    + "WHERE NAME = 'DOCTOR_COUNT'"
            );
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public int getDoctorCount(){
        int doctorCount = -1;
        try {
            resultSet = statement.executeQuery(
                "SELECT COUNT FROM STATISTICS WHERE NAME = 'DOCTOR_COUNT'"
            );
            while(resultSet.next()){
                doctorCount = resultSet.getInt("COUNT");
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
        return doctorCount;
    }

    public ObservableList <String> getDoctorByDepartment(String department){
        ArrayList <String> arrayList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery(
                "SELECT * FROM DOCTOR WHERE "
                + "DEPARTMENT = " + "'" + department + "'"
            );

            while(resultSet.next()){
                int id = resultSet.getInt("ID");
                String name =
                    resultSet.getString("FIRST_NAME")
                    + " "
                    + resultSet.getString("LAST_NAME");
                arrayList.add(id + " " + name);
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return FXCollections.observableArrayList(arrayList);
    }

    public ObservableList <Doctor> getAllDoctors(){
        ArrayList <Doctor> arrayList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT * FROM DOCTOR");
            while(resultSet.next()){
                Doctor doctor = new Doctor(
                        resultSet.getInt("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        LocalDate.parse(resultSet.getString("DATE_OF_BIRTH")),
                        Integer.parseInt(resultSet.getString("AGE")),
                        resultSet.getString("GENDER"),
                        resultSet.getString("MOBILE"),
                        LocalDate.parse(resultSet.getString("JOINING_DATE")),
                        resultSet.getString("POST"),
                        resultSet.getString("DEPARTMENT"),
                        resultSet.getString("DESCRIPTION")
                );
                arrayList.add(doctor);
                System.out.println(doctor.getFirstName() + " " + doctor.getDateOfBirth());
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return FXCollections.observableArrayList(arrayList);
    }

    public Doctor getDoctorByID(int id){
        Doctor doctor = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM DOCTOR WHERE "
                            + "ID = " + "'" + id + "'"
            );
            while(resultSet.next()){
                doctor = new Doctor(
                        resultSet.getInt("ID"),
                        resultSet.getString("FIRST_NAME"),
                        resultSet.getString("LAST_NAME"),
                        LocalDate.parse(resultSet.getString("DATE_OF_BIRTH")),
                        Integer.parseInt(resultSet.getString("AGE")),
                        resultSet.getString("GENDER"),
                        resultSet.getString("MOBILE"),
                        LocalDate.parse(resultSet.getString("JOINING_DATE")),
                        resultSet.getString("POST"),
                        resultSet.getString("DEPARTMENT"),
                        resultSet.getString("DESCRIPTION")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
        return doctor;
    }

    public void updateDoctor(int doctorID, Doctor updatedDoctor){
        try {
            statement.executeUpdate(
                    "DELETE FROM DOCTOR WHERE "
                            + "ID = " + "'" + doctorID + "'"
            );
            addDoctorToDB(updatedDoctor);
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ObservableList<Doctor> getObservableList(ObservableList doctorObservableList)
    {
        try{
            resultSet = statement.executeQuery("Select * From doctor;");
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String id = resultSet.getString("id");
                String department = resultSet.getString("department");

//                doctorObservableList.add(new Doctor(id , name , name , new Date() , 33,"mael" , "dibo na "  , new Date() , "" , department , description));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return doctorObservableList ;
    }

}
