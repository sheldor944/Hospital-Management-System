package controllers;

import database.dbConnectDoctor;
import database.dbConnectPatient;
import datamodel.Appointment;
import datamodel.Doctor;
import datamodel.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class DisplayAppointmentController {

    @FXML
    private Label dateLabel;

    @FXML
    private Label doctorNameLabel;

    @FXML
    private Label patientNameLabel;

    @FXML
    private Label timeLabel;

    private Appointment appointment;

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public void init(){
        Patient patient = new dbConnectPatient().getPatientByID(appointment.getPatientID());
        Doctor doctor = new dbConnectDoctor().getDoctorByID(appointment.getDoctorID());

        patientNameLabel.setText(
                "Patient: "
                + patient.getFirstName() + " " + patient.getLastName()
        );
        doctorNameLabel.setText(
                "Doctor: " + doctor.getFirstName() + " " + doctor.getLastName()
                + " ("
                + "ID: " + doctor.getId()
                + ", Department: " + doctor.getDepartment()
                + ")"
        );
        timeLabel.setText("Time: " + appointment.getTime().toString());
        dateLabel.setText("Date: " + appointment.getDate().toString());
    }
}
