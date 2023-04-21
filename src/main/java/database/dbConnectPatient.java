package database;

import datamodel.Patient;
import javafx.collections.ObservableList;

import java.sql.*;

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
        catch (Exception e ){
            System.out.println(e);
        } finally {
            close();
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
        } finally {
            close();
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
        } finally {
            close();
        }
        return patientCount;
    }

    public ObservableList getObservableList(ObservableList patientObservableList)
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

                patientObservableList.add(new Patient(patientID , firstName , lastName , dateOfBirth , age , gender , mobile , sympotms));
            }

        }
        catch (Exception e )
        {
            System.out.println(e);
        }

        return patientObservableList ;
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
