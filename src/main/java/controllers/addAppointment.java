package controllers;

import database.dbConnectAppointment;
import datamodel.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;


import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class addAppointment implements Initializable {
    @FXML
    private ChoiceBox<String> departmentPicker ;
    @FXML
    private ChoiceBox<?> doctorChoiceBox;
    @FXML
    private DatePicker datePicker ;
    private  Timestamp appointmentDate ;
    @FXML
    private ChoiceBox<Timestamp> timePicker;


    Date selectedDate ;
    String selectedDept ;
    Patient patient  ;
    dbConnectAppointment dbapp = new dbConnectAppointment();

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    private ArrayList<Timestamp> times = new ArrayList<>();

    private String[] departments = {"Medicine" , "Surgery" , "Cardiology"  , "Neurology" , "Psychiatry" , "Radiotherapy" , "ENT" , "Casualty", "Urology" , "Pediatrics" , "Dermatology" , "Gastroenterology" , "Hepatology" , "Neonatology" , "Gynecology and Obstetrics" , "Orthopedics and traumatology"}  ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentPicker.getItems().addAll(departments);
        departmentPicker.setDisable(true); // Disable the departmentPicker initially
        timePicker.setDisable(true); // Disable the timePicker initially
        datePicker.valueProperty().addListener((observable , oldValue , newValue ) ->
        {
            if (newValue != null) {
                // If a date is selected, enable the departmentPicker and disable the timePicker
                departmentPicker.setDisable(false);
                timePicker.setDisable(true);
            } else {
                // If no date is selected, disable both the departmentPicker and the timePicker
                departmentPicker.setDisable(true);
                timePicker.setDisable(true);
            }
        });
        departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue ) ->
        {
            if (newValue != null) {
                // If a department is selected, enable the timePicker
                timePicker.setDisable(false);
                selectedDept = newValue ;
                times = dbapp.searchForAppointment(selectedDate,newValue , times);
                timePicker.getItems().addAll(times);
            } else {
                // If no department is selected, disable the timePicker
                timePicker.setDisable(true);
            }
        });
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
