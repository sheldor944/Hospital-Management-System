package database;

import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class dbConnectPatient extends dbConnect{
    public void addPatientToDB(Patient patient)
    {
        try {
            statement.executeUpdate(
                    "INSERT INTO PATIENT "
                    + "VALUES"
                    + "("
                    + "'" + patient.getId() + "', "
                    + "'" + patient.getFirstName() + "', "
                    + "'" + patient.getLastName() + "', "
                    + "'" + patient.getDateOfBirth() + "', "
                    + "'" + patient.getAge() + "', "
                    + "'" + patient.getGender() + "', "
                    + "'" + patient.getMobile() + "', "
                    + "'" + patient.getSymptoms() + "', "
                    + "'" + patient.getAssignedDoctorID() + "'"
                    + ")"
            );
        }
        catch (Exception e){
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void increasePatientCount(){
        try {
            statement.executeUpdate(
            "UPDATE STATISTICS "
                + "SET COUNT = COUNT + 1 "
                + "WHERE NAME = 'PATIENT_COUNT'"
            );
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public int getPatientCount(){
        int patientCount = -1;
        try {
            resultSet = statement.executeQuery(
                    "SELECT COUNT FROM STATISTICS WHERE NAME = 'PATIENT_COUNT'"
            );
            while(resultSet.next()){
                patientCount = resultSet.getInt("COUNT");
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
        return patientCount;
    }

    public ObservableList <Patient> getAllPatients(){
        ArrayList <Patient> arrayList = new ArrayList<>();
        try {
            resultSet = statement.executeQuery("SELECT * FROM PATIENT");
            while(resultSet.next()){
                arrayList.add(new Patient(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRST_NAME"),
                    resultSet.getString("LAST_NAME"),
                    LocalDate.parse(resultSet.getString("DATE_OF_BIRTH")),
                    Integer.parseInt(resultSet.getString("AGE")),
                    resultSet.getString("GENDER"),
                    resultSet.getString("MOBILE"),
                    resultSet.getString("SYMPTOMS")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return FXCollections.observableArrayList(arrayList);
    }

    public Patient getPatientByID(int id){
        Patient patient = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM PATIENT WHERE "
                    + "ID = " + "'" + id + "'"
            );
            while(resultSet.next()){
                patient = new Patient(
                    resultSet.getInt("ID"),
                    resultSet.getString("FIRST_NAME"),
                    resultSet.getString("LAST_NAME"),
                    LocalDate.parse(resultSet.getString("DATE_OF_BIRTH")),
                    Integer.parseInt(resultSet.getString("AGE")),
                    resultSet.getString("GENDER"),
                    resultSet.getString("MOBILE"),
                    resultSet.getString("SYMPTOMS")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            System.exit(1);
        }
        return patient;
    }

    public ObservableList getObservableList(ObservableList patientObservableList)
    {

        try{
            resultSet = statement.executeQuery("select patientId , firstName , lastName , dateOfBirth , age , gender , mobile , symptoms from patient;");
            while(resultSet.next()){
                String patientID = resultSet.getString("patientId") ;
                String firstName = resultSet.getString("firstName") ;
                String lastName = resultSet.getString("lastName") ;
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                Integer age = resultSet.getInt("age");
                String gender = resultSet.getString("gender") ;
                String mobile = resultSet.getString("mobile") ;
                String sympotms = resultSet.getString("symptoms");

//                patientObservableList.add(new Patient(patientID , firstName , lastName , dateOfBirth , age , gender , mobile , sympotms));
            }

        }
        catch (Exception e )
        {
            System.out.println(e);
        }

        return patientObservableList ;
    }

    public void updatePatient(int patientID, Patient patient){
        try {
            statement.executeUpdate(
                "UPDATE PATIENT SET "
                        + "ID = '" + patient.getId() + "', "
                        + "FIRST_NAME = '" + patient.getFirstName() + "', "
                        + "LAST_NAME = '" + patient.getLastName() + "', "
                        + "DATE_OF_BIRTH = '" + patient.getDateOfBirth() + "', "
                        + "AGE = '" + patient.getAge() + "', "
                        + "GENDER = '" + patient.getGender() + "', "
                        + "MOBILE = '" + patient.getMobile() + "', "
                        + "SYMPTOMS = '" + patient.getSymptoms() + "', "
                        + "ASSIGNED_DOCTOR_ID = '" + patient.getAssignedDoctorID() + "'"
                    + "WHERE ID = " + patientID
            );
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }
    public void updatePatient(Patient patient)
    {
        String updateSql = "UPDATE patient SET "
                + "firstName = '" + patient.getFirstName() + "', "
                + "lastName = '" + patient.getLastName() + "', "
                + "symptoms = '" + patient.getSymptoms() + "', "
                + "dateOfBirth = '" + patient.getDateOfBirth() + "', "
                + "mobile = '" + patient.getMobile() + "', "
                + "gender = '" + patient.getGender() + "', "
                + "age = '" + patient.getAge() + "' "
                + "WHERE patientID = '" + patient.getId() + "'";
        System.out.println( updateSql);
        try{
            statement.executeUpdate(updateSql);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
