package utilities;

import java.sql.*;

/**
 *
 * @author Christian Dye
 */
public class DBConnection {

    private static final String protocol = "jdbc:";
    private static final String vendorName = "mysql:";
    private static final String ipAddress = "//database-2.ckhgwtpupbdb.us-east-1.rds.amazonaws.com:3306/scheduler";
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    private static final String Driver = "com.mysql.cj.jdbc.Driver";
    private static final String username = "admin";
    private static final String password = "53689362807";
    public static Connection conn;

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
