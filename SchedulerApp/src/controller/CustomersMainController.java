/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class CustomersMainController implements Initializable {
    
    Stage stage;
    
    Parent scene;
    
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
    private TableColumn<Customer, String> zoneIDCol;

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

    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddModify.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionNewCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersAddModify.fxml"));
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        IDCol.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        zoneIDCol.setCellValueFactory(new PropertyValueFactory<>("divisionName"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("countryName"));
        postalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));

        // Wrapping observable lists (allParts and allProducts) in a filtered list
        FilteredList<Customer> filteredCustomers = new FilteredList<>(Customer.getAllCustomers(), p -> true);
        
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
}
