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
import utilities.DBAppointment;
import utilities.DBCustomer;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class CustomersMainController implements Initializable {
    
    Stage stage;
    
    Parent scene;
    
    private static Customer updatedCustomer;
    
    public static Customer getUpdatedCustomer() {
        return updatedCustomer;
    }
    
    public void setUpdatedCustomer(Customer updatedCustomer) {
        CustomersMainController.updatedCustomer = updatedCustomer;
    }
    
    
    @FXML
    private Label customerSearchLabel;

    @FXML
    private TextField customerSearchBox;

    @FXML
    private TableView<Customer> customersTableView;

    @FXML
    private TableColumn<Customer, Integer> IDCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableColumn<Customer, String> regionCol;

    @FXML
    private TableColumn<Customer, String> countryCol;

    @FXML
    private TableColumn<Customer, String> postalCodeCol;

    @FXML
    void onActionAppointmentsMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AppointmentsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {
        Customer customerDelete = customersTableView.getSelectionModel().getSelectedItem();
        if (customerDelete == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Delete Customer");
            alert.setContentText("Customer not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            if (DBAppointment.getCustomer(customerDelete.getCustomerID()) != null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Delete Customer");
                alert.setContentText("Delete customer's appointments before deleting customer.");
                Optional<ButtonType> result = alert.showAndWait();
            } else {
             //Delete customer confirmation
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setContentText("Delete the selected customer?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    DBCustomer.deleteCustomer(customerDelete);
                    Alert deleted = new Alert(Alert.AlertType.INFORMATION);
                    deleted.setTitle("Customer Deleted");
                    alert.setContentText("Customer has been deleted.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }   
            }
        }
        tableSetup();
    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
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
    void onActionUpdateCustomer(ActionEvent event) throws IOException {
        updatedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (updatedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Customer");
            alert.setContentText("Customer not selected.");
            Optional<ButtonType> result = alert.showAndWait();
        } else {
            setUpdatedCustomer(updatedCustomer);
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddUpdate.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionNewCustomer(ActionEvent event) throws IOException {
        updatedCustomer = null;
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddUpdate.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
        
    }

    @FXML
    void onActionReportsAll(ActionEvent event)  throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ReportsMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }
    
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

        // Wrapping observable lists (allParts and allProducts) in a filtered list
        customersTableView.getItems().clear();
        FilteredList<Customer> filteredCustomers = new FilteredList<>(DBCustomer.getAllCustomers(), p -> true);
        
        // Setting the filter predicate whenever the filter changes
        customerSearchBox.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredCustomers.setPredicate(customer -> {
                // If filter text is empty, display all parts
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
            if(filteredCustomers.isEmpty()){
                customerSearchLabel.setText("Customer not found!");
                customerSearchLabel.setVisible(true);
            }
        });
        
        // Wrapping filtered list in a sorted list.
        SortedList<Customer> sortedCustomers = new SortedList<>(filteredCustomers);
        
        // Binding the sorted list comparator to the TableView comparator.
        sortedCustomers.comparatorProperty().bind(customersTableView.comparatorProperty());
        
        // Adding sorted (and filtered) parts to table.
        customersTableView.setItems(sortedCustomers);
    } 
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tableSetup();
    }   
}
