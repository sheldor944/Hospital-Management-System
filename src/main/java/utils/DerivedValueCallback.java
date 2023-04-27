package utils;

import database.dbConnectAppointment;
import database.dbConnectDoctor;
import database.dbConnectPatient;
import datamodel.Appointment;
import datamodel.Doctor;
import datamodel.Patient;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DerivedValueCallback implements Callback<TableColumn.CellDataFeatures<Appointment, String>, ObservableValue<String>> {

    private final String propertyName;

    public DerivedValueCallback(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Appointment, String> cellData) {
        // Get the patient object from the row data
        Appointment appointment = cellData.getValue();

        // Get the property value and concatenate with "Derived from"
        String derivedValue = "";
        if(propertyName.equals("patientName")){
            Patient patient = new dbConnectPatient().getPatientByID(appointment.getPatientID());
            derivedValue += patient.getFirstName() + " " + patient.getLastName();
        }
        else if(propertyName.equals("doctorName")){
            Doctor doctor = new dbConnectDoctor().getDoctorByID(appointment.getDoctorID());
            derivedValue += doctor.getFirstName() + " " + doctor.getLastName();
        }
        else if(propertyName.equals("date")) {
            derivedValue = appointment.getDate().toString();
        } else if (propertyName.equals("time")) {
            derivedValue = appointment.getTime().toString();
        } else {
            derivedValue = appointment.getDepartment();
        }

        // Return the derived value as an observable value
        return new SimpleStringProperty(derivedValue);
    }
}
