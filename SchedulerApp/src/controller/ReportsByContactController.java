/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsByContactController implements Initializable {

    @FXML
    private ComboBox<String> contactNameComboBox;

    @FXML
    private TableView<Appointment> contactTableView;

    @FXML
    private TableColumn<Appointment, Integer> contactAppointmentIDCol;

    @FXML
    private TableColumn<Appointment, String> contactTitleCol;

    @FXML
    private TableColumn<Appointment, String> contactDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> contactLocationCol;

    @FXML
    private TableColumn<Appointment, String> contactStartCol;

    @FXML
    private TableColumn<Appointment, String> contactEndCol;

    @FXML
    private TableColumn<Appointment, String> contactCustomerIDCol;

    @FXML
    private TableColumn<Appointment, String> contactCustomerNameCol;

    @FXML
    void onActionChangeTableView(ActionEvent event) {
        contactTableView.getItems().clear();
        String selectedCustomer = contactNameComboBox.getSelectionModel().getSelectedItem();
        int customerID = DBCustomer.getCustomerID(selectedCustomer);
        DBCustomer.getAppointmentsForCustomer(customerID).clear();
        ObservableList<Appointment> customerAppointments = DBCustomer.getAppointmentsForCustomer(customerID);
        contactTableView.setItems(customerAppointments);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        DBCustomer.getAllCustomerNames().clear();
        contactNameComboBox.setItems(DBAppointment.getAllContactNames());

        contactAppointmentIDCol.setCellValueFactory(new PropertyValueFactory<>("appointmentID"));
        contactTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        contactDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactLocationCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        contactStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        contactEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        contactCustomerIDCol.setCellValueFactory(new PropertyValueFactory<>("contactID"));
        contactCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        contactNameComboBox.setValue(DBCustomer.getCustomerName(1));
        contactTableView.setItems(DBCustomer.getAppointmentsForCustomer(1));

    }

}
