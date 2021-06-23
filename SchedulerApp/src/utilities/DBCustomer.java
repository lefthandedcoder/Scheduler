package utilities;

import static controller.LoginController.currentUser;
import java.sql.*;
import java.time.ZoneId;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Customer;
import model.Region;
import static utilities.DBAppointment.dtf;
import static utilities.DBConnection.conn;

/**
 *
 * @author Christian
 */
public class DBCustomer {

    //Adding customer, note customer ID auto increments
    public static Customer addCustomer(Customer customer) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("INSERT INTO customers "
                    + "(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, DIVISION_ID) "
                    + "VALUES (?, ?, ?, ?, CURRENT_TIMESTAMP, ?, CURRENT_TIMESTAMP, ?, ?)");
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, currentUser.getUsername());
            statement.setString(6, currentUser.getUsername());
            statement.setInt(7, DBRegion.getRegion(customer.getRegionName()).getRegionID());
            statement.executeUpdate();
            System.out.println("Customer added to database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    //Updating customer
    public static void updateCustomer(Customer customer) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("UPDATE customers "
                    + "SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Last_Update=CURRENT_TIMESTAMP, Last_Updated_By=?, DIVISION_ID=? "
                    + "WHERE Customer_ID=?");
            statement.setString(1, customer.getCustomerName());
            statement.setString(2, customer.getAddress());
            statement.setString(3, customer.getPostalCode());
            statement.setString(4, customer.getPhone());
            statement.setString(5, currentUser.getUsername());
            statement.setInt(6, DBRegion.getRegion(customer.getRegionName()).getRegionID());
            statement.setInt(7, customer.getCustomerID());
            statement.executeUpdate();
            System.out.println("Customer udpdated in database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Deleting customer
    public static void deleteCustomer(Customer customer) {
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM customers WHERE Customer_ID=?");
            statement.setInt(1, customer.getCustomerID());
            statement.executeUpdate();
            System.out.println("Customer deleted from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting customers
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    // Get specific customer info from database
    public static String getCustomerName(int id) {
        try {
            // Pulling customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM customers WHERE Customer_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String name = rs.getString("Customer_Name");
                statement.close();
                System.out.println("Customer found.");
                return name;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    // Get specific customer info from database
    public static Integer getCustomerID(String name) {
        try {
            // Pulling customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM customers WHERE Customer_Name='" + name + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("Customer_ID");
                statement.close();
                System.out.println("Customer found.");
                return id;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    // Get customer phone from database
    public static String getCustomerPhone(String name) {
        try {
            // Pulling customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT Phone FROM customers WHERE Customer_Name='" + name + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String phone = rs.getString("Phone");
                statement.close();
                System.out.println("Customer found.");
                return phone;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    // Get customer postal code from database
    public static String getCustomerPostalCode(String name) {
        try {
            // Pulling customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT Postal_Code FROM customers WHERE Customer_Name='" + name + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                String postalCode = rs.getString("Postal_Code");
                statement.close();
                System.out.println("Customer found.");
                return postalCode;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }

        return null;
    }

    // Get all customers from database    
    public static ObservableList<Customer> getAllCustomers() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT customers.Customer_ID, customers.Customer_Name, customers.Address, first_level_divisions.Division, countries.Country, customers.Postal_Code, customers.Phone"
                    + " FROM customers INNER JOIN first_level_divisions ON customers.Division_ID = first_level_divisions.Division_ID "
                    + "INNER JOIN countries ON first_level_divisions.Country_ID = countries.Country_ID";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("Customer_ID"),
                        rs.getString("Customer_Name"),
                        rs.getString("Address"),
                        rs.getString("Division"),
                        rs.getString("Country"),
                        rs.getString("Postal_Code"),
                        rs.getString("Phone"));
                allCustomers.add(customer);
            }
            statement.close();
            return allCustomers;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Getting customer for appointments from database
    public static Customer getCustomer(int id) {
        try {
            // Pulling appointment info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM appointments "
                    + "INNER JOIN customers "
                    + "on appointments.Customer_ID = customers.Customer_ID WHERE appointments.Customer_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerName(rs.getString("Customer_Name"));
                statement.close();
                System.out.println("Customer found.");
                return customer;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
        }
        return null;
    }

    private static ObservableList<String> allCustomerNames = FXCollections.observableArrayList();

    // Get all customer names from database    
    public static ObservableList<String> getAllCustomerNames() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * from customers";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
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

    private static ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();

    // Get all customer IDs from database    
    public static ObservableList<Integer> getAllCustomerIDs() {
        try {
            // Pulling all customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * from customers";
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                int id;
                id = rs.getInt("Customer_ID");
                allCustomerIDs.add(id);
            }
            statement.close();
            return allCustomerIDs;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting appointments
    private static ObservableList<Appointment> allAppointmentsForCustomer = FXCollections.observableArrayList();

    // Get appointment from system
    public static ObservableList<Appointment> getAppointmentsForCustomer(int id) {
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
                    + "WHERE customers.Customer_ID ='" + id + "'";
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
                allAppointmentsForCustomer.add(appointment);
            }
            statement.close();
            return allAppointmentsForCustomer;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            return null;
        }
    }

}
