package database;

import datamodel.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class dbConnectAppointment extends  dbConnect {
    public void addAppointmentToDB(Appointment appointment) {
        try {
            statement.executeUpdate(
                    "INSERT INTO APPOINTMENT "
                            + "VALUES"
                            + "("
                            + "'" + appointment.getDoctorID() + "', "
                            + "'" + appointment.getPatientID() + "', "
                            + "'" + appointment.getDate() + "', "
                            + "'" + appointment.getTime() + "', "
                            + "'" + appointment.getDepartment() + "'"
                            + ")"
            );
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            close();
        }
    }

    //    returns true if such an appointment is already present
    public boolean searchAppointment(Appointment appointment) {
        boolean found = false;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM APPOINTMENT WHERE "
                            + "DOCTOR_ID = '" + appointment.getDoctorID() + "' AND "
                            + "DATE = '" + appointment.getDate() + "' AND "
                            + "TIME = '" + appointment.getTime() + "'"
            );

            while (resultSet.next()) {
                found = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return found;
    }

    public ObservableList<LocalTime> getAvailableTimes(int doctorID, LocalDate localDate) {
        ArrayList<LocalTime> availableTimes = new ArrayList<>();
        LocalTime time = LocalTime.NOON;

        while (time.compareTo(LocalTime.of(18, 0)) <= 0) {
            Appointment appointment = new Appointment(doctorID, localDate, time);
            if (!searchAppointment(appointment)) {
                availableTimes.add(time);
            }
            time = time.plusMinutes(20);
        }

        return FXCollections.observableArrayList(availableTimes);
    }

    public Appointment getAppointmentByPatientID(int patientID){
        Appointment appointment = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM APPOINTMENT WHERE "
                        + "PATIENT_ID = '" + patientID + "'"
            );

            while (resultSet.next()) {
                appointment = new Appointment(
                        resultSet.getInt("DOCTOR_ID"),
                        resultSet.getInt("PATIENT_ID"),
                        LocalDate.parse(resultSet.getString("DATE")),
                        LocalTime.parse(resultSet.getString("TIME")),
                        resultSet.getString("DEPARTMENT")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return appointment;
    }

    public Appointment getAppointmentByDoctorID(int doctorID){
        Appointment appointment = null;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM APPOINTMENT WHERE "
                            + "DOCTOR_ID = '" + doctorID + "'"
            );

            while (resultSet.next()) {
                appointment = new Appointment(
                        resultSet.getInt("DOCTOR_ID"),
                        resultSet.getInt("PATIENT_ID"),
                        LocalDate.parse(resultSet.getString("DATE")),
                        LocalTime.parse(resultSet.getString("TIME")),
                        resultSet.getString("DEPARTMENT")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return appointment;
    }

    public void deleteByPatientID(int id){
        try {
            statement.executeUpdate(
                    "DELETE FROM APPOINTMENT WHERE "
                     + "PATIENT_ID = '" + id + "'"
            );
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void deleteByDoctorID(int id){
        try {
            statement.executeUpdate(
                    "DELETE FROM APPOINTMENT WHERE "
                        + "DOCTOR_ID = '" + id + "'"
            );
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
    }

    public ObservableList <Appointment> getAllAppointments(){
        ObservableList <Appointment> appointments =
                FXCollections.observableArrayList();

        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM APPOINTMENT"
            );

            while (resultSet.next()) {
                Appointment appointment = new Appointment(
                        resultSet.getInt("DOCTOR_ID"),
                        resultSet.getInt("PATIENT_ID"),
                        LocalDate.parse(resultSet.getString("DATE")),
                        LocalTime.parse(resultSet.getString("TIME")),
                        resultSet.getString("DEPARTMENT")
                );
                appointments.add(appointment);
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }

        return appointments;
    }
}