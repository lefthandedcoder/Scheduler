/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static controller.CustomersMainController.getUpdatedCustomer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Region;
import utilities.DBCountry;
import utilities.DBCustomer;
import utilities.DBRegion;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class CustomersAddUpdateController implements Initializable {
    
    private static Country selectedCountry;
    private static Region selectedRegion;
    
    public static Country getselectedCountry() {
        return selectedCountry;
    }
    
    public static Region getselectedRegion() {
        return selectedRegion;
    }
    
    private Customer newCustomer;
    // Customer being modified if this is a modification, else null 
    private final Customer updatingCustomer;

    public Customer getUpdatingCustomer() {
        return updatingCustomer;
    }

    /**
     * Constructor
     */
    public CustomersAddUpdateController() {
        this.updatingCustomer = getUpdatedCustomer();
    }
    
    Stage stage;
    
    Parent scene;
    
    @FXML
    private Label customerLbl;

    @FXML
    private TextField autoIDTxt;

    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField postalCodeTxt;

    @FXML
    private TextField phoneTxt;

    @FXML
    private ComboBox<String> regionComboBox;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    void onActionCustomersMain(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add/Update Customer");
        alert.setContentText("Cancel changes and return to Customers menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Add/Update Customer");
        alert.setContentText("Save changes and return to Customers menu?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (updatingCustomer == null) {
            int id = 0;
            String customerName = nameTxt.getText();
            String address = addressTxt.getText();
            String regionName = regionComboBox.getSelectionModel().getSelectedItem();
            String countryName = countryComboBox.getSelectionModel().getSelectedItem();
            String postalCode = postalCodeTxt.getText();
            String phone = phoneTxt.getText();
            Customer savingCustomer = new Customer(id, customerName, address, regionName, countryName, postalCode, phone);
            DBCustomer.addCustomer(savingCustomer);
        } else {
            String customerName = nameTxt.getText();
            String address = addressTxt.getText();
            String regionName = updatingCustomer.getRegionName();
            if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
                regionName = regionComboBox.getSelectionModel().getSelectedItem();
            }
            String countryName = updatingCustomer.getRegionName();
            if (countryComboBox.getSelectionModel().getSelectedItem() != null) {
                regionName = countryComboBox.getSelectionModel().getSelectedItem();
            }
            String postalCode = postalCodeTxt.getText();
            String phone = phoneTxt.getText();
            updatingCustomer.setCustomerName(customerName);
            updatingCustomer.setAddress(address);
            updatingCustomer.setRegionName(regionName);
            updatingCustomer.setCountryName(countryName);
            updatingCustomer.setPostalCode(postalCode);
            updatingCustomer.setPhone(phone);
            DBCustomer.updateCustomer(updatingCustomer);
        }
        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Clear updating customer data when adding new customer
        if (updatingCustomer == null) {
            customerLbl.setText("Add Customer");
        }
        else{
            customerLbl.setText("Update Customer");
            autoIDTxt.setText(Integer.toString(updatingCustomer.getCustomerID()));
            nameTxt.setText(updatingCustomer.getCustomerName());
            addressTxt.setText(updatingCustomer.getAddress());
            regionComboBox.setPromptText((updatingCustomer.getRegionName()));
            countryComboBox.setPromptText((updatingCustomer.getCountryName()));
            postalCodeTxt.setText(updatingCustomer.getPostalCode());
            phoneTxt.setText(updatingCustomer.getPhone());
        }
        
        // Populating combo box with country choices
        countryComboBox.setItems(DBCountry.getAllComboStrings());
        
        
        // Populating combo box with region choices        
        regionComboBox.setItems(DBRegion.getAllComboStrings());
        countryComboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue ov, Object t, Object t1) {

                regionComboBox.getItems().clear();
                switch (t1.toString()) {
                    case "U.S":
                        regionComboBox.setItems(DBRegion.getAllComboStringsUSA());
                        break;
                   case "Canada":
                        regionComboBox.setItems(DBRegion.getAllComboStringsCanada());
                        break;
                   case "UK":
                        regionComboBox.setItems(DBRegion.getAllComboStringsUK());
                       break;

                }
            }
        });
    }
}
