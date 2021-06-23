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
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsByCustomerController implements Initializable {

    @FXML
    private ComboBox<String> customerNameComboBox;

    @FXML
    private TableView<Appointment> customerTableView;

    @FXML
    private TableColumn<Appointment, Integer> customerApptIDCol;

    @FXML
    private TableColumn<Appointment, String> customerTitleCol;

    @FXML
    private TableColumn<Appointment, String> customerDescriptionCol;

    @FXML
    private TableColumn<Appointment, String> customerLocationCol;

    @FXML
    private TableColumn<Appointment, String> customerStartCol;

    @FXML
    private TableColumn<Appointment, String> customerEndCol;

    @FXML
    private TableColumn<Appointment, Integer> customerContactIDCol;

    @FXML
    private TableColumn<Appointment, String> customerContactNameCol;

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
