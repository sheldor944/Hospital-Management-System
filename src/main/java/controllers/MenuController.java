package controllers;

import database.dbConnectLoginInfo;
import datamodel.LoginInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController extends Controller implements Initializable {
    private String username;

    @FXML
    private Button logoutButton;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button doctorButton;

    @FXML
    private Button patientButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoginInfo loginInfo =
                new dbConnectLoginInfo().getLoginInfo();
        String username = loginInfo.getUsername();
        String welcomeText = "Welcome " + username + "!";
        welcomeLabel.setText(welcomeText);
    }

    public void logout(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/loginPage.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    void switchToUpdateLoginInfo(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/UpdateLoginInfo.fxml"));
        Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void setUsername(String username){
        this.username = username;
    }
}
