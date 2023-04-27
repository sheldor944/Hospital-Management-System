package controllers;

import database.dbConnectLoginInfo;
import datamodel.LoginInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.AlertUtils;

import java.io.IOException;

public class UpdateLoginInfoController extends Controller {

    @FXML
    private Label errorLabel;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField usernameTextField;

    @FXML
    void save(ActionEvent event) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if(username.isEmpty() || password.isEmpty()){
            errorLabel.setText("Please fill up both of the fields.");
            return;
        }

        if(AlertUtils.getConfirmation()) {
            LoginInfo loginInfo = new LoginInfo(username, password);
            new dbConnectLoginInfo().setLoginInfo(loginInfo);

            Alert alert = new Alert(
                    Alert.AlertType.CONFIRMATION,
                    "New login info successfully saved.",
                    ButtonType.OK
            );
            alert.showAndWait();
            switchToMenu(event);
        }
    }

}
