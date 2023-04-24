package controllers;

import database.dbConnectPatient;
import datamodel.Patient;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

//import java.lang.foreign.MemoryLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PatientSeachController extends Controller implements Initializable {
    @FXML
    private TableView<Patient> patientTableView ;
    @FXML
    private TableColumn<Patient , String> patientIdTableColumn ;
    @FXML
    private TableColumn<Patient,String> firstNameTableColumn ;
    @FXML
    private TableColumn<Patient, String> lastNameTableColumn ;
    @FXML
    private TableColumn<Patient , Integer> ageTableColumn ;
    @FXML
    private TextField searchBarTextField ;
    ObservableList<Patient> patientObservableList;
    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        try{
            dbConnectPatient database = new dbConnectPatient() ;
            patientObservableList = database.getAllPatients();

            patientIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));

            patientTableView.setItems(patientObservableList);

            FilteredList<Patient> patientFilteredList =
                    new FilteredList<>(patientObservableList , b -> true);
            searchBarTextField.textProperty().addListener(
                (observable  , oldValue , newValue) -> {
                    patientFilteredList.setPredicate(patient -> {
                        if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
                            return true;
                        }
                        String searchKeyword = newValue.toLowerCase();
                        if (patient.getFirstName().toLowerCase().indexOf(searchKeyword) > -1) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                );
            });

            SortedList<Patient> sortedList = new SortedList<>(patientFilteredList);
            sortedList.comparatorProperty().bind(patientTableView.comparatorProperty());
            patientTableView.setItems(sortedList);

//            handle selection
            patientTableView.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2){
                    Patient patient =
                            patientTableView.getSelectionModel().getSelectedItem();
                    if(patient != null){
                        switchToDisplayPatient(e, patient);
                    }
                }
            });
        }
        catch(Exception e )
        {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    private void switchToDisplayPatient(MouseEvent event, Patient patient) {
        try {
            FXMLLoader loader =
                    new FXMLLoader(getClass().getResource("/fxml/DisplayPatient.fxml"));
            root = loader.load();

            DisplayPatientController controller = (DisplayPatientController) loader.getController();
            controller.setPatient(patient);
            controller.init();

            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }

    @FXML
    void returnToPatientPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Patient.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
