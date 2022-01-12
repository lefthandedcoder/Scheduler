package utilities;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Region;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian Dye
 */
public class DBRegion {

    /**
     * Stores a list of all regions
     */
    private static ObservableList<String> comboStrings = FXCollections.observableArrayList();

    /**
     * Gets all regions
     * @return
     */
    public static ObservableList<String> getAllComboStrings() {
        try {
            // Pulling all region info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM first_level_divisions";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
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

    /**
     * Stores list of all regions in USA
     */
    private static ObservableList<String> comboStringsUSA = FXCollections.observableArrayList();

    /**
     * Gets all regions in USA
     * @return
     */
    public static ObservableList<String> getAllComboStringsUSA() {
        try {
            // Pulling all region info from database for USA
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'USA'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                comboStringsUSA.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsUSA;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Stores list of all regions in Canada
     */
    private static ObservableList<String> comboStringsCanada = FXCollections.observableArrayList();

    /**
     * Gets all regions in Canada
     * @return
     */
    public static ObservableList<String> getAllComboStringsCanada() {
        try {
            // Pulling all region info from database for Canada
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'Canada'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                comboStringsCanada.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsCanada;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    /**
     * Stores list of all regions in UK
     */
    private static ObservableList<String> comboStringsUK = FXCollections.observableArrayList();

    /**
     * Gets all regions in UK
     * @return
     */
    public static ObservableList<String> getAllComboStringsUK() {
        try {
            // Pulling all region info from database from UK
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT first_level_divisions.Division FROM first_level_divisions "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID "
                    + "WHERE countries.Country = 'UK'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                comboStringsUK.add(rs.getString("Division"));
            }
            statement.close();
            return comboStringsUK;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Get specific region from database
   
    /**
     * Stores region ID
     */
    private static int regionID;
    
    /**
     * Gets region ID from database based on specific region name
     * @return
     */
    public static Integer getRegionID(String regionName) {
        try {
            // Pulling specific user info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT Division_ID FROM first_level_divisions "
                    + "WHERE Division='" + regionName + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                regionID = rs.getInt("Division_ID");
                statement.close();
                return regionID;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

}
