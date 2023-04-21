package datamodel;

import java.util.Date;

public class Appointment {
    private int doctorID;
    private int patientID;
    private Date time;
    private String department;

    public Appointment(int doctorID, int patientID, Date time, String department) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.time = time;
        this.department = department;
    }

    public int getDoctorID() {
        return doctorID;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
