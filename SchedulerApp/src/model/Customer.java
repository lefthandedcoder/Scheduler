/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author chris
 */
public class Customer {
    private int customerID;
    private String address;
    private String divisionName;
    private String countryName;
    private int postalCode;
    private String phone;

    public Customer(int customerID, String address, String divisionName, String countryName, int postalCode, String phone) {
        this.customerID = customerID;
        this.address = address;
        this.divisionName = divisionName;
        this.countryName = countryName;
        this.postalCode = postalCode;
        this.phone = phone;
    }
    
    
    
    
}
