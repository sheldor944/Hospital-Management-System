package controllers;

import datamodel.Patient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.*;

import java.io.IOException;

public class DoctorController extends Controller {


    @FXML
     void addDoctor(ActionEvent event) throws IOException {
//        System.out.println("hello");
        root = FXMLLoader.load(getClass().getResource("/fxml/AddDoctor.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


    public void modifyDoctor(ActionEvent event) {

    }

//    @FXML
//    void returnToMenu(ActionEvent event) throws IOException {
//
//    }


    public void  searchDoctor(ActionEvent event) {

    }

}
