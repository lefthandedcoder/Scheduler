package schedulerapp;

import java.util.Locale;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DBConnection;

/**
 *
 * @author Christian Dye
 */
public class SchedulerApp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Locale.setDefault(new Locale("fr"));
        //TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        DBConnection.connect();
        System.out.println("Launching...");
        launch(args);
        DBConnection.disconnect();
    }

    /**
     * Starts the application
     *
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
