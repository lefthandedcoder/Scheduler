package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsMainController implements Initializable {

    /**
     * Sets stage for displaying scene
     */
    Stage stage;

    /**
     * Sets scene for displaying FXML
     */
    Parent scene;

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
     * Switches to customer main menu
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
     * Switches to reports main menu
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionAllReports(ActionEvent event) throws IOException {
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
     * Initializes the controller class. This mainly is a scene for the tabs.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

}
