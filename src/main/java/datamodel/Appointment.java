package datamodel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

public class Appointment {
    private int doctorID;
    private int patientID;
    private LocalDate date;
    private LocalTime time;
    private String department;

    public Appointment(int doctorID, int patientID, LocalDate date, LocalTime time, String department) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.department = department;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
