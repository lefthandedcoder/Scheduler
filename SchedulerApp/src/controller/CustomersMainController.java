/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class CustomersMainController implements Initializable {

    /**
     * Sets stage for displaying scene
     */
    Stage stage;

    /**
     * Sets scene for displaying FXML
     */
    Parent scene;

    /**
     * Stores customer being modified
     */
    private static Customer updatedCustomer;

    /**
     * Grabs customer being modified
     * @return
     */
    public static Customer getUpdatedCustomer() {
        return updatedCustomer;
    }

    /**
     * Sets customer being updated
     * @param updatedCustomer
     */
    public void setUpdatedCustomer(Customer updatedCustomer) {
        CustomersMainController.updatedCustomer = updatedCustomer;
    }

    /**
     * Label for customer search
     */
    @FXML
    private Label customerSearchLabel;

    /**
     * Search box for searching for customers based on ID or name
     */
    @FXML
    private TextField customerSearchBox;

    /**
     * Table of customers
     */
    @FXML
    private TableView<Customer> customersTableView;

    /**
     * Column for customer ID
     */
    @FXML
    private TableColumn<Customer, Integer> IDCol;

    /**
     * Column for customer name
     */
    @FXML
    private TableColumn<Customer, String> nameCol;

    /**
     * Column for customer phone
     */
    @FXML
    private TableColumn<Customer, String> phoneCol;

    /**
     * Column for customer address
     */
    @FXML
    private TableColumn<Customer, String> addressCol;

    /**
     * Column for customer region
     */
    @FXML
    private TableColumn<Customer, String> regionCol;

    /**
     * Column for customer country
     */
    @FXML
    private TableColumn<Customer, String> countryCol;

    /**
     * Column for customer postal code
     */
    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    /**
     * Switches to appointment main menu
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
     * Deletes selected customer if customer selected and customer has no appointments
     * @param event
     */
    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Customer customerDelete = customersTableView.getSelectionModel().getSelectedItem();
        String customerName = customerDelete.getCustomerName();
        int customerID = customerDelete.getCustomerID();
        String contentText = "Customer " + customerName + " has been deleted.";
        String sureContentText = "Delete the selected customer (Customer: " + customerName + ")?";
        Boolean deleteCheck = false;
        if (customerDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Customer");
            alert.setContentText("Customer not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            if (DBCustomer.getCustomer(customerDelete.getCustomerID()) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Customer");
                alert.setContentText("Delete customer's appointments before deleting customer.");
                Optional<ButtonType> optional = alert.showAndWait();
            } else {
                //Delete customer confirmation
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete Customer");
                alert.setContentText(sureContentText);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    try {
                        DBCustomer.deleteCustomer(customerDelete);
                        deleteCheck = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        if (deleteCheck = true) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Customer Deleted");
            alert.setContentText(contentText);
            Optional<ButtonType> result = alert.showAndWait();
        }
        tableSetup();
    }

    /**
     * Switches to main menu
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
     * Refreshes customer main menu
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
     * Grabs selected customer if selected and switches window to allow user to update info with text fields and combo boxes
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        updatedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (updatedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Customer");
            alert.setContentText("Customer not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            setUpdatedCustomer(updatedCustomer);
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddUpdate.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Switches window to allow user to add customer info using text fields and combo boxes
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionNewCustomer(ActionEvent event) throws IOException {
        updatedCustomer = null;
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * Switches window to reports main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionReportsAll(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Closes the program
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
     * Sets up customer table with database info. Uses a lambda to filter based on search box string
     */
    public void tableSetup() {
        customersTableView.getSelectionModel().clearSelection();
        customersTableView.setItems(DBCustomer.getAllCustomers());
        customersTableView.getSortOrder().add(IDCol);
        IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("regionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        // Wrapping observable list in a filtered list
        customersTableView.getItems().clear();
        FilteredList<Customer> filteredCustomers = new FilteredList<>(DBCustomer.getAllCustomers(), p -> true);

        // Setting the filter predicate whenever the filter changes
        customerSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers.setPredicate(customer -> {
                // If filter text is empty, display all customers
                if (newValue == null || newValue.isEmpty()) {
                    customerSearchLabel.setVisible(false);
                    return true;
                }

                //Compare all customer names
                String search = newValue.toLowerCase();

                if (customer.getCustomerName().toLowerCase().contains(search) || Integer.valueOf(customer.getCustomerID()).toString().equals(search)) {
                    customerSearchLabel.setText("Customers found!");
                    customerSearchLabel.setVisible(true);
                    return true; // Filter matches part name or id.
                } else {
                    return false; // Does not match.
                }
            });
            // Displays "not found" message    
            if (filteredCustomers.isEmpty()) {
                customerSearchLabel.setText("Customer not found!");
                customerSearchLabel.setVisible(true);
            }
        });

        // Wrapping filtered list in a sorted list.
        SortedList<Customer> sortedCustomers = new SortedList<>(filteredCustomers);

        // Binding the sorted list comparator to the TableView comparator.
        sortedCustomers.comparatorProperty().bind(customersTableView.comparatorProperty());

        // Adding sorted (and filtered) customers to table.
        customersTableView.setItems(sortedCustomers);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableSetup();
    }
}
