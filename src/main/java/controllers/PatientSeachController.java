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
import javafx.stage.Stage;

//import java.lang.foreign.MemoryLayout;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PatientSeachController implements Initializable {
    @FXML
    private TableView<Patient> patientTableView ;
    @FXML
    private TableColumn<Patient , String> patientIdTableColumn ;
    @FXML
    private TableColumn<Patient,String> firstNameTableColumn ;
    @FXML
    private TableColumn<Patient, String> lastNameTableColumn ;
    @FXML
    private TableColumn<Patient , DatePicker> dateOfBirthTableColumn ;
    @FXML
    private TableColumn<Patient , Integer> ageTableColumn ;
    @FXML
    private TableColumn<Patient , String> genderTableColumn ;
    @FXML
    private TableColumn<Patient , String > moileNumberTableColumn ;
    @FXML
    private TableColumn<Patient , String> symptomsTableColumn ;
    @FXML
    private TextField searchBarTextField ;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Parent root ;
    private Stage stage ;
    private Scene scene ;


    ObservableList<Patient> patientObservableList = FXCollections.observableArrayList() ;

    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        try{
            dbConnectPatient dbPatient = new dbConnectPatient() ;
            patientObservableList = dbPatient.getObservableList(patientObservableList) ;

            patientIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            dateOfBirthTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
            ageTableColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
            genderTableColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
            moileNumberTableColumn.setCellValueFactory(new PropertyValueFactory<>("mobile"));
            symptomsTableColumn.setCellValueFactory(new PropertyValueFactory<>("symptoms"));

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
                    else if (patient.getLastName().toLowerCase().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
                    else if (patient.getGender().toLowerCase().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
                    else if (patient.getMobile().toLowerCase().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
                    else if (patient.getSymptoms().toLowerCase().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
                    else if (patient.getAge().toString().indexOf(searchKeyword) > -1 )
                    {
                        return true ;
                    }
//                    else if ((Integer)(patient.getId()).toString().toLowerCase().indexOf(searchKeyword) > -1 )
//                    {
//                        return true ;
//                    }
                    else{
                        return false ;
                    }
                });
            });

            SortedList<Patient> sortedList = new SortedList<>(patientFilteredList);
            sortedList.comparatorProperty().bind(patientTableView.comparatorProperty());
            patientTableView.setItems(sortedList);

            // selected method er jonno nicher code
            patientTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Patient>() {
                @Override
                public void changed(ObservableValue<? extends Patient> observableValue, Patient patient, Patient t1) {
                    int rowIndex = ( (patientTableView.getSelectionModel().getSelectedIndex()));
                    Patient selectedPatient = patientTableView.getItems().get(rowIndex);
                    System.out.println(selectedPatient.getFirstName());
                }
            });

        }
        catch(Exception e )
        {
            System.out.println(e);
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
