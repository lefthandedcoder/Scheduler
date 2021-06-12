/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static utilities.DBConnection.conn;
import utilities.DBQuery;
import java.sql.*;

/**
 *
 * @author chris
 */
public class Customer {
    private int customerID;
    private String customerName;
    private String address;
    private String divisionName;
    private String countryName;
    private String postalCode;
    private String phone;

    public Customer(int customerID, String customerName, String address, String divisionName, String countryName, String postalCode, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    public Customer(){}

    public int getCustomerID() {
        return customerID;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    //add customer
    //modify customer
    //delete customer
    
    // Code below is for interacting with the database and for displaying, adding, modifying, and deleting customers
    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private static int globalCustomerID = 0;
    
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
