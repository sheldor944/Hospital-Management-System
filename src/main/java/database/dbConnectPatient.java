package database;

import datamodel.Patient;
import javafx.collections.ObservableList;

import java.sql.*;

public class dbConnectPatient extends dbConnect{
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

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
