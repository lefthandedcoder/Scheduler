package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsByCustomerController implements Initializable {

    /**
     * Combobox for selecting customer 
     */
    @FXML
    private ComboBox<String> customerNameComboBox;

    /**
     * Table of appointments for selected customer
     */
    @FXML
    private TableView<Appointment> customerTableView;

    /**
     * Column of appointment IDs for selected customer
     */
    @FXML
    private TableColumn<Appointment, Integer> customerApptIDCol;

    /**
     * Column of appointment titles for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerTitleCol;

    /**
     * Column of appointment descriptions for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerDescriptionCol;

    /**
     * Column of appointment locations for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerLocationCol;

    /**
     * Column of appointment start times for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerStartCol;

    /**
     * Column of appointment end times for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerEndCol;

    /**
     * Column of appointment contact IDs for selected customer
     */
    @FXML
    private TableColumn<Appointment, Integer> customerContactIDCol;

    /**
     * Column of appointment contact names for selected customer
     */
    @FXML
    private TableColumn<Appointment, String> customerContactNameCol;

    /**
     * Changes table based on selected customer
     * @param event
     */
    @FXML
    void onActionChangeTableView(ActionEvent event) {
        customerTableView.getItems().clear();
        String selectedCustomer = customerNameComboBox.getSelectionModel().getSelectedItem();
        int customerID = DBCustomer.getCustomerID(selectedCustomer);
        DBCustomer.getAppointmentsForCustomer(customerID).clear();
        ObservableList<Appointment> customerAppointments = DBCustomer.getAppointmentsForCustomer(customerID);
        customerTableView.setItems(customerAppointments);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DBCustomer.getAllCustomerNames().clear();
        customerNameComboBox.setItems(DBCustomer.getAllCustomerNames());
        customerApptIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        customerTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        customerDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        customerLocationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        customerStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        customerEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        customerContactIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        customerContactNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        customerNameComboBox.setValue(DBCustomer.getCustomerName(1));
        customerTableView.setItems(DBCustomer.getAppointmentsForCustomer(1));

    }

}
