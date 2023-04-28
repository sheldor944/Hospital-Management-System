package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import database.dbConnectPatient;
import datamodel.Appointment;
import datamodel.Doctor;
import datamodel.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.AlertUtils;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class DisplayPatientController extends Controller {

    @FXML
    private ToggleGroup Gender;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private TextField patientAge;

    @FXML
    private TextField patientMobile;

    @FXML
    private AnchorPane patientNumber;

    @FXML
    private RadioButton rFemaleButton;

    @FXML
    private RadioButton rMaleButton;

    @FXML
    private RadioButton rOtherButton;

    @FXML
    private TextArea symptomsTextArea;

    @FXML
    private Label doctorLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label timeLabel;

    @FXML
    private Button modifyButton;

    private Patient patient;
    private boolean hasDecidedToPoceed = false;

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void initialize(){
        doctorLabel.setText("");
        timeLabel.setText("");
        dateLabel.setText("");
    }

    public void init() {
        modifyButton.requestFocus();

        System.out.println("Initializing the fields in Display Patient...");
        firstName.setText(patient.getFirstName());
        lastName.setText(patient.getLastName());
        dateOfBirth.setValue(patient.getDateOfBirth());
        patientAge.setText(Integer.toString(patient.getAge()));

        if(patient.getGender().equals("MALE")){
            rMaleButton.setSelected(true);
        } else if(patient.getGender().equals("FEMALE")){
            rFemaleButton.setSelected(true);
        } else{
            rOtherButton.setSelected(true);
        }

        patientMobile.setText(patient.getMobile());
        symptomsTextArea.setText(patient.getSymptoms());


//        ------ the code below handles the appointment bit ------


        dbConnectAppointment database = new dbConnectAppointment();
        Appointment appointment = database.getAppointmentByPatientID(patient.getId());
        if(appointment != null) {
            Doctor doctor = new dbConnectDoctor().getDoctorByID(appointment.getDoctorID());

            doctorLabel.setText("Assigned Doctor: " + doctor.getFirstName() + " " + doctor.getLastName());
            dateLabel.setText("Date: " + appointment.getDate());
            timeLabel.setText("Time: " + appointment.getTime());
        } else {
            doctorLabel.setText("The patient has to be rescheduled.");
        }
    }

    @FXML
    void modifyButtonClicked(ActionEvent event) {
        if (firstName.getText().isEmpty()
                || lastName.getText().isEmpty()
                || dateOfBirth.getValue() == null
                || patientAge.getText().isEmpty()
                || patientMobile.getText().isEmpty()
                || Gender.getSelectedToggle() == null) {
            AlertUtils.showAlert("Please fill in all required fields.");
            return;
        }
        if(AlertUtils.getConfirmation()){
            dbConnectPatient database = new dbConnectPatient();

            int id = patient.getId();
            String gender;
            if (rMaleButton.isSelected()) {
                gender = "MALE";
            } else if (rFemaleButton.isSelected()) {
                gender = "FEMALE";
            } else {
                gender = "OTHER";
            }
            int age = Integer.parseInt(patientAge.getText());
            LocalDate localDate = dateOfBirth.getValue();

            patient = new Patient(
                    id,
                    firstName.getText(),
                    lastName.getText(),
                    localDate,
                    age,
                    gender,
                    patientMobile.getText(),
                    symptomsTextArea.getText()
            );

            database.updatePatient(id, patient);
            database.close();

            AlertUtils.showAlert("The information has been updated.");
        }
        else{
//            restore patient details if modifications are discarded
            System.out.println("Cancel (modifying patient) button was clicked");
            init();
        }
    }

    @FXML
    void deletePatientClicked(ActionEvent event) throws IOException {
        if(AlertUtils.getConfirmation()){
            System.out.println("Clicked on yes (delete patient)");
            new dbConnectPatient().delete(patient.getId());
            new dbConnectAppointment().deleteByPatientID(patient.getId());
            AlertUtils.showAlert("The patient was successfully deleted.");
            switchToPatient(event);
        }
    }

    @FXML
    void rescheduleButtonClicked(ActionEvent event) throws IOException {
        if(AlertUtils.getConfirmation()) {
            dbConnectAppointment database = new dbConnectAppointment();
            database.deleteByPatientID(patient.getId());

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NewAppointment.fxml"));
            root = fxmlLoader.load();
            addAppointment controller = fxmlLoader.getController();
            controller.setPatient(patient);
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
}
