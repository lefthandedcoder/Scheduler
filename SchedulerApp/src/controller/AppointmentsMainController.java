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
import javafx.scene.control.ToggleGroup;
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

/**
 * FXML Controller class
 *
 * @author chris
 */
public class AppointmentsMainController implements Initializable {
    
    Stage stage;
    
    Parent scene;
    
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    private static Appointment updatedAppointment;
    
    public static Appointment getUpdatedAppointment() {
        return updatedAppointment;
    }
    
    public void setUpdatedAppointment(Appointment updatedAppointment) {
        AppointmentsMainController.updatedAppointment = updatedAppointment;
    }
    
    @FXML
    private RadioButton weekRBtn;
    
    @FXML
    private DatePicker datePicker;

    @FXML
    private ToggleGroup appointmentWeekMonthTglGrp;

    @FXML
    private RadioButton monthRBtn;
    
    @FXML
    private RadioButton allRBtn;

    @FXML
    private Label appointmentSearchLabel;

    @FXML
    private TextField appointmentSearchBox;

    @FXML
    private TableView<Appointment> appointmentsTableView;

    @FXML
    private TableColumn<Appointment, Integer> IDCol;

    @FXML
    private TableColumn<Appointment, String> titleCol;

    @FXML
    private TableColumn<Appointment, String> descriptionCol;
    
    @FXML
    private TableColumn<Appointment, String> typeCol;

    @FXML
    private TableColumn<Appointment, String> locationCol;

    @FXML
    private TableColumn<Appointment, String> contact;

    @FXML
    private TableColumn<Appointment, String> startCol;

    @FXML
    private TableColumn<Appointment, String> endCol;

    @FXML
    private TableColumn<Appointment, Integer> customerIDCol;

    @FXML
    private TableColumn<Appointment, String> nameCol;

    @FXML
    void onActionDeleteAppointment(ActionEvent event)  {
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

    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        updatedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        if (updatedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Appointment");
            alert.setContentText("Appointment not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            {setUpdatedAppointment(updatedAppointment);
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAddUpdate.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
            }
        }
    }

    @FXML
    void onActionNewAppointment(ActionEvent event) throws IOException {
        updatedAppointment = null;
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAddUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionAllReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
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
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    
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
            if(filteredAppointments.isEmpty()){
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredAppointments);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        appointmentsTableView.setItems(sortedAppointments);
    }
    
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
            if(filteredMonthAppointments.isEmpty()){
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredMonthAppointments);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        appointmentsTableView.setItems(sortedAppointments);

    }
    
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
            if(filteredWeekAppointments.isEmpty()){
                appointmentSearchLabel.setText("Appointment not found!");
                appointmentSearchLabel.setVisible(true);
            }
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Appointment> sortedAppointments = new SortedList<>(filteredWeekAppointments);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedAppointments.comparatorProperty().bind(appointmentsTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        appointmentsTableView.setItems(sortedAppointments);

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBAppointment.getAllAppointments().clear();
        if (!MainMenuController.getUpcomingAppointments().isEmpty()) {
            appointmentsTableView.setItems(MainMenuController.getUpcomingAppointments());
        } else {
            appointmentsTableView.setItems(DBAppointment.getAllAppointments());
        }
        appointmentsTableView.getSortOrder().add(IDCol);        
        IDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerIDCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("apptCustomerName"));
        datePicker.setValue(LocalDate.parse(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))));
        allRBtn.isSelected();
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
    }
    
}
