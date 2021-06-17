/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;
import static controller.LoginController.currentUser;
import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Region;
import static utilities.DBConnection.conn;


/**
 *
 * @author chris
 */
public class DBCustomer {
    
    //add customer, note customer ID auto increments
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    //update customer
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
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //delete customer
    public static void deleteCustomer(Customer customer) {        
        try {
            PreparedStatement statement = DBConnection.getConnection().prepareStatement("DELETE FROM customers WHERE Customer_ID=?");
            statement.setInt(1, customer.getCustomerID());
            statement.executeUpdate();
            System.out.println("Customer deleted from database.");
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting customers
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    
    // Get customer from system
    public static Customer getCustomer(int id) {
        try {
            // Pulling customer info from database
            DBQuery.setStatement(conn);
            Statement statement = DBQuery.getStatement();
            String query = "SELECT * FROM customers WHERE Customer_ID='" + id + "'";
            ResultSet rs = statement.executeQuery(query);
            if(rs.next()) {
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
            while(rs.next()) {
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
    
}
