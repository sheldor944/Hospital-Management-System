package controllers;

import datamodel.Authorizer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController extends Controller implements Initializable {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        errorLabel.setText("");
        usernameTextField.setPromptText("Username");
        passwordField.setPromptText("Password");
    }

    public void login(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if(Authorizer.authorize(username, password)){
//            try {
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/menu.fxml"));
//                System.out.println("Loaded");
//                MenuController menuController = loader.getController();
//                System.out.println("Got class");
//                menuController.foo();
//                System.out.println("Successfully set");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
//            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//            stage.setScene(new Scene(root));
//            stage.show();
            switchToMenu(event);
        } else {
            errorLabel.setText("The username and password don't match. Try again.");
        }
    }
}
