/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Report;
import utilities.DBAppointment;

/**
 * FXML Controller class
 *
 * @author chris
 */
public class ReportsAllController implements Initializable {
    
    @FXML
    private TableView<Report> summaryTableView;

    @FXML
    private TableColumn<Report, String> monthCol;

    @FXML
    private TableColumn<Report, String> typeCol;

    @FXML
    private TableColumn<Report, Integer> countCol;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        countCol.setCellValueFactory(new PropertyValueFactory<>("count"));
        summaryTableView.setItems(DBAppointment.getallAppointmentsReport());
        
    }    
    
}
