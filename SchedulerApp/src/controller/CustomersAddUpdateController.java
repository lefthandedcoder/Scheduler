package controller;

import static controller.CustomersMainController.getUpdatedCustomer;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TextArea;
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
 * @author Christian Dye
 */
public class CustomersAddUpdateController implements Initializable {

    /**
     * Stores selected country
     */
    private static Country selectedCountry;

    /**
     * Stores selected region
     */
    private static Region selectedRegion;

    /**
     * Grabs selected country and stores it
     * @return
     */
    public static Country getselectedCountry() {
        return selectedCountry;
    }

    /**
     * Grabs selected region and stores it
     * @return
     */
    public static Region getselectedRegion() {
        return selectedRegion;
    }

    // Customer being modified if this is a modification, else null 

    /**
     * Stores customer being modified
     */
    private final Customer updatingCustomer;

    /**
     * Grabs customer being modified
     * @return
     */
    public Customer getUpdatingCustomer() {
        return updatingCustomer;
    }

    /**
     * Constructor for selected customer from main menu
     */
    public CustomersAddUpdateController() {
        this.updatingCustomer = getUpdatedCustomer();
    }

    /**
     * Sets stage for displaying scene
     */
    Stage stage;

    /**
     * Scene for displaying FXML
     */
    Parent scene;

    /**
     * Label for customer window (add or update)
     */
    @FXML
    private Label customerLbl;

    /**
     * Text field for auto-incremented customer ID
     */
    @FXML
    private TextField autoIDTxt;

    /**
     * Text field for customer name
     */
    @FXML
    private TextField nameTxt;

    /**
     * Text field for customer address
     */
    @FXML
    private TextField addressTxt;

    /**
     * Text field for customer postal code
     */
    @FXML
    private TextField postalCodeTxt;

    /**
     * Text field for customer phone
     */
    @FXML
    private TextField phoneTxt;

    /**
     * Combobox for customer region
     */
    @FXML
    private ComboBox<String> regionComboBox;

    /**
     * Combobox for customer country
     */
    @FXML
    private ComboBox<String> countryComboBox;

    /**
     * Text area for displaying errors
     */
    @FXML
    private TextArea errorDisplay;

    /**
     * Cancels changes and returns to customer main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionCustomersMain(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Add/Update Customer");
        alert.setContentText("Cancel changes and return to Customers menu?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            regionComboBox.getItems().clear();
            countryComboBox.getItems().clear();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }

    /**
     * Saves or updates customer information if information is valid, otherwise displays errors in text area
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionSaveCustomer(ActionEvent event) throws IOException {
        if ((nameValid() && addressValid() && regionValid() && countryValid() && postalCodeValid() && phoneValid())) {
            errorDisplay.setVisible(false);
            System.out.println("Customer info is valid.");
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Add/Update Customer");
            alert.setContentText("Save changes and return to Customers menu?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && alert.getResult() == ButtonType.OK) {
                if (updatingCustomer == null) {
                    int id = 0;
                    String customerName = nameTxt.getText();
                    String address = addressTxt.getText();
                    String regionName = regionComboBox.getSelectionModel().getSelectedItem();
                    int regionID = DBRegion.getRegionID(regionName);
                    String countryName = countryComboBox.getSelectionModel().getSelectedItem();
                    String postalCode = postalCodeTxt.getText();
                    String phone = phoneTxt.getText();
                    Customer savingCustomer = new Customer(id, customerName, address, regionName, regionID, countryName, postalCode, phone);
                    DBCustomer.addCustomer(savingCustomer);
                    regionComboBox.getItems().clear();
                    countryComboBox.getItems().clear();
                } else {
                    String customerName = nameTxt.getText();
                    String address = addressTxt.getText();
                    String regionName = updatingCustomer.getRegionName();
                    int regionID = updatingCustomer.getRegionID();
                    if (regionComboBox.getSelectionModel().getSelectedItem() != null) {
                        regionName = regionComboBox.getSelectionModel().getSelectedItem();
                        regionID = DBRegion.getRegionID(regionName);
                    }
                    String countryName = updatingCustomer.getCountryName();
                    if (countryComboBox.getSelectionModel().getSelectedItem() != null) {
                        countryName = countryComboBox.getSelectionModel().getSelectedItem();
                    }
                    String postalCode = postalCodeTxt.getText();
                    String phone = phoneTxt.getText();
                    updatingCustomer.setCustomerName(customerName);
                    updatingCustomer.setAddress(address);
                    updatingCustomer.setRegionName(regionName);
                    updatingCustomer.setRegionID(regionID);
                    updatingCustomer.setCountryName(countryName);
                    updatingCustomer.setPostalCode(postalCode);
                    updatingCustomer.setPhone(phone);
                    DBCustomer.updateCustomer(updatingCustomer);
                    regionComboBox.getItems().clear();
                    countryComboBox.getItems().clear();
                }
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/CustomersMain.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } else {
            validationErrors.clear();
            setupErrorDisplay();
            errorCollector();
            showErrors();
            validationErrors.clear();
        }
    }

    /**
     * Stores list of validation errors
     */
    ObservableList<String> validationErrors = FXCollections.observableArrayList();

    /**
     * Prepares display of errors with an initial string
     */
    public void setupErrorDisplay() {
        validationErrors.add("Customer information is missing!\n");
    }

    /**
     * Collects validation errors
     */
    public void errorCollector() {
        nameValid();
        addressValid();
        regionValid();
        countryValid();
        postalCodeValid();
        countryValid();
        phoneValid();
    }

    /**
     * Verifies if name is present
     * @return Boolean
     */
    public Boolean nameValid() {
        if (nameTxt.getText().isEmpty()) {
            validationErrors.add("Customer name required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies if address is present
     * @return Boolean
     */
    public Boolean addressValid() {
        if (addressTxt.getText().isEmpty()) {
            validationErrors.add("Address required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies if region selected
     * @return Boolean
     */
    public Boolean regionValid() {
        if (regionComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Region must be selected!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies if country selected
     * @return Boolean
     */
    public Boolean countryValid() {
        if (countryComboBox.getSelectionModel().isEmpty()) {
            validationErrors.add("Country must be selected!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies if postal code is present
     * @return Boolean
     */
    public Boolean postalCodeValid() {
        if (postalCodeTxt.getText().isEmpty()) {
            validationErrors.add("Postal code required!\n");
            return false;
        }
        return true;
    }

    /**
     * Verifies if phone is present
     * @return Boolean
     */
    public Boolean phoneValid() {
        if (phoneTxt.getText().isEmpty()) {
            validationErrors.add("Phone required!\n");
            return false;
        }
        return true;
    }

    /**
     * Shows list of errors
     */
    public void showErrors() {
        String allErrors = "";
        for (String error : validationErrors) {
            allErrors = allErrors.concat(error);
        }
        errorDisplay.setVisible(true);
        errorDisplay.setText(allErrors);
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        errorDisplay.setVisible(false);
        //Clear updating customer data when adding new customer
        if (updatingCustomer == null) {
            customerLbl.setText("Add Customer");
        } else {
            customerLbl.setText("Update Customer");
            autoIDTxt.setText(Integer.toString(updatingCustomer.getCustomerID()));
            nameTxt.setText(updatingCustomer.getCustomerName());
            addressTxt.setText(updatingCustomer.getAddress());
            regionComboBox.setValue((updatingCustomer.getRegionName()));
            countryComboBox.setValue((updatingCustomer.getCountryName()));
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
                    case "USA":
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
