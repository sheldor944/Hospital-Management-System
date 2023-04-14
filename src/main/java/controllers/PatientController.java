package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientController extends Controller {
    public void addPatient(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/addPatient.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void modifyPatient(ActionEvent event) {

    }

    public void searchPatient(ActionEvent event) throws  IOException {
        root = FXMLLoader.load(getClass().getResource("/fxml/PatientSearch.fxml"));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
