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
public class Appointment {
    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private String contactID;
    private String contactName;
    private String type;
    private String start;
    private String end;
    private int apptCustomerID;
    private String apptCustomerName;
    private int postalCode;
    private String phone;
    private String user;

    public Appointment(int appointmentID, String title, String description, String location, String contactID, String contactName, String type, String start, String end, int apptCustomerID, String apptCustomerName, int postalCode, String phone, String user) {
        this.appointmentID = appointmentID;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contactID = contactID;
        this.contactName = contactName;
        this.type = type;
        this.start = start;
        this.end = end;
        this.apptCustomerID = apptCustomerID;
        this.apptCustomerName = apptCustomerName;
        this.postalCode = postalCode;
        this.phone = phone;
        this.user = user;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContactID() {
        return contactID;
    }

    public void setContactID(String contactID) {
        this.contactID = contactID;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getApptCustomerID() {
        return apptCustomerID;
    }

    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

    public String getApptCustomerName() {
        return apptCustomerName;
    }

    public void setApptCustomerName(String apptCustomerName) {
        this.apptCustomerName = apptCustomerName;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    //Set appointment start date and time
    //Set appointment end date and time
    //monthly appointments
    //weekly appointments
    //save appointment
    //modify appointment
    //delete appointment
    //15 minute appointment alert
    //Prevent appointment overlap
    
    
    
}
