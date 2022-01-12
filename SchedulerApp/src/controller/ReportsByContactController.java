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
import utilities.DBAppointment;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsByContactController implements Initializable {

    /**
     * Combobox of contact names
     */
    @FXML
    private ComboBox<String> contactNameComboBox;

    /**
     * Table of appointments for selected contact
     */
    @FXML
    private TableView<Appointment> contactTableView;

    /**
     * Column of appointment IDs for selected contact
     */
    @FXML
    private TableColumn<Appointment, Integer> contactAppointmentIDCol;

    /**
     * Column of appointment titles for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactTitleCol;

    /**
     * Column of appointment descriptions for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactDescriptionCol;

    /**
     * Column of appointment locations for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactLocationCol;

    /**
     * Column of appointment start dates and times for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactStartCol;

    /**
     * Column of appointment end dates and times for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactEndCol;

    /**
     * Column of appointment customer IDs for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactCustomerIDCol;

    /**
     * Column of appointment customer names for selected contact
     */
    @FXML
    private TableColumn<Appointment, String> contactCustomerNameCol;

    /**
     * Changes table based on selected contact
     * @param event
     */
    @FXML
    void onActionChangeTableView(ActionEvent event) {
        contactTableView.getItems().clear();
        String selectedContact = contactNameComboBox.getSelectionModel().getSelectedItem();
        int contactID = DBAppointment.getContactID(selectedContact);
        DBAppointment.getAppointmentsForContact(contactID).clear();
        ObservableList<Appointment> contactAppointments = DBAppointment.getAppointmentsForContact(contactID);
        contactTableView.setItems(contactAppointments);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DBAppointment.getAllContactNames().clear();
        contactNameComboBox.setItems(DBAppointment.getAllContactNames());

        contactAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactLocationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        contactEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        contactCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        contactCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactNameComboBox.setValue(DBAppointment.getContactName(1));
        contactTableView.setItems(DBAppointment.getAppointmentsForContact(1));

    }

}
