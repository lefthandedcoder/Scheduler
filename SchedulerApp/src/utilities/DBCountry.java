package utilities;

import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian Dye
 */
public class DBCountry {

    /**
     * Stores list of all country names
     */
    private static ObservableList<String> comboStrings = FXCollections.observableArrayList();

    /**
     * Gets all country names for Customer add/update combobox
     * @return
     */
    public static ObservableList<String> getAllComboStrings() {
        try {
            // Pulling all country info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM countries";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
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
