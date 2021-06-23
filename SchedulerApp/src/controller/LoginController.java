package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import utilities.DBAppointment;
import static utilities.DBConnection.conn;
import utilities.DBQuery;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class LoginController implements Initializable {

    /**
     * File name of login activity text document
     */
    public static String fileName = "login_activity.txt";

    /**
     * Stores current user
     */
    public static User currentUser = new User();

    /**
     * Grabs current user
     * @return
     */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Checks to see if username and password exist in the database
     * @param username
     * @param password
     * @return Boolean
     */
    public static Boolean login(String username, String password) {
        try {
            // Pulling user info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                currentUser = new User();
                currentUser.setUsername(rs.getString("User_Name"));
                currentUser.setUserID(rs.getInt("User_ID"));
                statement.close();
                System.out.println("User found.");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return false;
        }
    }

    /**
     * Sets stage for displaying scene
     */
    Stage stage;

    /**
     * Sets scene for displaying FXML
     */
    Parent scene;

    /**
     * String for error alert with language functionality
     */
    private String errorHeader;

    /**
     * String for error alert with language functionality
     */
    private String errorTitle;

    /**
     * String for error alert with language functionality
     */
    private String errorText;

    /**
     * String for successful pop-up with language functionality
     */
    private String successHeader;

    /**
     * String for successful pop-up with language functionality
     */
    private String successTitle;

    /**
     * String for successful pop-up with language functionality
     */
    private String successText;

    /**
     * Label for scheduler title with language functionality
     */
    @FXML
    private Label titleLbl;

    /**
     * Password label with language functionality
     */
    @FXML
    private Label passwordLbl;

    /**
     * Username label with language functionality
     */
    @FXML
    private Label usernameLbl;

    /**
     * Time label with language functionality
     */
    @FXML
    private Label timeLbl;

    /**
     * Label for current time based on LocalDateTime and system ZoneID
     */
    @FXML
    private Label currentTimeLbl;

    /**
     * Text field for username input
     */
    @FXML
    private TextField usernameTxtFld;

    /**
     * Text field for password input
     */
    @FXML
    private TextField passwordTxtFld;

    /**
     * Button for login
     */
    @FXML
    private Button loginBtn;

    /**
     * Button to close program
     */
    @FXML
    private Button exitBtn;

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
     * Attempts to log into the scheduler, logs login attempt in text document
     * @param event
     * @throws IOException
     */
    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        // Checking username and password against database
        String username = usernameTxtFld.getText();
        String password = passwordTxtFld.getText();
        boolean validUser = login(username, password);
        if (validUser) {
            try {
                PrintWriter outputStream = new PrintWriter(fileName);
                outputStream.println("User " + username + " successfully logged in at " + LocalDateTime.now().format(DBAppointment.dtf) + " " + ZoneId.systemDefault());
                System.out.println("User " + username + " successfully logged in at " + LocalDateTime.now().format(DBAppointment.dtf) + " " + ZoneId.systemDefault());
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(successTitle);
            alert.setHeaderText(successHeader);
            alert.setContentText(successText);
            alert.showAndWait();
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            try {
                PrintWriter outputStream = new PrintWriter(fileName);
                outputStream.println("User " + username + " failed to log in  " + password + " at " + LocalDateTime.now().format(DBAppointment.dtf) + " " + ZoneId.systemDefault());
                System.out.println("User " + username + " failed to log in with " + password + " at " + LocalDateTime.now().format(DBAppointment.dtf) + " " + ZoneId.systemDefault());
                outputStream.flush();
                outputStream.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Current time:
        String now = LocalDateTime.now().format(DBAppointment.dtf);
        ZoneId currentZone = ZoneId.systemDefault();
        currentTimeLbl.setText(now + " " + currentZone);
        //Adding language features for login screen
        rb = ResourceBundle.getBundle("login/lang", Locale.getDefault());
        timeLbl.setText(rb.getString("time"));
        titleLbl.setText(rb.getString("title"));
        usernameLbl.setText(rb.getString("username"));
        passwordLbl.setText(rb.getString("password"));
        usernameTxtFld.setPromptText(rb.getString("usernamePrompt"));
        passwordTxtFld.setPromptText(rb.getString("passwordPrompt"));
        loginBtn.setText(rb.getString("login"));
        exitBtn.setText(rb.getString("exit"));
        errorHeader = rb.getString("errorheader");
        errorTitle = rb.getString("errortitle");
        errorText = rb.getString("errortext");
        successHeader = rb.getString("successheader");
        successTitle = rb.getString("successtitle");
        successText = rb.getString("successtext");
    }

}
