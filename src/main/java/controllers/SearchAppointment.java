package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import datamodel.Appointment;
import datamodel.Doctor;
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

            System.out.println("Setting items on the table...");
            appointmentTableView.setItems(appointmentModelObservableList);

            FilteredList<Appointment> appointmentFilteredList = new FilteredList<>(appointmentModelObservableList, b -> true);
            searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                appointmentFilteredList.setPredicate(appointment -> {
                    if (newValue == null || newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }

                    String comparingTo =
                            new dbConnectDoctor().getDoctorByID(appointment.getDoctorID()).getName();

                    String searchKeyword = newValue.toLowerCase();
                    if (comparingTo.toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else {
                        return false;
                    }
                });
            });
//
            SortedList<Appointment> sortedList = new SortedList<>(appointmentFilteredList);
            sortedList.comparatorProperty().bind(appointmentTableView.comparatorProperty());
            appointmentTableView.setItems(sortedList);
        }
        catch (Exception e )
        {
            System.out.println(e);
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
