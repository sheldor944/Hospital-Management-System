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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

public class DoctorSearchController extends Controller implements Initializable {
    @FXML
    private TableView doctorTableView ;
    @FXML
    private TableColumn<Doctor , String> idTableColumn ;
    @FXML
    private  TableColumn<Doctor, String> firstNameTableColumn ;
    @FXML
    private  TableColumn<Doctor, String> lastNameTableColumn ;
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

    ObservableList<Doctor> doctorObservableList;
    @Override
    public void initialize(URL url , ResourceBundle resourceBundle)
    {
        try{
            dbConnectDoctor dbDoctor = new dbConnectDoctor();
            doctorObservableList = dbDoctor.getAllDoctors();

            idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            departmentTableColumn.setCellValueFactory(new PropertyValueFactory<>("department"));

            doctorTableView.setItems(doctorObservableList);

            FilteredList<Doctor> doctorFilteredList = new FilteredList<>(doctorObservableList , b -> true ) ;
            searchBarTextField.textProperty().addListener((observable , oldValue , newValue) -> {
                doctorFilteredList.setPredicate(doctor -> {
                            if(newValue.isEmpty() || newValue.isBlank() || newValue == null ){
                                return true ;
                            }
                            String searchKeyword = newValue.toLowerCase();
                            if (doctor.getFirstName().toLowerCase().indexOf(searchKeyword) > -1)
                            {
                                return true ;
                            }
                            return false;
                        }
                    );
                }
            );
            SortedList<Doctor> sortedList = new SortedList<>(doctorFilteredList);
            sortedList.comparatorProperty().bind(doctorTableView.comparatorProperty());
            doctorTableView.setItems(sortedList);

            doctorTableView.setOnMouseClicked(event -> {
                if(event.getClickCount() == 2){
                    Doctor selectedDoctor =
                            (Doctor) doctorTableView
                                    .getSelectionModel()
                                    .getSelectedItem();
                    if(selectedDoctor != null){
                        switchToDisplayDoctor(event, selectedDoctor);
                    }
                }
            });
        }
        catch (Exception e )
        {
            System.out.println(e);
        }

    }

    private void switchToDisplayDoctor(MouseEvent event, Doctor selectedDoctor) {
        FXMLLoader loader =
                new FXMLLoader(
                        getClass()
                        .getResource("/fxml/DisplayDoctor.fxml")
                );
        try {
            root = loader.load();
            DisplayDoctorController controller =
                    loader.getController();
            controller.setDoctor(selectedDoctor);
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
    void returnToDoctorPage(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/Doctor.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
