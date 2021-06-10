/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulerapp;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;
import utilities.DBQuery;

/**
 *
 * @author chris
 */
public class SchedulerApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));
        Locale current = Locale.getDefault();
        ResourceBundle rb = ResourceBundle.getBundle("lang", current);
        DBConnection.connect();
        launch(args);
        DBConnection.disconnect();
    }

    /**
     * Starts the application
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        
    Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
    
    Scene scene = new Scene(root);
    
    stage.setScene(scene);
    stage.show();

    }
    
}
