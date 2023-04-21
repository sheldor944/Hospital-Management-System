package controllers;

import database.dbConnect;
import database.dbConnectAppointment;
import database.dbConnectDoctor;
import database.dbConnectPatient;
import datamodel.Appointment;
import datamodel.Doctor;
import datamodel.Patient;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Date;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            new dbConnectAppointment().addAppointmentToDB(
                    new Appointment(
                            1,
                            2,
                            new Date(),
                            "Gynae"
                    )
            );
//            System.out.println(new dbConnectPatient().getPatientCount());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/loginPage.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hospital Management System");
            stage.setScene(scene);
            stage.show();
        } catch(Exception e){
            System.out.println("File could not be loaded");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}