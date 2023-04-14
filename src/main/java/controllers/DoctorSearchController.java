package controllers;

import datamodel.Doctor;
import datamodel.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

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

    ObservableList<Doctor> doctorObservableList = FXCollections.observableArrayList() ;
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
            resultSet = statement.executeQuery("Select * From doctor;");
            while(resultSet.next())
            {
                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                String id = resultSet.getString("id");
                String department = resultSet.getString("department");

                doctorObservableList.add(new Doctor(id , name , name , new Date() , 33,"mael" , "dibo na "  , new Date() , "" , department , description));
            }
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
}
