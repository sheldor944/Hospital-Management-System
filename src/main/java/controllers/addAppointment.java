package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import datamodel.Appointment;
import datamodel.Hospital;
import datamodel.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addAppointment implements Initializable {
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
                datePicker.setDisable(true);
            }
        });
        departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue ) ->
        {
            if (newValue != null) {
                selectedDept = newValue ;
                doctorPicker.setDisable(false);
                doctorPicker.getItems().removeAll();
                doctorPicker.getItems().addAll(
                    new dbConnectDoctor().getDoctorByDepartment(selectedDept)
                );
                departmentPicker.setDisable(true);
            }
        });
        doctorPicker.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldValue, newValue) -> {
                String doctorInfo = newValue;
                doctorID = Integer.parseInt(
                    (doctorInfo.split("\\s+"))[0]
                );
                timePicker.getItems().addAll(
                    database.getAvailableTimes(
                        doctorID,
                        datePicker.getValue()
                    )
                );
                timePicker.setDisable(false);
                doctorPicker.setDisable(true);
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
    public void submit(ActionEvent event )
    {
        if (departmentPicker.getValue() == null
                || datePicker.getValue() == null
                || doctorPicker.getValue() == null
                || timePicker.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
        } else {
            dbConnectAppointment database = new dbConnectAppointment();
            database.addAppointmentToDB(
                new Appointment(
                    doctorID,
                    patient.getId(),
                    selectedDate,
                    timePicker.getValue(),
                    selectedDept
                )
            );
        }
    }


}
