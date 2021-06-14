/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Region;
import static utilities.DBConnection.conn;
/**
 *
 * @author chris
 */
public class DBRegion {
    
    private static ObservableList<String> comboStrings = FXCollections.observableArrayList();
    public static ObservableList<String> getAllComboStrings() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM first_level_divisions";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                comboStrings.add(rs.getString("Division"));
            }
            statement.close();
            return comboStrings;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    //Bulky code to ensure that region combo box filters by regions by selection in country combo box
    private static ObservableList<String> comboStringsUSA = FXCollections.observableArrayList();
    public static ObservableList<String> getAllComboStringsUSA() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'U.S'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                comboStringsUSA.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsUSA;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    private static ObservableList<String> comboStringsCanada = FXCollections.observableArrayList();
    public static ObservableList<String> getAllComboStringsCanada() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'Canada'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                comboStringsCanada.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsCanada;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    private static ObservableList<String> comboStringsUK = FXCollections.observableArrayList();
    public static ObservableList<String> getAllComboStringsUK() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'UK'";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                comboStringsUK.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsUK;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    // Get region from system
    public static Region getRegion(String regionName) {
        try {
            // Pulling region info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM first_level_divisions WHERE DIVISION='" + regionName + "'";
            statement.executeQuery(query);
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
                Region region = new Region();
                region.setRegionID(rs.getInt("DIVISION_ID"));
                statement.close();
                System.out.println("Region found.");
                return region;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        
        return null;
    }
    
    
}
