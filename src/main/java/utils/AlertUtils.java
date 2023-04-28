package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class AlertUtils {
    public static boolean hasDecidedToProceed;

    public static boolean getConfirmation(){
        hasDecidedToProceed = false;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Dialog");
        alert.setHeaderText("Are you sure you want to proceed?");
        alert.setContentText("This action cannot be undone.");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeCancel = new ButtonType("Cancel");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

        // show the alert and wait for the user's response
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeYes) {
                hasDecidedToProceed = true;
            }
        });

        return hasDecidedToProceed;
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(
                Alert.AlertType.CONFIRMATION,
                message,
                ButtonType.OK
        );
        alert.showAndWait();
    }
}
