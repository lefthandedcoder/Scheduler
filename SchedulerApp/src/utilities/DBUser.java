package utilities;

import static controller.LoginController.currentUser;
import java.sql.*;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import static utilities.DBAppointment.dtf;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian Dye
 */
public class DBUser {

    // Get all user names from database    

    /**
     * Stores list of all usernames in database
     */
    private static ObservableList<String> allUserNames = FXCollections.observableArrayList();

    /**
     * Gets all usernames from database
     * @return
     */
    public static ObservableList<String> getAllUserNames() {
        try {
            // Pulling all user info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String name;
                //Filtering by user names
                name = rs.getString("User_Name");
                allUserNames.add(name);
            }
            statement.close();
            return allUserNames;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Get user ID from database

    /**
     * Stores user ID
     */
    private static int userID;

    /**
     *  Gets user ID from database for specific username
     * @param userName
     * @return
     */
    public static Integer getUserID(String userName) {
        try {
            // Pulling specific user info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT User_ID FROM users "
                    + "WHERE users.User_Name='" + userName + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                userID = rs.getInt("User_ID");
                statement.close();
                System.out.println("User found.");
                return userID;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    /**
     * Stores list of all appointments for current user
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    // Get all user appointments from database for current user

    /**
     * Gets all appointments from database for current user
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments() {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            int id = currentUser.getUserID();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, "
                    + "appointments.Start, appointments.End, appointments.Contact_ID, contacts.Contact_Name, appointments.Customer_ID, customers.Phone, customers.Customer_Name, "
                    + "customers.Postal_Code, appointments.User_ID, users.User_Name "
                    + "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                    + "INNER JOIN customers on appointments.Customer_ID = customers.Customer_ID "
                    + "INNER JOIN users on appointments.User_ID = users.User_ID "
                    + "WHERE users.User_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {

                Appointment appointment = new Appointment(
                        rs.getInt("Appointment_ID"),
                        rs.getString("Title"),
                        rs.getString("Description"),
                        rs.getString("Location"),
                        rs.getInt("Contact_ID"),
                        rs.getString("Contact_Name"),
                        rs.getString("Type"),
                        rs.getTimestamp("Start").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(dtf),
                        rs.getTimestamp("End").toLocalDateTime().atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).format(dtf),
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"),
                        rs.getInt("User_ID"),
                        rs.getString("User_Name"));
                allAppointments.add(appointment);
            }
            statement.close();
            return allAppointments;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
