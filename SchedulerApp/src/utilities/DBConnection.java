package utilities;

import java.sql.*;

/**
 *
 * @author Christian Dye
 */
public class DBConnection {

    /**
     * The protocol
     */
    private static final String protocol = "jdbc:";

    /**
     * The vendor name
     */
    private static final String vendorName = "mysql:";

    /**
     * The IP address
     */
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ08Qnw";
    //JDBC URL

    /**
     * The JDBC URL
     */
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    // Driver interface reference

    /**
     * The driver
     */
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    // Database user credentials

    /**
     * The database username
     */
    private static final String username = "U08Qnw";

    /**
     * The database password
     */
    private static final String password = "53689362807";
    // Driver connection reference

    /**
     * The connection
     */
    public static Connection conn;

    //Connecting to database

    /**
     * Connects to database
     */
    public static void connect() {
        try {
            // Connect to database
            Class.forName(Driver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connected to MySQL Database");
        } catch (ClassNotFoundException e) {
            System.out.println("Class Not Found " + e.getMessage());

        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error: " + e.getErrorCode());
        }
    }

    // Return Database Connection

    /**
     * Gets database connection
     * @return
     */
    public static Connection getConnection() {
        return conn;
    }

    // Close Database Connection

    /**
     * Closes database connection
     */
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from MySQL Database");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
