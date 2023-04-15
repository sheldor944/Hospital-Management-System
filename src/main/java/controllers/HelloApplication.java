package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
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