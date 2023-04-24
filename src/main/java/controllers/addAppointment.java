package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import datamodel.Appointment;
import datamodel.Hospital;
import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addAppointment extends Controller implements Initializable {
    @FXML
    private ChoiceBox <String> departmentPicker;
    @FXML
    private ChoiceBox <String> doctorPicker;
    @FXML
    private DatePicker datePicker;
    private  Timestamp appointmentDate;
    @FXML
    private ChoiceBox <LocalTime> timePicker;


    LocalDate selectedDate ;
    String selectedDept ;
    Patient patient  ;
    private int doctorID;
    dbConnectAppointment database = new dbConnectAppointment();

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private ArrayList<Timestamp> times = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentPicker.getItems().addAll(Hospital.getDepartments());
        departmentPicker.setDisable(true);
        timePicker.setDisable(true);
        doctorPicker.setDisable(true);
        datePicker.valueProperty().addListener((observable , oldValue , newValue ) ->
        {
            if (newValue != null) {
                selectedDate = newValue;
                departmentPicker.setDisable(false);
            }
        });
        departmentPicker.getSelectionModel().selectedItemProperty().addListener(
            (observable , oldValue , newValue ) -> {
                if (newValue != null) {
                    selectedDept = newValue ;
                    doctorPicker.setDisable(false);
                    doctorPicker.getItems().removeAll();
                    doctorPicker.setItems(
                        new dbConnectDoctor().getDoctorByDepartment(selectedDept)
                    );
                    timePicker.setItems(
                        FXCollections.observableArrayList()
                    );
                }
        });
        doctorPicker.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldValue, newValue) -> {
                if(newValue != null) {
                    String doctorInfo = newValue;
                    doctorID = Integer.parseInt(
                            (doctorInfo.split("\\s+"))[0]
                    );
                    timePicker.setItems(
                            database.getAvailableTimes(
                                    doctorID,
                                    datePicker.getValue()
                            )
                    );
                    timePicker.setDisable(false);
                }
            }
        );
        LocalDate minDate = LocalDate.now();
        LocalDate maxDate = minDate.plusDays(5);
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(date.isBefore(minDate) || date.isAfter(maxDate));
            }
        });
    }
    public String getSelectedDepartment(ActionEvent event )
    {
        String dept = departmentPicker.getValue();
        selectedDept = dept ;
        return  dept ;
    }
    public void submit(ActionEvent event ) throws IOException {
        if (departmentPicker.getValue() == null
                || datePicker.getValue() == null
                || doctorPicker.getValue() == null
                || timePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        } else {
            dbConnectAppointment database = new dbConnectAppointment();
            Appointment appointment = new Appointment(
                    doctorID,
                    patient.getId(),
                    selectedDate,
                    timePicker.getValue(),
                    selectedDept
            );
            database.addAppointmentToDB(appointment);
            database.close();

            FXMLLoader loader =
                new FXMLLoader(getClass().getResource("/fxml/DisplayAppointment.fxml"));
            root = loader.load();

            DisplayAppointmentController controller =
                    loader.getController();
            controller.setAppointment(appointment);
            controller.init();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
