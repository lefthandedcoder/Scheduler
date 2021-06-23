package utilities;

import static controller.LoginController.currentUser;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian Dye
 */
public class DBAppointment {

    /**
     * Establishes date and time format for all visible dates and times
     */
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Establishes date and time format for all visible times
     */
    public static DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm:ss");

    //Adding appointment, note appointment ID auto increments

    /**
     * Adds new appointment to database based on user input
     * @param appointment
     * @return
     */
    public static Appointment addAppointment(Appointment appointment) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO appointments "
                    + "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)");
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getStart(), dtf).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.parse(appointment.getEnd(), dtf).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
            statement.setString(7, currentUser.getUsername());
            statement.setString(8, currentUser.getUsername());
            statement.setInt(9, appointment.getApptCustomerID());
            statement.setInt(10, appointment.getUserID());
            statement.setInt(11, appointment.getContactID());
            statement.executeUpdate();
            System.out.println("Appointment added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }

    //Updating appointment

    /**
     * Updates appointment in database based on user input
     * @param appointment
     */
    public static void updateAppointment(Appointment appointment) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("UPDATE appointments "
                    + "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? "
                    + "WHERE Appointment_ID=?");
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getStart(), dtf).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
            statement.setTimestamp(6, Timestamp.valueOf(LocalDateTime.parse(appointment.getEnd(), dtf).atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneId.of("UTC")).format(dtf)));
            statement.setString(7, currentUser.getUsername());
            statement.setInt(8, appointment.getApptCustomerID());
            statement.setInt(9, appointment.getUserID());
            statement.setInt(10, appointment.getContactID());
            statement.setInt(11, appointment.getAppointmentID());
            statement.executeUpdate();
            System.out.println("Appointment udpdated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Deleting appointment

    /**
     * Deletes appointment from database
     * @param appointment
     */
    public static void deleteAppointment(Appointment appointment) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");
            statement.setInt(1, appointment.getAppointmentID());
            statement.executeUpdate();
            System.out.println("Appointment deleted from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Code below is for interacting with the database and for displaying, adding, modifying, and deleting appointments

    /**
     * Stores list of all appointments
     */
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    //Get appointment from system

    /**
     * Gets appointment info from database based on appointment ID
     * @param id
     * @return
     */
    public static Appointment getAppointment(int id) {
        try {
            // Pulling appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM appointments WHERE Appointment_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setTitle(rs.getString("Title"));
                statement.close();
                System.out.println("Appointment found.");
                return appointment;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    //Get all appointments from database    

    /**
     * Gets all appointments from database
     * @return
     */
    public static ObservableList<Appointment> getAllAppointments() {
        try {
            //Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, "
                    + "appointments.Start, appointments.End, appointments.Contact_ID, contacts.Contact_Name, appointments.Customer_ID, customers.Phone, customers.Customer_Name, "
                    + "customers.Postal_Code, appointments.User_ID, users.User_Name "
                    + "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                    + "INNER JOIN customers on appointments.Customer_ID = customers.Customer_ID "
                    + "INNER JOIN users on appointments.User_ID = users.User_ID";
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

    //Get all contact names from database    

    /**
     * Stores list of all contact names from database
     */
    private static ObservableList<String> allContactNames = FXCollections.observableArrayList();

    /**
     * Gets list of all contact names
     * @return
     */
    public static ObservableList<String> getAllContactNames() {
        try {
            //Pulling all contact info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM contacts";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                String name;
                //Filtering for just contact names
                name = rs.getString("Contact_Name");
                allContactNames.add(name);
            }
            statement.close();
            return allContactNames;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    //Get contact ID from database

    /**
     * Stores contact ID
     */
    private static Integer contactID;

    /**
     * Gets contact ID base don contact name
     * @param contactName
     * @return
     */
    public static Integer getContactID(String contactName) {
        try {
            //Pulling contact info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT Contact_ID FROM contacts WHERE Contact_Name='" + contactName + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                //Filtering for specific contact's ID
                contactID = rs.getInt("Contact_ID");
                statement.close();
                System.out.println("Contact found.");
                return contactID;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }
    
    private static String contactName;
    
    public static String getContactName(int contactID) {
        try {
            //Pulling contact info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT Contact_Name FROM contacts WHERE Contact_Name='" + contactID + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                //Filtering for specific contact's ID
                contactName = rs.getString("Contact_Name");
                statement.close();
                System.out.println("Contact found.");
                return contactName;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }
    
    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting appointments

    /**
     * Stores list of all appointments for specific contact
     */
    private static ObservableList<Appointment> allAppointmentsForContact = FXCollections.observableArrayList();

    // Get appointment from system

    /**
     * Gets appointments for contact based on specific contact ID
     * @param id
     * @return
     */
    public static ObservableList<Appointment> getAppointmentsForContact(int id) {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, "
                    + "appointments.Start, appointments.End, appointments.Contact_ID, contacts.Contact_Name, appointments.Customer_ID, customers.Phone, customers.Customer_Name, "
                    + "customers.Postal_Code, appointments.User_ID, users.User_Name "
                    + "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                    + "INNER JOIN customers on appointments.Customer_ID = customers.Customer_ID "
                    + "INNER JOIN users on appointments.User_ID = users.User_ID "
                    + "WHERE contacts.Contact_ID ='" + id + "'";
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
                allAppointmentsForContact.add(appointment);
            }
            statement.close();
            return allAppointmentsForContact;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
}
