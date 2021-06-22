/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.Appointment;
import utilities.DBAppointment;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    
    Parent scene;
    
    private static FilteredList<Appointment> upcomingAppointments = new FilteredList<>(DBAppointment.getAllAppointments());
    
    public static FilteredList<Appointment> getUpcomingAppointments() {
        upcomingAppointments.setPredicate(appointment -> {

            ZoneId currentZone = ZoneId.systemDefault();
            ZoneId databaseZone = ZoneId.of("UTC");
            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);
            LocalDate currentDate = LocalDateTime.now().atZone(currentZone).toLocalDate();
            LocalTime currentTime = LocalDateTime.now().atZone(currentZone).toLocalTime();
            LocalDate appointmentDate = appointmentDateTime.atZone(databaseZone).withZoneSameInstant(currentZone).toLocalDate();
            LocalTime appointmentTime = appointmentDateTime.atZone(databaseZone).withZoneSameInstant(currentZone).toLocalTime();
            return (appointmentDate.isEqual(currentDate) && appointmentTime.isAfter(currentTime.minusMinutes(1)) && appointmentTime.isBefore(currentTime.plusMinutes(15)));
        });
        return upcomingAppointments;
    }
    
    public void setUpcomingAppointments(FilteredList<Appointment> upcomingAppointments) {
        MainMenuController.upcomingAppointments = upcomingAppointments;
    }
    
    @FXML
    private Button upcomingBtn;
    
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionCustomersMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionReportsAll(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionUpcomingAppointments(ActionEvent event) {
        
    }
    
    public void upcomingAppointmentAlert() {
        getUpcomingAppointments();
        if (upcomingAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            alert.setContentText("There are no upcoming appointments.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upcoming Appointments");
            alert.setContentText("You have appointments in the next 15 minutes.");
            Optional<ButtonType> result = alert.showAndWait();
            upcomingBtn.setDisable(false);
            
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        upcomingBtn.setDisable(true);
        upcomingAppointmentAlert();
    }
    
}
