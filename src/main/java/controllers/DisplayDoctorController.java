package controllers;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import datamodel.Doctor;
import datamodel.Hospital;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.AlertUtils;

import java.io.IOException;
import java.time.LocalDate;

public class DisplayDoctorController extends Controller {

    @FXML
    private TextField ageField;

    @FXML
    private DatePicker dateOfBirthPicker;

    @FXML
    private ChoiceBox<String> departmentPicker;

    @FXML
    private TextArea descriptionField;

    @FXML
    private Label errorLabel;

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

    private Doctor doctor;

    public void setDoctor(Doctor doctor){
        this.doctor = doctor;
    }

    public void init(){
        departmentPicker.getItems().addAll(Hospital.getDepartments());
        firstNameField.setText(doctor.getFirstName());
        lastNameField.setText(doctor.getLastName());
        dateOfBirthPicker.setValue(doctor.getDateOfBirth());
        ageField.setText(Integer.toString(doctor.getAge()));
        genderField.setText(doctor.getGender());
        joiningDatePicker.setValue(doctor.getJoiningDate());
        mobileField.setText(doctor.getMobile());
        postField.setText(doctor.getPost());
        departmentPicker.getSelectionModel().select(doctor.getDepartment());
        descriptionField.setText(doctor.getDescription());
    }

    @FXML
    void modifyButtonClicked(ActionEvent event) {
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
            if(!AlertUtils.getConfirmation()) {
                System.out.println("Cancelling doctor modification...");
                init();
                return;
            }

            int age = Integer.parseInt(ageInString);

            dbConnectDoctor database = new dbConnectDoctor();

            Doctor updatedDoctor = new Doctor(
                    doctor.getId(),
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
            database.updateDoctor(doctor.getId(), updatedDoctor);
            database.close();

            AlertUtils.showAlert("The information has been updated.");
        }
    }

    @FXML
    void deleteDoctorClicked(ActionEvent event) throws IOException {
        if(!AlertUtils.getConfirmation()) {
            System.out.println("Cancelling doctor delete...");
            return;
        }

        System.out.println("Deleting doctor with ID: " + doctor.getId());
        new dbConnectAppointment()
                .deleteByDoctorID(
                        doctor.getId()
                );
        new dbConnectDoctor()
                .delete(
                        doctor.getId()
                );

        AlertUtils.showAlert("The doctor has been successfully deleted.");
        switchToDoctor(event);
    }
}
