/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
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
import java.util.Locale;
import java.util.TimeZone;
import javafx.scene.control.Alert;
import static utilities.DBConnection.conn;
import utilities.DBQuery;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class LoginController implements Initializable {
    
    public static User currentUser;
    
    public static User getCurrentUser() {
        return currentUser;
    }
    
     public static Boolean login(String username, String password) {
        try {
            // Pulling user info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM users WHERE User_Name='" + username + "' AND Password='" + password + "'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                currentUser = new User();
                currentUser.setUsername(rs.getString("User_Name"));
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
    
    Stage stage;
    
    Parent scene;
    
    private String errorHeader;
    private String errorTitle;
    private String errorText;
    private String successHeader;
    private String successTitle;
    private String successText;
    
    @FXML
    private Label titleLbl;
    
    @FXML
    private Label passwordLbl;

    @FXML
    private Label usernameLbl;

    @FXML
    private Label currentTimeLbl;

    @FXML
    private Label timeLbl;

    @FXML
    private TextField usernameTxtFld;

    @FXML
    private TextField passwordTxtFld;

    @FXML
    private Button loginBtn;

    @FXML
    private Button exitBtn;

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionMainMenu(ActionEvent event) throws IOException {
        // Checking username & password authentication against database
        String username = usernameTxtFld.getText();
        String password = passwordTxtFld.getText();
        boolean validUser = login(username, password);
        if(validUser) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(successTitle);
            alert.setHeaderText(successHeader);
            alert.setContentText(successText);
            alert.showAndWait();
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(errorTitle);
            alert.setHeaderText(errorHeader);
            alert.setContentText(errorText);
            alert.showAndWait();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Adding language features for login screen
        rb = ResourceBundle.getBundle("login/lang", Locale.getDefault());
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
