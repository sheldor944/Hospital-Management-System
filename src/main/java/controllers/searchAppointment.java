package controllers;

import database.dbConnectAppointment;
import database.dbConnectPatient;
import datamodel.appointmentModel;
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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.transformation.SortedList;
import javafx.stage.Stage;


public class searchAppointment implements Initializable {
    @FXML
    private TextField searchBarTextField;
    @FXML
    private TableView<appointmentModel> appointmentTableView;
    @FXML
    private TableColumn<appointmentModel , String> patientIdTableColumn ;
    @FXML
    private TableColumn<appointmentModel , String> doctorIdTableColumn;
    @FXML
    private TableColumn<appointmentModel , String> patientNameTableColumn;
    @FXML
    private TableColumn<appointmentModel , String> doctorNameTableColumn ;
    @FXML
    private TableColumn<appointmentModel , Timestamp> timeTableColumn;
    @FXML
    private TableColumn<appointmentModel , String> departmentTableColumn ;
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
    String department ;
    dbConnectAppointment dbapp = new dbConnectAppointment();

    ObservableList<appointmentModel> appointmentModelObservableList = FXCollections.observableArrayList();
    private ArrayList<Timestamp> times = new ArrayList<>();

    private Date selectedDate;
    private String selectedDept;
    private String[] departments = {"Medicine" , "Surgery" , "Cardiology"  , "Neurology" , "Psychiatry" , "Radiotherapy" , "ENT" , "Casualty", "Urology" , "Pediatrics" , "Dermatology" , "Gastroenterology" , "Hepatology" , "Neonatology" , "Gynecology and Obstetrics" , "Orthopedics and traumatology"}  ;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            dbConnectAppointment dbConnectAppointment = new dbConnectAppointment() ;
            appointmentModelObservableList = dbConnectAppointment.getObservableList(appointmentModelObservableList);


            patientIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));
            doctorIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));
            patientNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
            doctorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
            timeTableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            departmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

            appointmentTableView.setItems(appointmentModelObservableList);

            FilteredList<appointmentModel> appointmentFilteredList = new FilteredList<>(appointmentModelObservableList, b -> true);
            searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                appointmentFilteredList.setPredicate(appointment -> {
                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    if (appointment.getPatientName().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (appointment.getDoctorName().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else if (appointment.getDepartment().toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });

            SortedList<appointmentModel> sortedList = new SortedList<>(appointmentFilteredList);
            sortedList.comparatorProperty().bind(appointmentTableView.comparatorProperty());
            appointmentTableView.setItems(sortedList);

            appointmentTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                     patientId = newSelection.getPatientId();
                     doctorId = newSelection.getDoctorId();
                     patientName = newSelection.getPatientName();
                     doctorName = newSelection.getDoctorName();
                     time = newSelection.getTime();
                     department = newSelection.getDepartment();
                    // do something with the selected item's information
                    System.out.println(patientId);

                }
            });

            // works fine

            departmentPicker.getItems().addAll(departments);
            LocalDate minDate = LocalDate.now();
            LocalDate maxDate = minDate.plusDays(5);
            datePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(date.isBefore(minDate) || date.isAfter(maxDate));
                }
            });
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
        }
        catch (Exception e )
        {
            System.out.println(e);
        }
    }
    public void confirm(ActionEvent event)
    {
        if (appointmentTableView.getSelectionModel().getSelectedItem() != null) {
            dbapp.editAppointment(patientId, doctorId ,department  , time , doctorName , patientName , timePicker.getValue() , departmentPicker.getValue(), type);

        } else {
            // Nothing is selected, do something else
        }

    }
    public Date getSelectedDate(ActionEvent event)
    {
        LocalDate localDate = datePicker.getValue();
        Date date = Date.valueOf(localDate);
        selectedDate = date ;
        return date ;
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
