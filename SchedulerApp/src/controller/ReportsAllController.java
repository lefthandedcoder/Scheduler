package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Report;
import utilities.DBReport;

/**
 * FXML Controller class
 *
 * @author Christian Dye
 */
public class ReportsAllController implements Initializable {

    /**
     * Table of appointments by month, type, and the number of each type
     */
    @FXML
     TableView<Report> summaryTableView;

    /**
     * Column of months with appointments
     */
    @FXML
    private TableColumn<Report, String> monthCol;

    /**
     * Column of appointment types
     */
    @FXML
    private TableColumn<Report, String> typeCol;

    /**
     * Column of total types per month
     */
    @FXML
    private TableColumn<Report, Integer> countCol;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        summaryTableView.setItems(DBReport.getallAppointmentsReport());

    }

}
