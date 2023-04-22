package database;

import datamodel.Appointment;
import datamodel.appointmentModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class dbConnectAppointment extends  dbConnect {
    public void addAppointmentToDB(Appointment appointment){
        try {
            statement.executeUpdate(
                    "INSERT INTO APPOINTMENT "
                    + "VALUES"
                    + "("
                    + "'" + appointment.getDoctorID() + "', "
                    + "'" + appointment.getPatientID() + "', "
                    + "'" + appointment.getDate() + "', "
                    + "'" + appointment.getTime() + "', "
                    + "'" + appointment.getDepartment() + "'"
                    + ")"
            );
        }
        catch (Exception e ){
            System.out.println(e);
        } finally {
            close();
        }
    }

//    returns true if such an appointment is already present
    public boolean searchAppointment(Appointment appointment){
        boolean found = false;
        try {
            resultSet = statement.executeQuery(
                    "SELECT * FROM APPOINTMENT WHERE "
                    + "DOCTOR_ID = '" + appointment.getDoctorID() + "' AND "
                    + "DATE = '" + appointment.getDate() + "' AND "
                    + "TIME = '" + appointment.getTime() + "'"
            );
//            System.out.println(appointment.getTime());

            while(resultSet.next()){
                found = true;
                break;
            }
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            System.exit(1);
        }
        return found;
    }

    public ObservableList <LocalTime> getAvailableTimes(int doctorID, LocalDate localDate){
        ArrayList <LocalTime> availableTimes = new ArrayList<>();
        LocalTime time = LocalTime.NOON;

        while (time.compareTo(LocalTime.of(18, 0)) <= 0) {
            Appointment appointment = new Appointment(doctorID, localDate, time);
            if(!searchAppointment(appointment)) availableTimes.add(time);
            time = time.plusMinutes(20);
        }

        return FXCollections.observableArrayList(availableTimes);
    }

    public ArrayList<Timestamp> searchForAppointment(Date date , String department , ArrayList<Timestamp> time )
    {
        try {
            System.out.println("into ono  aise ");
            resultSet = statement.executeQuery("select time  from appointment where checker='0'  && department='"+department+"' && Date(time)='"+date+"';");
            System.out.println("into to aise ");
            while(resultSet.next())
            {
                time.add(resultSet.getTimestamp("time"));
            }
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        return time ;
    }
    public void addAppointment(String patientID , String doctorId , String department , Timestamp time , String doctorName , String patientName )
    {
        try {
            statement.executeUpdate("UPDATE appointment set patientId='" + patientID + "' , doctorID = '" + doctorId + "', doctorName ='" + doctorName + "', patientName='" + patientName + "',checker = 1 WHERE department='"+department + "' AND time ='"+time+"';\n");

//            statement.executeUpdate("UPDATE appointment set patientId='" + patientID + "' , doctorID = '" + doctorId + ", doctorName ='" + doctorName + "', patientName='" + patientName + "',checker = 1 WHERE department='"+department + "' AND time ='"+time+"';\n");
        }
        catch (Exception e)
        {
            System.out.println(e );
        }
    }
    public void editAppointment(String patientId ,String doctorId , String department , Timestamp time , String doctorName , String patientName ,Timestamp selectedTime , String selectedDept , int type )
    {
        if(type ==1 )
        {
            try {
                statement.executeUpdate("UPDATE appointment set patientId='" + patientId + "' , doctorID = '" + doctorId + "', doctorName ='" + doctorName + "', patientName='" + patientName + "',checker = 1 WHERE department='"+selectedDept + "' AND time ='"+selectedTime+"';\n");

                statement.executeUpdate("UPDATE appointment set checker=0 where patientId='"+patientId+"' AND time='"+time+"';");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
        else {
            try {
                statement.executeUpdate("UPDATE appointment set patientId='' , doctorId='' , doctorName='' , patientName='' , checker=0 where patientId='"+patientId+"' AND time='"+time+"';");
            }
            catch (Exception e)
            {
                System.out.println(e);
            }
        }
    }
    public ObservableList<appointmentModel> getObservableList(ObservableList<appointmentModel> appointmentObservableList) {

        try {
            resultSet = statement.executeQuery("select * from appointment where checker=1;");
            while (resultSet.next()) {
                int appointmentId = resultSet.getInt("ID");
                String patientId = resultSet.getString("patientId");
                String doctorId = resultSet.getString("doctorId");
                String patientName = resultSet.getString("patientName");
                String doctorName = resultSet.getString("doctorName");
                Timestamp appointmentTime = resultSet.getTimestamp("time");
                String department = resultSet.getString("department");

                appointmentObservableList.add(new appointmentModel(patientId, doctorId, patientName, doctorName, appointmentTime, department));
            }

        } catch (Exception e) {
            System.out.println(e);
        }

        return appointmentObservableList;
    }

    public void addAppointmentBlankTimeSlot()
    {
        try {
            Timestamp time = Timestamp.valueOf("2023-04-19 00:00:00.00");
            long t = time.getTime();
            LocalTime start = LocalTime.of(10, 00, 0);
            LocalTime end = LocalTime.of(18, 00, 00);
            long cnt = 0;
            for (long i = 0; i < 1e4; i++) {
                Timestamp now = new Timestamp(t + (long) ((i * 20) * 60 * 1000));
                LocalTime localTime = now.toLocalDateTime().toLocalTime();


                if (localTime.compareTo(start) >= 0 && localTime.compareTo(end) <= 0) {
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '' ,'' ," + "'Medicine'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Surgery'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Cardiology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Neurology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Psychiatry'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'ENT'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Radiotherapy'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Casualty'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Urology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Pediatrics'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Dermatology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Gastroenterology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Hepatology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Neonatology'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Gynecology and Obstetrics'" + ", '' , '' , '" + now + "','0');");
                    statement.executeUpdate("insert into appointment (patientID , doctorID , department , doctorName , patientName , time , checker  ) values ( '',''," + "'Orthopedics and traumatology'" + ", '' , '' , '" + now + "','0');");

//                System.out.println(now  + " condition ");
                    cnt++;
                }
                if (cnt == 1000) {
                    break;
                }

            }
        }
        catch (Exception e ){
            System.out.println(e );
        }
        System.out.println("shesh");
    }
}
