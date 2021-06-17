/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionUpdateAppointment(ActionEvent event) throws IOException {
        updatedAppointment = appointmentsTableView.getSelectionModel().getSelectedItem();
        setUpdatedAppointment(updatedAppointment);
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsAddUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionNewAppointment(ActionEvent event) throws IOException {
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
    
    public void tableSetup() {
        appointmentsTableView.getSelectionModel().clearSelection();

       //Wrapping observable list (allAppointments) in a filtered list
        appointmentsTableView.getItems().clear();
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        appointmentsTableView.setItems(DBAppointment.getAllAppointments());
        tableSetup();
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
        datePicker.setPromptText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    }
    
}
