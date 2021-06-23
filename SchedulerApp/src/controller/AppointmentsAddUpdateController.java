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
import utilities.DBUser;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class AppointmentsAddUpdateController implements Initializable {

    /**
     * Sets the stage for showing the scene
     */
    Stage stage;

    /**
     * Sets up the scene for displaying the FXML
     */
    Parent scene;

    /**
     * Grabs the selected appointment to be updated
     */
    private final Appointment updatingAppointment;

    /**
     * Used for populating the combobox for start times
     */
    private final ObservableList<String> startOptions = FXCollections.observableArrayList();

    /**
     * Used for populating the combobox for end times
     */
    private final ObservableList<String> endOptions = FXCollections.observableArrayList();

    /**
     * Used for setting the EST timezone
     */
    ZoneId officeZone = ZoneId.of("America/New_York");

    /**
     * Used for setting the current system timezone
     */
    ZoneId currentZone = ZoneId.systemDefault();

    /**
     *  Used for grabbing selected end date
     */
    LocalDate selectedEndDate;

    /**
     * Used for grabbing selected end time
     */
    LocalTime selectedEndTime;

    /**
     * Used for grabbing selected start date
     */
    LocalDate selectedStartDate;

    /**
     * Used for grabbing selected start time
     */
    LocalTime selectedStartTime;

    /**
     * Used for converting selected start time to system time
     */
    LocalDateTime localStartTime;

    /**
     * Used for converting selected end time to system time
     */
    LocalDateTime localEndTime;

    /**
     * Used for storing system date and time
     */
    LocalDateTime systemDateTime = LocalDateTime.now().atZone(currentZone).toLocalDateTime();

    /**
     * Used for storing EST date and time
     */
    LocalDateTime officeDateTime = LocalDateTime.now().atZone(currentZone).toLocalDateTime();

    /**
     * Grabs selected appointment for updating
     * @return updatingAppointment
     */
    public Appointment getUpdatingAppointment() {
        return updatingAppointment;
    }

    /**
     * Constructor for specific appointment from other controller
     */
    public AppointmentsAddUpdateController() {
        this.updatingAppointment = getUpdatedAppointment();
    }

    /**
     * Date picker for appointment start date
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * Label for appointment window
     */
    @FXML
    private Label appointmentLbl;

    /**
     * Text field for auto-incremented appointment ID
     */
    @FXML
    private TextField autoIDTxt;

    /**
     * Text field for appointment title
     */
    @FXML
    private TextField titleTxt;

    /**
     * Text field for appointment description
     */
    @FXML
    private TextField descriptionTxt;

    /**
     * Combobox for appointment contact name
     */
    @FXML
    private ComboBox<String> contactComboBox;

    /**
     * Combobox for appointment start time
     */
    @FXML
    private ComboBox<String> startTimeComboBox;

    /**
     * Date picker for appointment end date
     */
    @FXML
    private DatePicker endDatePicker;

    /**
     * Combobox for appointment end time
     */
    @FXML
    private ComboBox<String> endTimeComboBox;

    /**
     * Combobox for appointment type
     */
    @FXML
    private ComboBox<String> typeComboBox;

    /**
     * Text field for appointment location
     */
    @FXML
    private TextField locationTxt;

    /**
     * Combobox for appointment user
     */
    @FXML
    private ComboBox<String> userComboBox;

    /**
     * Combobox for appointment customer name
     */
    @FXML
    private ComboBox<String> customerNameComboBox;

    /**
     * Combobox for appointment customer ID
     */
    @FXML
    private ComboBox<Integer> customerIDComboBox;

    /**
     * Label for appointment start time information (checks if time works)
     */
    @FXML
    private Label startLbl;

    /**
     * Label for appointment end time information (checks if time works)
     */
    @FXML
    private Label endLbl;

    /**
     * Displays errors in a text area
     */
    @FXML
    private TextArea errorDisplay;

    /**
     * Displays start and time info and if there are logical errors
     * @param event
     */
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
                        startLbl.setText(localStartTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        startLbl.setTextFill(Color.WHITE);
                        startLbl.setVisible(true);
                        endLbl.setText(localEndTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        endLbl.setTextFill(Color.WHITE);
                        endLbl.setVisible(true);
                    } else {
                        startLbl.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                        startLbl.setTextFill(Color.RED);
                        startLbl.setVisible(true);
                        endLbl.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                        endLbl.setTextFill(Color.RED);
                        endLbl.setVisible(true);
                    }
                } else {
                    endLbl.setText("Appointment end time must be after start time.");
                    endLbl.setTextFill(Color.RED);
                    endLbl.setVisible(true);
                }
            }
        }
    }

    /**
     * Displays start and time info and if there are logical errors
     * @param event
     */
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
                        startLbl.setText(localStartTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        startLbl.setTextFill(Color.WHITE);
                        startLbl.setVisible(true);
                        endLbl.setText(localEndTime.format(DBAppointment.dtf) + " " + currentZone.toString());
                        endLbl.setTextFill(Color.WHITE);
                        endLbl.setVisible(true);
                    } else {
                        startLbl.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                        startLbl.setTextFill(Color.RED);
                        startLbl.setVisible(true);
                        endLbl.setText("Appointment hours must be between 8:00 and 22:00 EST.");
                        endLbl.setTextFill(Color.RED);
                        endLbl.setVisible(true);
                    }
                } else {
                    startLbl.setText("Appointment start time must be before end time.");
                    startLbl.setTextFill(Color.RED);
                    startLbl.setVisible(true);
                }
            }
        }
    }

    /**
     * Sets customer ID based on customer name selection
     * @param event
     */
    @FXML
    void onActionSetID(ActionEvent event) {
        String name = customerNameComboBox.getSelectionModel().getSelectedItem();
        customerIDComboBox.setValue(DBCustomer.getCustomerID(name));
    }

    /**
     * Sets customer name based on customer ID selection
     * @param event
     */
    @FXML
    void onActionSetName(ActionEvent event) {
        int id = customerIDComboBox.getSelectionModel().getSelectedItem();
        customerNameComboBox.setValue(DBCustomer.getCustomerName(id));
    }

    /**
     * Cancels changes and returns to appointments main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add/Update Appointment");
        alert.setContentText("Cancel changes and return to Appointments menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves or updates appointment changes if everything is valid, otherwise, displays logical errors in text area
     * @param event
     * @throws IOException
     */
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
                    int userID = DBUser.getUserID(userName);
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
                    int userID = DBUser.getUserID(userName);
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
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
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

    /**
     * Stores list of validation errors
     */
    ObservableList<String> validationErrors = FXCollections.observableArrayList();

    /**
     * Prepares display of errors with an initial string
     */
    public void setupErrorDisplay() {
        validationErrors.add("Appointment information is missing!\n");
    }

    /**
     * Collects and stores list of validation errors
     */
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

    /**
     * Verifies that type is present
     * @return Boolean
     */
    public Boolean typeValid() {
        if (typeComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Appointment type required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies that title is present
     * @return Boolean
     */
    public Boolean titleValid() {
        if (titleTxt.getText().isEmpty()) {
            validationErrors.add("Appointment title required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies that description is present
     * @return Boolean
     */
    public Boolean descriptionValid() {
        if (descriptionTxt.getText().isEmpty()) {
            validationErrors.add("Appointment description required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies that location is present
     * @return Boolean
     */
    public Boolean locationValid() {
        if (locationTxt.getText().isEmpty()) {
            validationErrors.add("Appointment location required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies contact name is selected
     * @return Boolean
     */
    public Boolean contactValid() {
        if (contactComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Contact required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies that start time is during office hours
     * @return Boolean
     */
    public Boolean startDateTimeValid() {
        if (!hoursStartCheck()) {
            validationErrors.add("Appointment hours must be between 8:00 and 22:00 EST.\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies that end time is during office hours
     * @return Boolean
     */
    public Boolean endDateTimeValid() {
        if (!hoursEndCheck()) {
            validationErrors.add("Appointment hours must be between 8:00 and 22:00 EST.\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies customer name is selected
     * @return Boolean
     */
    public Boolean customerNameValid() {
        if (customerNameComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Customer name must be selected!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies customer ID selected
     * @return Boolean
     */
    public Boolean customerIDValid() {
        if (customerIDComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Customer ID must be selected!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies user selected
     * @return Boolean
     */
    public Boolean userValid() {
        if (userComboBox.getValue().isEmpty()) {
            validationErrors.add("User required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies customer time has no overlaps
     * @return Boolean
     */
    public Boolean customerTimeValid() {
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBCustomer.getAppointmentsForCustomer(customerIDComboBox.getSelectionModel().getSelectedItem()));
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

    /**
     * Shows list of errors in error display
     */
    public void showErrors() {
        String allErrors = "";
        for (String error : validationErrors) {
            allErrors = allErrors.concat(error);
        }
        errorDisplay.setVisible(true);
        errorDisplay.setText(allErrors);
    }

    /**
     * Verifies that start time is during office hours
     * @return
     */
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

    /**
     * Verifies that end time is during office hours
     * @return
     */
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
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeComboBox.getItems().clear();
        contactComboBox.getItems().clear();
        customerNameComboBox.getItems().clear();
        userComboBox.getItems().clear();
        customerIDComboBox.getItems().clear();
        startLbl.setTextFill(Color.WHITE);
        endLbl.setTextFill(Color.WHITE);
        endLbl.setVisible(false);
        startLbl.setVisible(false);

        LocalTime time = systemDateTime.toLocalTime().of(0, 0, 0);
        startOptions.add(time.format(DBAppointment.timeDTF));
        endOptions.add(time.format(DBAppointment.timeDTF));
        time = systemDateTime.toLocalTime().of(0, 30, 0);
        do {
            startOptions.add(time.format(DBAppointment.timeDTF));
            endOptions.add(time.format(DBAppointment.timeDTF));
            time = time.plusMinutes(30);
        } while (!time.equals(LocalTime.of(0, 0, 0)));
        startOptions.remove(startOptions.size() - 1);
        endOptions.remove(0);
        startTimeComboBox.getItems().setAll(startOptions);
        endTimeComboBox.getItems().setAll(endOptions);
        if (updatingAppointment == null) {
            appointmentLbl.setText("Add Appointment");
            startDatePicker.setValue(LocalDate.now());
            endDatePicker.setValue(LocalDate.now());
            startTimeComboBox.setValue(LocalTime.now().format(DBAppointment.timeDTF));
            endTimeComboBox.setValue(LocalTime.now().plusMinutes(30).format(DBAppointment.timeDTF));
        } else {
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
        DBCustomer.getAllCustomerNames().clear();
        customerNameComboBox.setItems(DBCustomer.getAllCustomerNames());
        DBUser.getAllUserNames().clear();
        userComboBox.setItems(DBUser.getAllUserNames());
        DBCustomer.getAllCustomerIDs().clear();
        customerIDComboBox.setItems(DBCustomer.getAllCustomerIDs());
    }

}
