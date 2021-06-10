/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chris
 */
public class DBConnection {
    private static final String protocol = "jdbc:";
    private static final String vendorName = "mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/WJ08Qnw";
    //JDBC URL
    private static final String jdbcURL = protocol + vendorName + ipAddress;
    // Driver interface reference
    private static final String Driver = "com.mysql.jdbc.Driver";
    // Database user credentials
    private static final String username = "U08Qnw";
    private static final String password = "53689362807";
    // Driver connection reference
    public static Connection conn;
    
    // Connect to database if located and auth is successful
    public static void connect() {
        try {
            // Connect to database
            Class.forName(Driver);
            conn = (Connection)DriverManager.getConnection(jdbcURL, username, password);
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
    public static Connection getConnection() {
        return conn;
    }
    
    // Close Database Connection
    public static void disconnect() {
        try {
            conn.close();
            System.out.println("Disconnected from MySQL Database");
        } catch (SQLException e) {
            System.out.println("SQL Exception: " + e.getMessage());
        }
    }
}
