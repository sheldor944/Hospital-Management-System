package controllers;

import database.dbConnectDoctor;
import datamodel.Doctor;
import datamodel.Hospital;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddDoctorController extends Controller implements Initializable {

    @FXML
    private Label errorLabel;
    @FXML
    private TextField ageField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private ChoiceBox<String> departmentPicker;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField genderField;

    @FXML
    private DatePicker joiningDatePicker;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField mobileField;

    @FXML
    private TextField postField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        departmentPicker.getItems().addAll(Hospital.getDepartments());
    }

    @FXML
    void submit(ActionEvent event) throws IOException {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        LocalDate dateOfBirth = dateOfBirthPicker.getValue();
        String ageInString = ageField.getText();
        String gender = genderField.getText();
        String mobile = mobileField.getText();
        LocalDate joiningDate = joiningDatePicker.getValue();
        String post = postField.getText();
        String department = departmentPicker.getValue();
        String description = descriptionField.getText();

        if(firstName.isEmpty()
            || lastName.isEmpty()
            || dateOfBirth == null
            || ageInString.isEmpty()
            || gender.isEmpty()
            || mobile.isEmpty()
            || joiningDate == null
            || post.isEmpty()
            || department == null
            || description.isEmpty()){
            errorLabel.setText("PLEASE FILL UP ALL THE INFORMATION.");
        }
        else{
            int age = Integer.parseInt(ageInString);

            dbConnectDoctor database = new dbConnectDoctor();
            int id = database.getDoctorCount();
            database.increaseDoctorCount();

            Doctor doctor = new Doctor(
                    id,
                    firstName,
                    lastName,
                    dateOfBirth,
                    age,
                    gender,
                    mobile,
                    joiningDate,
                    post,
                    department,
                    description
            );
            database.addDoctorToDB(doctor);
            database.close();

            Alert alert =
                    new Alert(
                            Alert.AlertType.CONFIRMATION,
                            "Doctor successfully created.",
                            ButtonType.OK
                    );
            alert.showAndWait();
            switchToDoctor(event);
        }
    }
}
