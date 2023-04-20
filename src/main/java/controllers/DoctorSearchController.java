package controllers;

import database.dbConnectDoctor;
import datamodel.Doctor;
import datamodel.Patient;
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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.Date;
import java.util.ResourceBundle;

public class DoctorSearchController implements Initializable {
    @FXML
    private TableView doctorTableView ;
    @FXML
    private TableColumn<Doctor , String> idTableColumn ;
    @FXML
    private  TableColumn<Doctor, String> nameTableColumn ;
    @FXML
    private TableColumn<Doctor , String> departmentTableColumn ;
    @FXML
    private  TableColumn<Doctor,String > descriptionTableColumn ;

    @FXML
    private TextField searchBarTextField;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Parent root ;
    private Stage stage ;
    private Scene scene ;

    ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList() ;
    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        try{
            dbConnectDoctor dbDoctor = new dbConnectDoctor() ;
            doctorObservableList =  dbDoctor.getObservableList(doctorObservableList) ;

            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            departmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("department"));
            descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

            doctorTableView.setItems(doctorObservableList);

            FilteredList<Doctor> doctorFilteredList = new FilteredList<>(doctorObservableList , b -> true ) ;
            searchBarTextField.textProperty().addListener((observable , oldValue , newValue) ->{
                doctorFilteredList.setPredicate(doctor ->{
                            if(newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                                return true ;
                            }
                            String searchKeyword = newValue.toLowerCase() ;
                            if (doctor.getFirstName().toLowerCase().indexOf(searchKeyword) > -1)
                            {
                                return true ;
                            }
                            if(doctor.getDescription().toLowerCase().indexOf(searchKeyword) > -1)
                            {
                                return  true ;
                            }
                            if(doctor.getDepartment().toLowerCase().indexOf(searchKeyword) >-1)
                            {
                                return  true ;
                            }
                            if(doctor.getId().toLowerCase().indexOf(searchKeyword) > -1)
                            {
                                return  true ;
                            }
                            else{
                                return  false ;
                            }
                        }
                        );
            }
        );
            SortedList<Doctor> sortedList = new SortedList<>(doctorFilteredList);
            sortedList.comparatorProperty().bind(doctorTableView.comparatorProperty());
            doctorTableView.setItems(sortedList);
        }
        catch (Exception e )
        {
            System.out.println(e);
        }

    }
    @FXML
    void returnToDoctorPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Doctor.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
