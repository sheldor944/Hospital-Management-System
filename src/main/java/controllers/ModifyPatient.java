package controllers;

import database.dbConnectAppointment;
import database.dbConnectPatient;
import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.sql.Date;

public class ModifyPatient implements Initializable {
    @FXML
    private TableView<Patient> patientTableView ;
    @FXML
    private TableColumn<Patient , String> patientIdTableColumn ;
    @FXML
    private TableColumn<Patient , String> nameTableColumn;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private  TextField firstNameTextField;
    @FXML
    private  TextField lastNameTextField;
    @FXML
    private  TextField mobileTextField;
    @FXML
    private  TextField ageTextField;
    @FXML
    private TextArea symptomsTextArea;
    @FXML
    private DatePicker dateOfBirthPicker;
    @FXML
    private  DatePicker appointmentDatePicker;
    @FXML
    private  RadioButton rNothing , rChange , rDiscard ;
    @FXML
    private ChoiceBox<String> departmentPicker;
    @FXML
    private ChoiceBox<Timestamp> timePicker;


    dbConnectAppointment dbapp = new dbConnectAppointment();

    private ArrayList<Timestamp> times = new ArrayList<>();

    private Date selectedDate;
    private String selectedDept;
    private String[] departments = {"Medicine" , "Surgery" , "Cardiology"  , "Neurology" , "Psychiatry" , "Radiotherapy" , "ENT" , "Casualty", "Urology" , "Pediatrics" , "Dermatology" , "Gastroenterology" , "Hepatology" , "Neonatology" , "Gynecology and Obstetrics" , "Orthopedics and traumatology"}  ;



    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Parent root ;
    private Stage stage ;
    private Scene scene ;
    private  String patientId;
    private String firstName ;
    private String lastName ;
    private String mobile;
    private String gender;
    int age ;
    private  String symptoms;
    private Date DOB;
    int type ;
    Patient patient ;



    ObservableList<Patient> patientObservableList = FXCollections.observableArrayList() ;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            dbConnectPatient dbPatient = new dbConnectPatient();
            patientObservableList = dbPatient.getObservableList(patientObservableList);

            patientIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

            patientTableView.setItems(patientObservableList);

            FilteredList<Patient> patientFilteredList = new FilteredList<>(patientObservableList , b -> true );
            searchBarTextField.textProperty().addListener((observable  , oldValue , newValue) -> {
                patientFilteredList.setPredicate(patient -> {
                    if(newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                        return true ;
                    }
                    String searchKeyword = newValue.toLowerCase() ;
                    if(patient.getFirstName().toLowerCase().indexOf(searchKeyword) > -1)
                    {
                        return true ;
                    }
                    else if (patient.getId().toLowerCase().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
                    else{
                        return false ;
                    }
                });
            });

            SortedList<Patient> sortedList = new SortedList<>(patientFilteredList);
            sortedList.comparatorProperty().bind(patientTableView.comparatorProperty());
            patientTableView.setItems(sortedList);

            patientTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    patientId = newSelection.getId();
                    firstNameTextField.setText(newSelection.getFirstName());

                    firstName = newSelection.getFirstName();
                    lastName = newSelection.getLastName();
                    age= newSelection.getAge();
                    mobile =newSelection.getMobile();
                    symptoms=newSelection.getSymptoms();
                    DOB = (Date) newSelection.getDateOfBirth();
                    gender = newSelection.getGender();

                    lastNameTextField.setText(newSelection.getLastName());
                    mobileTextField.setText(newSelection.getMobile());
                    symptomsTextArea.setText(newSelection.getSymptoms());
                    ageTextField.setText(String.valueOf(newSelection.getAge()));
//                    dateOfBirthPicker.setValue((newSelection.getDateOfBirth().toInstant()
//                            .atZone(ZoneId.systemDefault())
//                            .toLocalDate()));
//
                    // do something with the selected item's information
                    System.out.println(patientId);

                }
            });
            // problem is here , patient object is not getting set properly
            
            patient = new Patient(patientId ,firstName , lastName , DOB ,age ,  gender , mobile , symptoms );
            System.out.println(patient.getFirstName() + " ota ");
            rChange.selectedProperty().addListener((ob, oV, nV) -> {
                if (nV) {
                    appointmentDatePicker.setDisable(false);

                    departmentPicker.getItems().addAll(departments);
                    LocalDate minDate = LocalDate.now();
                    LocalDate maxDate = minDate.plusDays(5);
                    appointmentDatePicker.setDayCellFactory(picker -> new DateCell() {
                        @Override
                        public void updateItem(LocalDate date, boolean empty) {
                            super.updateItem(date, empty);
                            setDisable(date.isBefore(minDate) || date.isAfter(maxDate));
                        }
                    });
                    departmentPicker.setDisable(true); // Disable the departmentPicker initially
                    timePicker.setDisable(true); // Disable the timePicker initially
                    appointmentDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
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
                    departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                    {
                        if (newValue != null) {
                            // If a department is selected, enable the timePicker
                            timePicker.setDisable(false);
                            selectedDept = newValue;
                            System.out.println(selectedDate);
                            times = dbapp.searchForAppointment(selectedDate, newValue, times);
                            timePicker.getItems().addAll(times);
                        } else {
                            // If no department is selected, disable the timePicker
                            timePicker.setDisable(true);
                        }
                    });
                    System.out.println("Radio button is selected");
                } else {
                    departmentPicker.setDisable(true); // Disable the departmentPicker initially
                    timePicker.setDisable(true); // Disable the timePicker initially
                    appointmentDatePicker.setDisable(true);

                    System.out.println("Radio button is not selected");
                }
            });

//            if( rChange.isSelected() ) {
//                //Changing appointment
//                departmentPicker.getItems().addAll(departments);
//                LocalDate minDate = LocalDate.now();
//                LocalDate maxDate = minDate.plusDays(5);
//                appointmentDatePicker.setDayCellFactory(picker -> new DateCell() {
//                    @Override
//                    public void updateItem(LocalDate date, boolean empty) {
//                        super.updateItem(date, empty);
//                        setDisable(date.isBefore(minDate) || date.isAfter(maxDate));
//                    }
//                });
//                departmentPicker.setDisable(true); // Disable the departmentPicker initially
//                timePicker.setDisable(true); // Disable the timePicker initially
//                appointmentDatePicker.valueProperty().addListener((observable, oldValue, newValue) ->
//                {
//                    if (newValue != null) {
//                        // If a date is selected, enable the departmentPicker and disable the timePicker
//                        departmentPicker.setDisable(false);
//                        timePicker.setDisable(true);
//                    } else {
//                        // If no date is selected, disable both the departmentPicker and the timePicker
//                        departmentPicker.setDisable(true);
//                        timePicker.setDisable(true);
//                    }
//                });
//                departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
//                {
//                    if (newValue != null) {
//                        // If a department is selected, enable the timePicker
//                        timePicker.setDisable(false);
//                        selectedDept = newValue;
//                        System.out.println(selectedDate);
//                        times = dbapp.searchForAppointment(selectedDate, newValue, times);
//                        timePicker.getItems().addAll(times);
//                    } else {
//                        // If no department is selected, disable the timePicker
//                        timePicker.setDisable(true);
//                    }
//                });
//            }
//            else{
//                departmentPicker.setDisable(true); // Disable the departmentPicker initially
//                timePicker.setDisable(true); // Disable the timePicker initially
//                appointmentDatePicker.setDisable(true);
//
//            }

        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
    public void getDecision(ActionEvent event)
    {
        if(rChange.isSelected())
        {
            type = 1 ;
        }
        else if (rDiscard.isSelected()){
            type = 2 ;
        }
        else{
            type = 3 ;
        }
    }
    public Date getSelectedDate(ActionEvent event)
    {
        LocalDate localDate = appointmentDatePicker.getValue();
        Date date = Date.valueOf(localDate);
        selectedDate = date ;
        return date ;
    }
    public void confirm(ActionEvent event){
        dbConnectPatient dbPatient = new dbConnectPatient();
        dbPatient.updatePatient(patient);
        if(type == 1 )
        {

        }
        else if (type == 2)
        {

        }
    }
}
