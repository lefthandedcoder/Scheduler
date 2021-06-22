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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Appointment;
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
    ZoneId currentZone = ZoneId.systemDefault();
    LocalDate selectedEndDate;
    LocalTime selectedEndTime;
    LocalDate selectedStartDate;
    LocalTime selectedStartTime;
    LocalDateTime localStartTime;
    LocalDateTime localEndTime;
    LocalDateTime systemDateTime = LocalDateTime.now().atZone(currentZone).toLocalDateTime();
    LocalDateTime officeDateTime = LocalDateTime.now().atZone(currentZone).toLocalDateTime();
    

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
    private TextArea errorDisplay;
    
    @FXML
    void onActionshowEnd(ActionEvent event) {
        selectedEndDate = endDatePicker.getValue();
        selectedEndTime = LocalTime.parse(endTimeComboBox.getValue());
        selectedStartDate = startDatePicker.getValue();
        selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        localStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(currentZone).toLocalDateTime();
        localEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(currentZone).toLocalDateTime();
        if (selectedEndDate != null) {
            if (((selectedStartDate != null) && (selectedStartTime != null))) {
                if (localEndTime.isAfter(localStartTime.plusMinutes(1))) {
                    if (hoursEndCheck()) {
                        startEST.setText(localStartTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        startEST.setTextFill(Color.WHITE);
                        startEST.setVisible(true);
                        endEST.setText(localEndTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        endEST.setTextFill(Color.WHITE);
                        endEST.setVisible(true);
                        } else {
                            startEST.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                            startEST.setTextFill(Color.RED);
                            startEST.setVisible(true);
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
        selectedEndDate = endDatePicker.getValue();
        selectedEndTime = LocalTime.parse(endTimeComboBox.getValue());
        selectedStartDate = startDatePicker.getValue();
        selectedStartTime = LocalTime.parse(startTimeComboBox.getValue());
        localStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(currentZone).toLocalDateTime();
        localEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(currentZone).toLocalDateTime();
        if (selectedStartDate != null) {
            if (((selectedEndDate != null) && (selectedStartTime != null))) {
                if (localStartTime.isBefore(localEndTime.plusMinutes(1))) {
                    if (hoursStartCheck()) {
                        startEST.setText(localStartTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        startEST.setTextFill(Color.WHITE);
                        startEST.setVisible(true);
                        endEST.setText(localEndTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        endEST.setTextFill(Color.WHITE);
                        endEST.setVisible(true);
                        } else {
                            startEST.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                            startEST.setTextFill(Color.RED);
                            startEST.setVisible(true);
                            endEST.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                            endEST.setTextFill(Color.RED);
                            endEST.setVisible(true);
                        }
                } else {
                    startEST.setText("Appointment start time must be before end time.");
                    startEST.setTextFill(Color.RED);
                    startEST.setVisible(true);
                }
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add/Update Appointment");
        alert.setContentText("Cancel changes and return to Appointments menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveAppointment(ActionEvent event) throws IOException {
        if (typeValid() && titleValid() && descriptionValid() 
                && locationValid() && contactValid() && startDateTimeValid() 
                && endDateTimeValid() && customerNameValid() && customerIDValid() 
                && userValid() && customerTimeValid()) {
            errorDisplay.setVisible(false);
            System.out.println("Appointment info is valid.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add/Update Appointment");
            alert.setContentText("Save changes and return to Appointments menu?");
            Optional<ButtonType> result = alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                if (updatingAppointment == null) {
                    int id = 0;
                    String title = titleTxt.getText();
                    String description = descriptionTxt.getText();
                    String location = locationTxt.getText();
                    int contactID = DBAppointment.getContactID(contactComboBox.getSelectionModel().getSelectedItem());
                    String contactName = contactComboBox.getSelectionModel().getSelectedItem();
                    String type = typeComboBox.getSelectionModel().getSelectedItem();
                    selectedStartDate = startDatePicker.getValue();
                    selectedStartTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem());
                    String thisStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(currentZone).toLocalDateTime().format(DBAppointment.dtf);
                    selectedEndDate = endDatePicker.getValue();
                    selectedEndTime = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem());
                    String thisEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(currentZone).toLocalDateTime().format(DBAppointment.dtf);
                    String customerName = customerNameComboBox.getSelectionModel().getSelectedItem();
                    int customerID = customerIDComboBox.getValue();
                    String phone = DBCustomer.getCustomerPhone(customerName);
                    String postalCode = DBCustomer.getCustomerPostalCode(customerName);
                    String userName = userComboBox.getSelectionModel().getSelectedItem();
                    int userID = DBAppointment.getUserID(userName);
                    Appointment savingAppointment = new Appointment(
                            id, 
                            title, 
                            description, 
                            location,
                            contactID,
                            contactName, 
                            type, 
                            thisStartTime, 
                            thisEndTime,
                            customerID,
                            customerName,
                            phone,
                            postalCode,
                            userID,
                            userName);
                    DBAppointment.addAppointment(savingAppointment);
                } else {
                    int id = 0;
                    String title = titleTxt.getText();
                    String description = descriptionTxt.getText();
                    String location = locationTxt.getText();
                    int contactID = DBAppointment.getContactID(contactComboBox.getSelectionModel().getSelectedItem());
                    String contactName = contactComboBox.getSelectionModel().getSelectedItem();
                    String type = typeComboBox.getSelectionModel().getSelectedItem();
                    selectedStartDate = startDatePicker.getValue();
                    selectedStartTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem());
                    String thisStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(currentZone).toLocalDateTime().format(DBAppointment.dtf);
                    selectedEndDate = endDatePicker.getValue();
                    selectedEndTime = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem());
                    String thisEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(currentZone).toLocalDateTime().format(DBAppointment.dtf);
                    String customerName = customerNameComboBox.getSelectionModel().getSelectedItem();
                    int customerID = customerIDComboBox.getSelectionModel().getSelectedItem();
                    String phone = DBCustomer.getCustomerPhone(customerName);
                    String postalCode = DBCustomer.getCustomerPostalCode(customerName);
                    String userName = userComboBox.getSelectionModel().getSelectedItem();
                    int userID = DBAppointment.getUserID(userName);
                    updatingAppointment.setTitle(title);
                    updatingAppointment.setDescription(description);
                    updatingAppointment.setLocation(location);
                    updatingAppointment.setContactID(contactID);
                    updatingAppointment.setContactName(contactName);
                    updatingAppointment.setType(type);
                    updatingAppointment.setStart(thisStartTime);
                    updatingAppointment.setEnd(thisEndTime);
                    updatingAppointment.setApptCustomerName(customerName);
                    updatingAppointment.setApptCustomerID(customerID);
                    updatingAppointment.setPhone(phone);
                    updatingAppointment.setPostalCode(postalCode);
                    updatingAppointment.setUserName(userName);
                    updatingAppointment.setUserID(userID);
                    DBAppointment.updateAppointment(updatingAppointment);
                }
                stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else {
            validationErrors.clear();
            setupErrorDisplay();
            errorCollector();
            showErrors();
            validationErrors.clear();
        }
    }
    
    ObservableList<String> validationErrors = FXCollections.observableArrayList();
    public void setupErrorDisplay() {
        validationErrors.add("Appointment information is missing!\n");
    }
    public void errorCollector() {
        typeValid();
        titleValid();
        descriptionValid();
        locationValid();
        contactValid();
        startDateTimeValid();
        endDateTimeValid();
        customerNameValid();
        customerIDValid();
        userValid();
        customerTimeValid();
    }
    
    public Boolean typeValid() {
        if (typeComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Appointment type required!\n");
            return false;
        }
        return true;
    }
    
    public Boolean titleValid() {
        if (titleTxt.getText().isEmpty()) {
            validationErrors.add("Appointment title required!\n");
            return false;
        }
        return true;
    }
    
    public Boolean descriptionValid() {
        if (descriptionTxt.getText().isEmpty()) {
            validationErrors.add("Appointment description required!\n");
            return false;
        }
        return true;
    }
    
    public Boolean locationValid() {
        if (locationTxt.getText().isEmpty()) {
            validationErrors.add("Appointment location required!\n");
            return false;
        }
        return true;
    }
    
    public Boolean contactValid() {
        if (contactComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Contact required!\n");
            return false;
        } 
        return true;
    }
    
    public Boolean startDateTimeValid() {
        if (!hoursStartCheck()) {
            validationErrors.add("Appointment hours must be between 8:00 and 22:00 EST.\n");
            return false;
        } 
        return true;
    }
    
    public Boolean endDateTimeValid() {
        if (!hoursEndCheck()) {
            validationErrors.add("Appointment hours must be between 8:00 and 22:00 EST.\n");
            return false;
        } 
        return true;
    }
    
    public Boolean customerNameValid() {
        if (customerNameComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Customer name must be selected!\n");
            return false;
        }
        return true;
    }
    
    public Boolean customerIDValid() {
        if (customerIDComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Customer ID must be selected!\n");
            return false;
        }
        return true;
    }
    
    public Boolean userValid() {
        if (userComboBox.getValue().isEmpty()) {
            validationErrors.add("User required!\n");
            return false;
        }
        return true;
    }
    
    public Boolean customerTimeValid() {
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBAppointment.getAppointmentsForCustomer(customerNameComboBox.getSelectionModel().getSelectedItem()));
        filteredAppointments.setPredicate(appointment -> {
            selectedStartDate = startDatePicker.getValue();
            selectedStartTime = LocalTime.parse(startTimeComboBox.getSelectionModel().getSelectedItem());
            selectedEndDate = endDatePicker.getValue();
            selectedEndTime = LocalTime.parse(endTimeComboBox.getSelectionModel().getSelectedItem());
            LocalDateTime thisStartTime = LocalDateTime.of(selectedStartDate, selectedStartTime).atZone(currentZone).toLocalDateTime();
            LocalDateTime thisEndTime = LocalDateTime.of(selectedEndDate, selectedEndTime).atZone(currentZone).toLocalDateTime();
            LocalDateTime appointmentStartDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);
            LocalDate appointmentStartDate = LocalDate.parse(appointment.getStart(), DBAppointment.dtf);
            LocalDateTime appointmentEndDateTime = LocalDateTime.parse(appointment.getEnd(), DBAppointment.dtf);
            LocalDate appointmentEndDate = LocalDate.parse(appointment.getEnd(), DBAppointment.dtf);
            return (appointmentStartDate.equals(selectedStartDate) 
                    && (appointmentStartDateTime.isAfter(thisStartTime) 
                    && appointmentEndDateTime.isBefore(thisEndTime) 
                    || (appointmentStartDateTime.isBefore(thisStartTime) 
                    && appointmentEndDateTime.isAfter(thisEndTime)) 
                    || (appointmentStartDateTime.equals(thisStartTime)
                    || appointmentEndDateTime.equals(thisEndTime))));
        });
        
        if (!filteredAppointments.isEmpty()) {
            validationErrors.add("Customer has overlapping appointments!\n");
            return false;
        }
        return true;
    }
    
    public void showErrors(){
        String allErrors = "";
        for (String error : validationErrors) {
            allErrors = allErrors.concat(error);
        }
        errorDisplay.setVisible(true);
        errorDisplay.setText(allErrors);
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
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.getItems().clear();
        contactComboBox.getItems().clear();
        customerNameComboBox.getItems().clear();
        userComboBox.getItems().clear();
        customerIDComboBox.getItems().clear();
        startEST.setTextFill(Color.WHITE);
        endEST.setTextFill(Color.WHITE);
        endEST.setVisible(false);
        startEST.setVisible(false);
        
        LocalTime time = systemDateTime.toLocalTime().of(0, 0, 0);
        startOptions.add(time.format(DBAppointment.timeDTF));
        endOptions.add(time.format(DBAppointment.timeDTF));        
        time = systemDateTime.toLocalTime().of(0, 30, 0);
        do {
		startOptions.add(time.format(DBAppointment.timeDTF));
		endOptions.add(time.format(DBAppointment.timeDTF));
		time = time.plusMinutes(30);
	} while(!time.equals(LocalTime.of(0, 0, 0)));
		startOptions.remove(startOptions.size() - 1);
		endOptions.remove(0);
                startTimeComboBox.getItems().setAll(startOptions);
                endTimeComboBox.getItems().setAll(endOptions);
        if (updatingAppointment == null) {
            appointmentLbl.setText("Add Appointment");
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeComboBox.setValue(LocalTime.now().format(DBAppointment.timeDTF));
            endTimeComboBox.setValue(LocalTime.now().format(DBAppointment.timeDTF));
        }
        else{
            appointmentLbl.setText("Update Appointment");
            autoIDTxt.setText(Integer.toString(updatingAppointment.getAppointmentID()));
            typeComboBox.setValue(updatingAppointment.getType());
            titleTxt.setText(updatingAppointment.getTitle());
            descriptionTxt.setText(updatingAppointment.getDescription());
            locationTxt.setText(updatingAppointment.getLocation());
            contactComboBox.setValue(updatingAppointment.getContactName());
            startDatePicker.setValue(LocalDate.parse(updatingAppointment.getStart(), DBAppointment.dtf));
            startTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getStart(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            endDatePicker.setValue(LocalDate.parse(updatingAppointment.getEnd(), DBAppointment.dtf));
            endTimeComboBox.getSelectionModel().select(LocalDateTime.parse(updatingAppointment.getEnd(), DBAppointment.dtf).toLocalTime().format(DBAppointment.timeDTF));
            userComboBox.setValue(updatingAppointment.getUserName());
            customerNameComboBox.setValue(updatingAppointment.getApptCustomerName());
            customerIDComboBox.setValue(updatingAppointment.getApptCustomerID());
        }
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
        DBAppointment.getAllContactNames().clear();
        contactComboBox.setItems(DBAppointment.getAllContactNames());
        DBAppointment.getAllCustomerNames().clear();
        customerNameComboBox.setItems(DBAppointment.getAllCustomerNames());
        DBAppointment.getAllUserNames().clear();
        userComboBox.setItems(DBAppointment.getAllUserNames());
        DBAppointment.getAllCustomerIDs().clear();
        customerIDComboBox.setItems(DBAppointment.getAllCustomerIDs());
    }    
    
}
