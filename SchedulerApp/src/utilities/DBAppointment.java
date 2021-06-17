/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;
import static controller.LoginController.currentUser;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import static utilities.DBConnection.conn;


/**
 *
 * @author chris
 */
public class DBAppointment {
    
    public static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static DateTimeFormatter timeDTF = DateTimeFormatter.ofPattern("HH:mm");
    
    //Set appointment start date and time
    //Set appointment end date and time
    //monthly appointments
    //weekly appointments
    //save appointment
    //modify appointment
    //delete appointment
    //15 minute appointment alert
    //Prevent appointment overlap
    
    //add appointment, note appointment ID auto increments
    public static Appointment addAppointment(Appointment appointment) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO appointments "
                    + "(Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) "
                    + "VALUES (?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?, ?, ?)");
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getStart(), dtf)));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getEnd(), dtf)));
            statement.setString(7, currentUser.getUsername());
            statement.setString(8, currentUser.getUsername());
            statement.setInt(9, appointment.getApptCustomerID());
            statement.setInt(9, appointment.getContactID());
            statement.setInt(10, appointment.getUserID());
            statement.executeUpdate();
            System.out.println("Appointment added to database.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return appointment;
    }
    
    //update customer
    public static void updateAppointment (Appointment appointment) {        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("UPDATE appointments "
                    + "SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, Customer_ID=?, User_ID=?, Contact_ID=? "
                    + "WHERE Appointment_ID=?");
            statement.setString(1, appointment.getTitle());
            statement.setString(2, appointment.getDescription());
            statement.setString(3, appointment.getLocation());
            statement.setString(4, appointment.getType());
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getStart(), dtf)));
            statement.setTimestamp(5, Timestamp.valueOf(LocalDateTime.parse(appointment.getEnd(), dtf)));
            statement.setString(7, currentUser.getUsername());
            statement.setInt(8, appointment.getApptCustomerID());
            statement.setInt(9, appointment.getContactID());
            statement.setInt(10, appointment.getUserID());
            statement.setInt(11, appointment.getAppointmentID());
            statement.executeUpdate();
            System.out.println("Appointment udpdated in database.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //delete customer
    public static void deleteAppointment(Appointment appointment) {        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM appointments WHERE Appointment_ID=?");
            statement.setInt(1, appointment.getAppointmentID());
            statement.executeUpdate();
            System.out.println("Customer deleted from database.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting appointments
    private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    
    // Get appointment from system
    public static Appointment getAppointment(int id) {
        try {
            // Pulling appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM appointments WHERE Appointment_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
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

    // Get all appointments from database    
    public static ObservableList<Appointment> getAllAppointments() {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT appointments.Appointment_ID, appointments.Title, appointments.Description, appointments.Location, appointments.Type, "
                    + "appointments.Start, appointments.End, appointments.Contact_ID, contacts.Contact_Name, appointments.Customer_ID, customers.Phone, customers.Customer_Name, "
                    + "customers.Postal_Code, appointments.User_ID, users.User_Name "
                    + "FROM appointments INNER JOIN contacts ON appointments.Contact_ID = contacts.Contact_ID "
                    + "INNER JOIN customers on appointments.Customer_ID = customers.Customer_ID "
                    + "INNER JOIN users on appointments.User_ID = users.User_ID";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                
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
    
    private static ObservableList<String> allCustomerNames = FXCollections.observableArrayList();
    // Get all customer names from database    
    public static ObservableList<String> getAllCustomerNames() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT customers.Customer_Name from customers";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String name;
                name = rs.getString("Customer_Name");
                allCustomerNames.add(name);
            }
            statement.close();
            return allCustomerNames;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    private static ObservableList<String> allCustomerIDs = FXCollections.observableArrayList();
    // Get all customer IDs from database    
    public static ObservableList<String> getAllCustomerIDs() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT customers.Customer_Name from customers";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String name;
                name = rs.getString("Customer_ID");
                allCustomerIDs.add(name);
            }
            statement.close();
            return allCustomerIDs;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }
    
    // Get all contact names from database    
    private static ObservableList<String> allContactNames = FXCollections.observableArrayList();
    public static ObservableList<String> getAllContactNames() {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM contacts";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String name; 
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
    
    // Get all contact names from database    
    private static ObservableList<String> allUserNames = FXCollections.observableArrayList();
    public static ObservableList<String> getAllUserNames() {
        try {
            // Pulling all appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM users";
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                String name; 
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
    
}
