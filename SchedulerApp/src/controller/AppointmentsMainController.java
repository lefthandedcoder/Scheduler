package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import utilities.DBAppointment;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import utilities.DBUser;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class AppointmentsMainController implements Initializable {

    /**
     * Sets the stage for showing the scene
     */
    Stage stage;

    /**
     * Sets up the scene for displaying the FXML
     */
    Parent scene;

    /**
     * Sets format for all visible dates and times
     */
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Stores appointment to be updated
     */
    private static Appointment updatedAppointment;

    /**
     * Grabs appointment to be updated
     * @return appointment to be updated
     */
    public static Appointment getUpdatedAppointment() {
        return updatedAppointment;
    }

    /**
     * Sets appointment to be updated
     * @param updatedAppointment
     */
    public void setUpdatedAppointment(Appointment updatedAppointment) {
        AppointmentsMainController.updatedAppointment = updatedAppointment;
    }

    /**
     * Radio button for appointments by week
     */
    @FXML
    private RadioButton weekRBtn;

    /**
     * Date picker for filtering and displaying appointments
     */
    @FXML
    private DatePicker datePicker;

    /**
     * Radio button for appointments by month
     */
    @FXML
    private RadioButton monthRBtn;

    /**
      Radio button for all appointments
     */
    @FXML
    private RadioButton allRBtn;

    /**
     * Radio button for upcoming appointments for current user
     */
    @FXML
    private RadioButton upcomingRBtn;

    /**
     * Label for appointment search
     */
    @FXML
    private Label appointmentSearchLabel;

    /**
     * Search box for searching appointments by ID or title
     */
    @FXML
    private TextField appointmentSearchBox;

    /**
     * Table of appointments
     */
    @FXML
    private TableView<Appointment> appointmentsTableView;

    /**
     * Column for appointment ID
     */
    @FXML
    private TableColumn<Appointment, Integer> IDCol;

    /**
     * Column for appointment title
     */
    @FXML
    private TableColumn<Appointment, String> titleCol;

    /**
     * Column for appointment description
     */
    @FXML
    private TableColumn<Appointment, String> descriptionCol;

    /**
     * Column for appointment type
     */
    @FXML
    private TableColumn<Appointment, String> typeCol;

    /**
     * Column for appointment location
     */
    @FXML
    private TableColumn<Appointment, String> locationCol;

    /**
     * Column for appointment contact name
     */
    @FXML
    private TableColumn<Appointment, String> contact;

    /**
     * Column for appointment start date and time
     */
    @FXML
    private TableColumn<Appointment, String> startCol;

    /**
     * Column for appointment end date and time
     */
    @FXML
    private TableColumn<Appointment, String> endCol;

    /**
     * Column for appointment customer ID
     */
    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    /**
     * Column for appointment customer name
     */
    @FXML
    private TableColumn<Appointment, String> nameCol;

    /**
     * Deletes selected appointment
     * @param event
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) {
        Appointment appointmentDelete = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (appointmentDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Appointment not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            //Delete appointment confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setContentText("Delete the selected appointment?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DBAppointment.deleteAppointment(appointmentDelete);
                    Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                    deleted.setTitle("Appointment Deleted");
                    alert.setContentText("Appointment has been deleted.");
                    tableSetupAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Grabs selected appointment and switches scene to allow user to update with text fields and comboboxes
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        updatedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (updatedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Appointment");
            alert.setContentText("Appointment not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            {
                setUpdatedAppointment(updatedAppointment);
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAddUpdate.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        }
    }

    /**
     * Switches scenes to allow user to add a new appointment with text fields and comboboxes
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionNewAppointment(ActionEvent event) throws IOException {
        updatedAppointment = null;
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAddUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches scenes to show reports
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAllReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Switches scenes to show customer main menu
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
     * Refreshes appointment main menu
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
     * Switches scenes to show scheduler main menu
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
     * Closes program if user clicks OK
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
     * Sets up table with all appointments, uses a lambda to filter list of appointments based on search
     */
    public void tableSetupAll() {
        appointmentsTableView.getSelectionModel().clearSelection();
        DBAppointment.getAllAppointments().clear();

        //Wrapping observable list (allAppointments) in a filtered list
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBAppointment.getAllAppointments(), p -> true);

        // Setting the filter predicate whenever the filter changes
        appointmentSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredAppointments.setPredicate(appointment -> {
                // If filter text is empty, display all parts
                if (newValue == null || newValue.isEmpty()) {
                    appointmentSearchLabel.setVisible(false);
                    return true;
                }

                //Compare all appointment titles
                String search = newValue.toLowerCase();

                if (appointment.getTitle().toLowerCase().contains(search) || Integer.valueOf(appointment.getAppointmentID()).toString().equals(search)) {
                    appointmentSearchLabel.setText("Appointments found!");
                    appointmentSearchLabel.setVisible(true);
                    return true; // Filter matches appointment title or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if (filteredAppointments.isEmpty()) {
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });

        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredAppointments);

        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());

        // Adding sorted (and filtered) appointments to table.
        appointmentsTableView.setItems(sortedAppointments);
    }

    /**
     * Sets up table of appointments for month based on datepicker selection. Uses two lambdas, one to filter for appointments for month and one to filter based on search box
     */
    public void tableSetupByMonth() {
        appointmentsTableView.getSelectionModel().clearSelection();
        LocalDate selectedDate = datePicker.getValue();
        LocalDate monthOut = selectedDate.plusMonths(1);
        DBAppointment.getAllAppointments().clear();
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBAppointment.getAllAppointments());
        filteredAppointments.setPredicate(appointment -> {

            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);
            LocalDate appointmentDate = appointmentDateTime.toLocalDate();
            return (appointmentDate.isEqual(selectedDate) || appointmentDate.isAfter(selectedDate)) && appointmentDate.isBefore(monthOut);
        });

        //Wrapping observable list (allAppointments) in a filtered list
        FilteredList<Appointment> filteredMonthAppointments = new FilteredList<>(filteredAppointments, p -> true);

        // Setting the filter predicate whenever the filter changes
        appointmentSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMonthAppointments.setPredicate(appointment -> {
                // If filter text is empty, display all parts
                if (newValue == null || newValue.isEmpty()) {
                    appointmentSearchLabel.setVisible(false);
                    return true;
                }

                //Compare all appointment titles
                String search = newValue.toLowerCase();

                if (appointment.getTitle().toLowerCase().contains(search) || Integer.valueOf(appointment.getAppointmentID()).toString().equals(search)) {
                    appointmentSearchLabel.setText("Appointments found!");
                    appointmentSearchLabel.setVisible(true);
                    return true; // Filter matches appointment title or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if (filteredMonthAppointments.isEmpty()) {
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });

        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredMonthAppointments);

        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());

        // Adding sorted (and filtered) appointments to table.
        appointmentsTableView.setItems(sortedAppointments);

    }

    /**
     * Sets up table of appointments for week based on datepicker selection. Uses two lambdas, one to filter for appointments for week and one to filter based on search box
     */
    public void tableSetupByWeek() {
        appointmentsTableView.getSelectionModel().clearSelection();

        LocalDate selectedDate = datePicker.getValue();
        LocalDate weekOut = selectedDate.plusDays(7);
        DBAppointment.getAllAppointments().clear();
        FilteredList<Appointment> filteredAppointments = new FilteredList<>(DBAppointment.getAllAppointments());
        filteredAppointments.setPredicate(appointment -> {

            LocalDateTime appointmentDateTime = LocalDateTime.parse(appointment.getStart(), DBAppointment.dtf);
            LocalDate appointmentDate = appointmentDateTime.toLocalDate();

            return appointmentDate.isAfter(selectedDate.minusDays(1)) && appointmentDate.isBefore(weekOut);
        });
        appointmentsTableView.setItems(filteredAppointments);

        //Wrapping observable list (allAppointments) in a filtered list
        FilteredList<Appointment> filteredWeekAppointments = new FilteredList<>(filteredAppointments, p -> true);

        // Setting the filter predicate whenever the filter changes
        appointmentSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredWeekAppointments.setPredicate(appointment -> {
                // If filter text is empty, display all parts
                if (newValue == null || newValue.isEmpty()) {
                    appointmentSearchLabel.setVisible(false);
                    return true;
                }

                //Compare all appointment titles
                String search = newValue.toLowerCase();

                if (appointment.getTitle().toLowerCase().contains(search) || Integer.valueOf(appointment.getAppointmentID()).toString().equals(search)) {
                    appointmentSearchLabel.setText("Appointments found!");
                    appointmentSearchLabel.setVisible(true);
                    return true; // Filter matches appointment title or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if (filteredWeekAppointments.isEmpty()) {
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });

        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredWeekAppointments);

        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());

        // Adding sorted (and filtered) appointments to table.
        appointmentsTableView.setItems(sortedAppointments);

    }

    /**
     * Sets up table of upcoming appointments for current user based on current time. Uses two lambdas, one to filter for appointments for period of time between now and 15 minutes from now and one to filter based on search box
     */
    public void tableSetupUpcoming() {
        appointmentsTableView.getSelectionModel().clearSelection();
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

        appointmentsTableView.setItems(filteredAppointments);

        //Wrapping observable list (allAppointments) in a filtered list
        FilteredList<Appointment> filteredWeekAppointments = new FilteredList<>(filteredAppointments, p -> true);

        // Setting the filter predicate whenever the filter changes
        appointmentSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredWeekAppointments.setPredicate(appointment -> {
                // If filter text is empty, display all parts
                if (newValue == null || newValue.isEmpty()) {
                    appointmentSearchLabel.setVisible(false);
                    return true;
                }

                //Compare all appointment titles
                String search = newValue.toLowerCase();

                if (appointment.getTitle().toLowerCase().contains(search) || Integer.valueOf(appointment.getAppointmentID()).toString().equals(search)) {
                    appointmentSearchLabel.setText("Appointments found!");
                    appointmentSearchLabel.setVisible(true);
                    return true; // Filter matches appointment title or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if (filteredWeekAppointments.isEmpty()) {
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });

        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredWeekAppointments);

        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());

        // Adding sorted (and filtered) appointments to table.
        appointmentsTableView.setItems(sortedAppointments);

    }

    /**
     * Initializes the controller class. Uses change listeners for radio buttons.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBAppointment.getAllAppointments().clear();
        appointmentsTableView.setItems(DBAppointment.getAllAppointments());
        appointmentsTableView.getSortOrder().add(IDCol);
        IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerName"));
        datePicker.setValue(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        upcomingRBtn.isSelected();
        tableSetupUpcoming();
        if (appointmentsTableView.getItems().isEmpty()) {
            tableSetupAll();
            allRBtn.isSelected();
        }
        allRBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    tableSetupAll();
                }
            }
        });

        monthRBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    tableSetupByMonth();
                }
            }
        });

        weekRBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    tableSetupByWeek();
                }
            }
        });

        allRBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    tableSetupAll();
                }
            }
        });

        upcomingRBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> obs, Boolean wasPreviouslySelected, Boolean isNowSelected) {
                if (isNowSelected) {
                    tableSetupUpcoming();
                }
            }
        });
    }
}
