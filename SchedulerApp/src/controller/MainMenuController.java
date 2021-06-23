package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
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
import utilities.DBUser;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class MainMenuController implements Initializable {

    /**
     * Sets stage for displaying scene
     */
    Stage stage;

    /**
     * Sets scene for displaying FXML
     */
    Parent scene;

    /**
     * Button for switching window and showing upcoming appointments
     */
    @FXML
    private Button upcomingBtn;

    /**
     * Filtered list of upcoming appointments
     */
    private static FilteredList<Appointment> upcomingAppointments;

    /**
     * Grabs upcoming appointments with a lambda that filters based on time between current time and next 15 minutes
     * @return
     */
    public static FilteredList<Appointment> getUpcomingAppointments() {
        DBUser.getAllAppointments().clear();
        LocalTime now = LocalDateTime.now().toLocalTime();
        LocalTime minutes15 = now.plusMinutes(15);
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBUser.getAllAppointments());
        filteredAppointments.setPredicate(appointment -> {

            LocalDateTime appointmentStartDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);
            LocalTime appointmentStartTime = appointmentStartDateTime.toLocalTime();
            LocalDateTime appointmentEndDateTime = LocalDateTime.parse(appointment.getEnd(), DBAppointment.dtf);
            LocalTime appointmentEndTime = appointmentEndDateTime.toLocalTime();

            return ((appointmentStartTime.isAfter(now) || appointmentStartTime.equals(now)) && (appointmentStartTime.equals(minutes15) || appointmentStartTime.isBefore(minutes15))
                    || (appointmentEndTime.equals(minutes15) || appointmentEndTime.isBefore(minutes15)));
        });
        return upcomingAppointments;
    }

    /**
     * Refreshes the main menu window (checks for upcoming appointments)
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches window to appointment main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches window to customer main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustomersMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Closes the program
     * @param event
     */
    @FXML
    void onActionExit(ActionEvent event) {
        // Exit confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit Program");
        alert.setContentText("Exit program?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**
     * Switches window to reports main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReportsAll(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches window to appointment main menu to display upcoming appointments, disabled if no upcoming appointments
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionUpcomingAppointments(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Pop-up to display if upcoming appointments for current user. Uses a lambda to filter for list of appointments for current user between current time and next 15 minutes
     */
    public void upcomingAppointmentAlert() {
        DBUser.getAllAppointments().clear();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime minutes15 = now.plusMinutes(15);
        DBAppointment.getAllAppointments().clear();
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBUser.getAllAppointments());
        filteredAppointments.setPredicate(appointment -> {

            LocalDateTime appointmentStartDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);

            return ((appointmentStartDateTime.isAfter(now)
                    || appointmentStartDateTime.equals(now))
                    && (appointmentStartDateTime.equals(minutes15)
                    || appointmentStartDateTime.isBefore(minutes15)));
        });
        if (filteredAppointments.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Upcoming Appointments");
            alert.setContentText("There are no upcoming appointments.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Upcoming Appointments");
            alert.setContentText("You have appointments in the next 15 minutes.\nClick on the Upcoming Appointments button to view.");
            Optional<ButtonType> result = alert.showAndWait();
            upcomingBtn.setDisable(false);

        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        upcomingBtn.setDisable(true);
        upcomingAppointmentAlert();
    }

}
