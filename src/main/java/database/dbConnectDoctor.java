package database;

import datamodel.Doctor;
import javafx.collections.ObservableList;
import javafx.scene.Parent;

import java.sql.*;
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
        finally {

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
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
                System.exit(1);
            }
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
        } finally {
            close();
        }
        return doctorCount;
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
