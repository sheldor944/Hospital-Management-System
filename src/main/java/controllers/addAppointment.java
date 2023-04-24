package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
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


    Date selectedDate ;
    String selectedDept ;
    Patient patient  ;
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
                departmentPicker.setDisable(false);
            }
        });
        departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue ) ->
        {
            if (newValue != null) {
                selectedDept = newValue ;
                doctorPicker.setDisable(false);
                doctorPicker.getItems().addAll(
                    new dbConnectDoctor().getDoctorByDepartment(selectedDept)
                );
            }
        });
        doctorPicker.getSelectionModel().selectedItemProperty().addListener(
            (observableValue, oldValue, newValue) -> {
                String doctorInfo = newValue;
                int id = Integer.parseInt(
                    (doctorInfo.split("\\s+"))[0]
                );
                timePicker.getItems().addAll(
                        database.getAvailableTimes(
                            id,
                            datePicker.getValue()
                        )
                );
                timePicker.setDisable(false);
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
    public Date getSelectedDate(ActionEvent event)
    {
        LocalDate localDate = datePicker.getValue();
        Date date = Date.valueOf(localDate);
        selectedDate = date ;
        return date ;
    }
    public void submit(ActionEvent event )
    {
        if (departmentPicker.getValue() == null || datePicker.getValue() == null || timePicker.getValue() == null) {
            // Show an error message to the user
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please fill in all required fields.", ButtonType.OK);
            alert.showAndWait();
            return;
//            System.out.println("Please fill all the required fields");
        } else {
            // Submit the form
            System.out.println(getSelectedDepartment(event));
            System.out.println(getSelectedDate(event));
            System.out.println(timePicker.getValue());
            System.out.println(patient.getId());
//            dbapp.addAppointment(patient.getId() ,"did420 " , selectedDept , timePicker.getValue() , "Ronaldo" , patient.getFirstName());
            System.out.println("submit o click oise ");
        }
    }


}
