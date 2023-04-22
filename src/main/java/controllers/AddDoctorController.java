package controllers;

import database.dbConnect;
import datamodel.Doctor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Date;

public class AddDoctorController extends  Controller {
    @FXML
    private TextField doctorName ;
    @FXML
    private TextArea doctorDescription ;
    @FXML
    private  TextField doctorDepartment ;
    public void submit(ActionEvent event )
    {
        dbConnect db = new dbConnect() ;
        String name = doctorName.getText();
        String description = doctorDescription.getText();
        String dept = doctorDepartment.getText() ;
        Date dateOfBirth = new Date();
        int age =11 ;
        String gender = "" ;
        String mobile = "";
        Date joiningDate = new Date() ;
        String post = "";

//        Doctor doctor = new Doctor( "1", name , name , dateOfBirth , age ,gender , mobile , joiningDate , post , dept , description );
//        db.addDoctorToDB(doctor);

    }
    public void returnToDoctorPage(ActionEvent event) throws IOException
    {
        root = FXMLLoader.load(getClass().getResource("/fxml/Doctor.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
