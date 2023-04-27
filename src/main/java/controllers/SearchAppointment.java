package controllers;

import database.dbConnectAppointment;
import datamodel.Appointment;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;
import utils.DerivedValueCallback;


public class SearchAppointment implements Initializable {
    @FXML
    private TextField searchBarTextField;
    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment , String> patientNameTableColumn;
    @FXML
    private TableColumn<Appointment, String> doctorNameTableColumn ;
    @FXML
    private TableColumn<Appointment, String> dateTableColumn;
    @FXML
    private TableColumn<Appointment, String> timeTableColumn;
    @FXML
    private DatePicker datePicker ;
    @FXML
    private ChoiceBox<Timestamp> timePicker;
    @FXML
    private ChoiceBox<String> departmentPicker;
    @FXML
    private RadioButton rButtonEdit, rButtonDiscard;

    Parent root;
    Stage stage;
    Scene scene;


    int type ;
    String patientId ;
    String doctorId ;
    String patientName ;
    String doctorName ;
    Timestamp time ;
    String department;

    ObservableList<Appointment> appointmentModelObservableList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            System.out.println("Connecting to Appointment Table...");
            dbConnectAppointment database = new dbConnectAppointment();
            appointmentModelObservableList = database.getAllAppointments();

            System.out.println("Setting table columns...");
            patientNameTableColumn.setCellValueFactory(new DerivedValueCallback("patientName"));
            doctorNameTableColumn.setCellValueFactory(new DerivedValueCallback("doctorName"));
            dateTableColumn.setCellValueFactory(new DerivedValueCallback("date"));
            timeTableColumn.setCellValueFactory(new DerivedValueCallback("time"));
//            departmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

            System.out.println("Setting items on the table...");
            appointmentTableView.setItems(appointmentModelObservableList);
//
//            FilteredList<Appointment> appointmentFilteredList = new FilteredList<>(appointmentModelObservableList, b -> true);
//            searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
//                appointmentFilteredList.setPredicate(appointment -> {
//                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
//                        return true;
//                    }
//                    String searchKeyword = newValue.toLowerCase();
//                    if (appointment.getPatientName().toLowerCase().contains(searchKeyword)) {
//                        return true;
//                    } else if (appointment.getDoctorName().toLowerCase().contains(searchKeyword)) {
//                        return true;
//                    } else if (appointment.getDepartment().toLowerCase().contains(searchKeyword)) {
//                        return true;
//                    } else {
//                        return false;
//                    }
//                });
//            });
//
//            SortedList<appointmentModel> sortedList = new SortedList<>(appointmentFilteredList);
//            sortedList.comparatorProperty().bind(appointmentTableView.comparatorProperty());
//            appointmentTableView.setItems(sortedList);
//
//            appointmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//                if (newSelection != null) {
//                     patientId = newSelection.getPatientId();
//                     doctorId = newSelection.getDoctorId();
//                     patientName = newSelection.getPatientName();
//                     doctorName = newSelection.getDoctorName();
//                     time = newSelection.getTime();
//                     department = newSelection.getDepartment();
//                    // do something with the selected item's information
//                    System.out.println(patientId);
//
//                }
//            });
//
//            // works fine
//
//            departmentPicker.getItems().addAll(departments);
//            LocalDate minDate = LocalDate.now();
//            LocalDate maxDate = minDate.plusDays(5);
//            datePicker.setDayCellFactory(picker -> new DateCell() {
//                @Override
//                public void updateItem(LocalDate date, boolean empty) {
//                    super.updateItem(date, empty);
//                    setDisable(date.isBefore(minDate) || date.isAfter(maxDate));
//                }
//            });
//            departmentPicker.setDisable(true); // Disable the departmentPicker initially
//            timePicker.setDisable(true); // Disable the timePicker initially
//            datePicker.valueProperty().addListener((observable , oldValue , newValue ) ->
//            {
//                if (newValue != null) {
//                    // If a date is selected, enable the departmentPicker and disable the timePicker
//                    departmentPicker.setDisable(false);
//                    timePicker.setDisable(true);
//                } else {
//                    // If no date is selected, disable both the departmentPicker and the timePicker
//                    departmentPicker.setDisable(true);
//                    timePicker.setDisable(true);
//                }
//            });
//            departmentPicker.getSelectionModel().selectedItemProperty().addListener((observable , oldValue , newValue ) ->
//            {
//                if (newValue != null) {
//                    // If a department is selected, enable the timePicker
//                    timePicker.setDisable(false);
//                    selectedDept = newValue ;
//
//                    times = dbapp.searchForAppointment(selectedDate,newValue , times);
//                    timePicker.getItems().addAll(times);
//                } else {
//                    // If no department is selected, disable the timePicker
//                    timePicker.setDisable(true);
//                }
//            });
        }
        catch (Exception e )
        {
            System.out.println(e);
        }
    }
    public void confirm(ActionEvent event)
    {
//        if (appointmentTableView.getSelectionModel().getSelectedItem() != null) {
//            dbapp.editAppointment(patientId, doctorId ,department  , time , doctorName , patientName , timePicker.getValue() , departmentPicker.getValue(), type);
//
//        } else {
//            // Nothing is selected, do something else
//        }

    }
    public Date getSelectedDate(ActionEvent event)
    {
//        LocalDate localDate = datePicker.getValue();
//        Date date = Date.valueOf(localDate);
//        selectedDate = date ;
//        return date ;
        return new Date(23);
    }
    public void getDecision(ActionEvent event)
    {
        if(rButtonEdit.isSelected())
        {
            type = 1 ;
        }
        else{
            type = 2 ;
        }
    }
    public void switchToMenu(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
