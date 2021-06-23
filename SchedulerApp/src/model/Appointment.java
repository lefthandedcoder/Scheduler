package model;

/**
 *
 * @author Christian Dye
 */
public class Appointment {

    private int appointmentID;
    private String title;
    private String description;
    private String location;
    private int contactID;
    private String contactName;
    private String type;
    private String start;
    private String end;
    private int apptCustomerID;
    private String apptCustomerName;
    private String postalCode;
    private String phone;
    private int userID;
    private String userName;

    public Appointment(int appointmentID, String title,
            String description, String location, int contactID,
            String contactName, String type, String start,
            String end, int apptCustomerID, String apptCustomerName,
            String postalCode, String phone, int userID, String userName) {
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
        this.userID = userID;
        this.userName = userName;
    }

    public Appointment() {
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

    public int getContactID() {
        return contactID;
    }

    public void setContactID(int contactID) {
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

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

}
