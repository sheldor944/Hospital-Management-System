package controllers;

import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

//import java.lang.foreign.MemoryLayout;
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

    ObservableList<Patient> patientObservableList = FXCollections.observableArrayList() ;

    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/acme", "root", "");
            statement = connection.createStatement();
        }
        catch (Exception e )
        {
            System.out.println(e);
        }

        try{
            resultSet = statement.executeQuery("select patientId , firstName , lastName , dateOfBirth , age , gender , mobile , symptoms from patient;");
            while(resultSet.next()){
                String patientID = resultSet.getString("patientId") ;
                String firstName = resultSet.getString("firstName") ;
                String lastName = resultSet.getString("lastName") ;
                Date dateOfBirth = resultSet.getDate("dateOfBirth");
                Integer age = resultSet.getInt("age");
                String gender = resultSet.getString("gender") ;
                String mobile = resultSet.getString("mobile") ;
                String sympotms = resultSet.getString("symptoms");

                patientObservableList.add(new Patient(patientID , firstName , lastName , dateOfBirth , age , gender , mobile , sympotms));
            }
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
        }
        catch(Exception e )
        {
            System.out.println(e);
        }
    }

}
