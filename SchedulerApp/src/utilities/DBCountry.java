/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utilities.DBConnection.conn;

/**
 *
 * @author chris
 */
public class DBCountry {
    
    private static ObservableList<String> comboStrings = FXCollections.observableArrayList();
    public static ObservableList<String> getAllComboStrings() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM countries";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                comboStrings.add(rs.getString("Country"));
            }
            statement.close();
            return comboStrings;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
}
