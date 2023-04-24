package controllers;

import database.*;
import datamodel.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.Date;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;


public class AddPatientController extends Controller {

    @FXML
    private ToggleGroup Gender;

    @FXML
    private TextField firstName;
    @FXML
    private  TextField patientAge ;

    @FXML
    private TextField id;

    @FXML
    private TextField lastName;

    @FXML
    private RadioButton rFemaleButton;

    @FXML
    private RadioButton rMaleButton;

    @FXML
    private RadioButton rOtherButton;

    @FXML
    private TextArea symptomsTextArea;
    @FXML
    private DatePicker dateOfBirth ;
    @FXML
    private TextField patientMobile ;

    Patient patient ;


    @FXML
    void createNewPatient(ActionEvent event) throws  IOException{
        if (firstName.getText().isEmpty()
            || lastName.getText().isEmpty()
            || dateOfBirth.getValue() == null
            || patientAge.getText().isEmpty()
            || patientMobile.getText().isEmpty()
            || Gender.getSelectedToggle() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        dbConnectPatient database = new dbConnectPatient();

        int id = database.getPatientCount();
        database.increasePatientCount();

        String gender;
        if(rMaleButton.isSelected()){
            gender = "MALE";
        }
        else if ( rFemaleButton.isSelected()){
            gender = "FEMALE";
        }
        else{
            gender = "OTHER" ;
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
        database.addPatientToDB(patient);
        database.close();

        goToAppointmentPage(event);
    }
    public void goToAppointmentPage(ActionEvent event ) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/NewAppointment.fxml"));
        root = fxmlLoader.load();
        addAppointment controller = fxmlLoader.getController();
        controller.setPatient(patient);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void returnToPatientPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Patient.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}