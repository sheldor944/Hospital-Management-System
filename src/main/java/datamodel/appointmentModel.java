package datamodel;

import java.sql.Timestamp;

public class appointmentModel {
    private String patientId;
    private  String doctorId ;
    private String patientName ;
    private String doctorName ;
    private Timestamp time ;
    private String department ;

    public appointmentModel(String patientId, String doctorId, String patientName, String doctorName, Timestamp time, String department) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.time = time;
        this.department = department;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public Timestamp getTime() {
        return time;
    }

    public String getDepartment() {
        return department;
    }


}
