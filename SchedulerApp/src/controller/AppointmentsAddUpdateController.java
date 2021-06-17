/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.AppointmentsMainController.getUpdatedAppointment;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Customer;
import utilities.DBAppointment;
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class AppointmentsAddUpdateController implements Initializable {
    
    Stage stage;
    
    Parent scene;
    
    private final Appointment updatingAppointment;

    public Appointment getUpdatingAppointment() {
        return updatingAppointment;
    }

    /**
     * Constructor
     */
    public AppointmentsAddUpdateController() {
        this.updatingAppointment = getUpdatedAppointment();
    }

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private Label appointmentLbl;

    @FXML
    private TextField autoIDTxt;

    @FXML
    private TextField titleTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private ComboBox<String> startTimeComboBox;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<String> endTimeComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField locationTxt;

    @FXML
    private ComboBox<String> userComboBox;

    @FXML
    private ComboBox<String> customerNameComboBox;
    
    @FXML
    private ComboBox<Integer> customerIDComboBox;
    
    @FXML
    void onActionSetID(ActionEvent event) {

    }

    @FXML
    void onActionSetName(ActionEvent event) {

    }

    @FXML
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    public void windowSetup() {
        typeComboBox.getItems().addAll(
                "Status Update",
                "Planning Session",
                "Training",
                "Brainstorming",
                "Meet and Greet",
                "Luncheon",
                "Proposal",
                "De-Briefing",
                "Project Kickoff");
        contactComboBox.setItems(DBAppointment.getAllContactNames());
        customerNameComboBox.setItems(DBAppointment.getAllCustomerNames());
        userComboBox.setItems(DBAppointment.getAllUserNames());
        userComboBox.setItems(DBAppointment.getAllCustomerIDs());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        windowSetup();
        if (updatingAppointment == null) {
            appointmentLbl.setText("Add Appointment");
        }
        else{
            appointmentLbl.setText("Update Appointment");
            autoIDTxt.setText(Integer.toString(updatingAppointment.getAppointmentID()));
            typeComboBox.setValue(updatingAppointment.getType());
            titleTxt.setText(updatingAppointment.getTitle());
            descriptionTxt.setText(updatingAppointment.getDescription());
            locationTxt.setText(updatingAppointment.getLocation());
            contactComboBox.setPromptText(updatingAppointment.getContactName());
            startTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getEnd(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            endDatePicker.setValue(LocalDate.parse(updatingAppointment.getEnd(), DBAppointment.dtf));
            endTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getEnd(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            userComboBox.setValue(updatingAppointment.getUserName());
            customerNameComboBox.setValue(updatingAppointment.getApptCustomerName());
            customerIDComboBox.setValue(updatingAppointment.getApptCustomerID());
        }
    }    
    
}
