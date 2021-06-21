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
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.paint.Color;
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
    private final ObservableList<String> startOptions = FXCollections.observableArrayList();
    private final ObservableList<String> endOptions = FXCollections.observableArrayList();
    ZoneId officeZone = ZoneId.of("America/New_York");
    LocalDateTime systemDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    LocalDateTime officeDateTime = LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime();
    

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
    private Label startEST;
    
    @FXML
    private Label endEST;
    
    @FXML
    void onActionshowEnd(ActionEvent event) {
        LocalDate selectedEndDate = endDatePicker.getValue();
        LocalTime selectedEndTime = LocalTime.parse(endTimeComboBox.getValue());
        LocalDate selectedStartDate = startDatePicker.getValue();
        LocalTime selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalDateTime localStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (selectedEndDate != null) {
            if (((selectedStartDate != null) && (selectedStartTime != null))) {
                if (localEndTime.isAfter(localStartTime.plusMinutes(1))) {
                    if (hoursEndCheck()) {
                        endEST.setText(localEndTime.format(DBAppointment.dtf) + " " + ZoneId.systemDefault().toString());
                        endEST.setTextFill(Color.WHITE);
                        endEST.setVisible(true);
                        } else {
                            endEST.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                            endEST.setTextFill(Color.RED);
                            endEST.setVisible(true);
                        }
                } else {
                    endEST.setText("Appointment end time must be after start time.");
                    endEST.setTextFill(Color.RED);
                    endEST.setVisible(true);
                }
            }
        }
    }

    @FXML
    void onActionshowStart(ActionEvent event) {
        LocalDate selectedStartDate = startDatePicker.getValue();
        LocalTime selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalDateTime localStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (selectedStartDate != null) {
            if (hoursStartCheck()) {
                startEST.setText(localStartTime.format(DBAppointment.dtf) + " " + ZoneId.systemDefault().toString());
                startEST.setTextFill(Color.WHITE);
                startEST.setVisible(true);
            } else {
                startEST.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                startEST.setTextFill(Color.RED);
                startEST.setVisible(true);
            }
        }
    }
    
    @FXML
    void onActionSetID(ActionEvent event) {
        String name = customerNameComboBox.getSelectionModel().getSelectedItem();
        customerIDComboBox.setValue(DBCustomer.getCustomerID(name));
    }

    @FXML
    void onActionSetName(ActionEvent event) {
        int id = customerIDComboBox.getSelectionModel().getSelectedItem();
        customerNameComboBox.setValue(DBCustomer.getCustomerName(id));
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
        startEST.setTextFill(Color.WHITE);
        endEST.setTextFill(Color.WHITE);
        endEST.setVisible(false);
        startEST.setVisible(false);
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
        customerIDComboBox.setItems(DBAppointment.getAllCustomerIDs());
        
        LocalTime time = systemDateTime.toLocalTime().of(0, 0);
        startOptions.add(time.format(DBAppointment.timeDTF));
        endOptions.add(time.format(DBAppointment.timeDTF));        
        time = systemDateTime.toLocalTime().of(0, 30);
        do {
		startOptions.add(time.format(DBAppointment.timeDTF));
		endOptions.add(time.format(DBAppointment.timeDTF));
		time = time.plusMinutes(30);
	} while(!time.equals(LocalTime.of(0, 0)));
		startOptions.remove(startOptions.size() - 1);
		endOptions.remove(0);
                startTimeComboBox.getItems().setAll(startOptions);
                endTimeComboBox.getItems().setAll(endOptions);
    }
    
    public Boolean hoursStartCheck() {
        LocalDate selectedStartDate = startDatePicker.getValue();
        LocalTime selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalDateTime selectedStartDateTime = systemDateTime.of(selectedStartDate, selectedStartTime);
        LocalTime officeStartTime = selectedStartDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(officeZone).toLocalDateTime().toLocalTime();
        boolean check = false;
        if (officeStartTime.isAfter(LocalTime.MIDNIGHT.plusHours(7).plusMinutes(30)) && officeStartTime.isBefore(LocalTime.MIDNIGHT.minusHours(1).minusMinutes(30))) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }
    
    public Boolean hoursEndCheck() {
        LocalDate selectedEndDate = endDatePicker.getValue();
        LocalTime selectedEndTime = LocalTime.parse(endTimeComboBox.getValue());
        LocalDateTime selectedEndDateTime = systemDateTime.of(selectedEndDate, selectedEndTime);
        LocalTime officeEndTime = selectedEndDateTime.atZone(ZoneId.systemDefault()).withZoneSameInstant(officeZone).toLocalDateTime().toLocalTime();
        boolean check = false;
        if (officeEndTime.isAfter(LocalTime.MIDNIGHT.plusHours(8)) && officeEndTime.isBefore(LocalTime.MIDNIGHT.minusHours(1))) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }
    
    public Boolean datesCheck() {
        boolean check = false;
        LocalDate selectedEndDate = endDatePicker.getValue();
        LocalTime selectedEndTime = LocalTime.parse(endTimeComboBox.getValue());
        LocalDate selectedStartDate = startDatePicker.getValue();
        LocalTime selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        LocalDateTime localStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (localStartTime.isBefore(localEndTime) && (localStartTime.isAfter(LocalDateTime.now()))) {
            check = true;
        } else {
            check = false;
        }
        return check;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        windowSetup();
        if (updatingAppointment == null) {
            appointmentLbl.setText("Add Appointment");
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeComboBox.setValue("8:00");
            endTimeComboBox.setValue("22:00");
        }
        else{
            appointmentLbl.setText("Update Appointment");
            autoIDTxt.setText(Integer.toString(updatingAppointment.getAppointmentID()));
            typeComboBox.setValue(updatingAppointment.getType());
            titleTxt.setText(updatingAppointment.getTitle());
            descriptionTxt.setText(updatingAppointment.getDescription());
            locationTxt.setText(updatingAppointment.getLocation());
            contactComboBox.setPromptText(updatingAppointment.getContactName());
            startDatePicker.setValue(LocalDate.parse(updatingAppointment.getStart(), DBAppointment.dtf));
            startTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getStart(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            endDatePicker.setValue(LocalDate.parse(updatingAppointment.getEnd(), DBAppointment.dtf));
            endTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getEnd(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            userComboBox.setValue(updatingAppointment.getUserName());
            customerNameComboBox.setValue(updatingAppointment.getApptCustomerName());
            customerIDComboBox.setValue(updatingAppointment.getApptCustomerID());
        }
    }    
    
}
