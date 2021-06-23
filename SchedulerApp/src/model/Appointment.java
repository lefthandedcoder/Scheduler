package model;

/**
 *
 * @author Christian Dye
 */
public class Appointment {

    /**
     * The appointment ID
     */
    private int appointmentID;

    /**
     * The title
     */
    private String title;

    /**
     * The description
     */
    private String description;

    /**
     * The location
     */
    private String location;

    /**
     * The contact ID
     */
    private int contactID;

    /**
     * The contact name
     */
    private String contactName;

    /**
     * The type
     */
    private String type;

    /**
     * The start date and time
     */
    private String start;

    /**
     * The end date and time
     */
    private String end;

    /**
     * The customer ID
     */
    private int apptCustomerID;

    /**
     * The customer name
     */
    private String apptCustomerName;

    /**
     * The potsal code
     */
    private String postalCode;

    /**
     * The phone
     */
    private String phone;

    /**
     * The user ID
     */
    private int userID;

    /**
     * The username
     */
    private String userName;

    /**
     *
     * @param appointmentID
     * @param title
     * @param description
     * @param location
     * @param contactID
     * @param contactName
     * @param type
     * @param start
     * @param end
     * @param apptCustomerID
     * @param apptCustomerName
     * @param postalCode
     * @param phone
     * @param userID
     * @param userName
     */
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

    /**
     * Empty appointment constructor
     */
    public Appointment() {
    }

    /**
     * Gets the appointment ID
     * @return
     */
    public int getAppointmentID() {
        return appointmentID;
    }

    /**
     * Sets the appointment ID
     * @param appointmentID
     */
    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    /**
     * Gets the title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the description
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the location
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the contact ID
     * @return
     */
    public int getContactID() {
        return contactID;
    }

    /**
     * Sets the contact ID
     * @param contactID
     */
    public void setContactID(int contactID) {
        this.contactID = contactID;
    }

    /**
     * Gets the contact name
     * @return
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * Sets the contact name
     * @param contactName
     */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**
     * Gets the type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the type
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start date and time
     * @return
     */
    public String getStart() {
        return start;
    }

    /**
     * Sets the start date and time
     * @param start
     */
    public void setStart(String start) {
        this.start = start;
    }

    /**
     * Gets the end date and time
     * @return
     */
    public String getEnd() {
        return end;
    }

    /**
     * Sets the end date and time
     * @param end
     */
    public void setEnd(String end) {
        this.end = end;
    }

    /**
     * Gets the customer ID
     * @return
     */
    public int getApptCustomerID() {
        return apptCustomerID;
    }

    /**
     * Sets the customer ID
     * @param apptCustomerID
     */
    public void setApptCustomerID(int apptCustomerID) {
        this.apptCustomerID = apptCustomerID;
    }

    /**
     * Gets the customer name
     * @return
     */
    public String getApptCustomerName() {
        return apptCustomerName;
    }

    /**
     * Sets the customer name
     * @param apptCustomerName
     */
    public void setApptCustomerName(String apptCustomerName) {
        this.apptCustomerName = apptCustomerName;
    }

    /**
     * Gets the postal code
     * @return
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Sets the postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Gets the phone
     * @return
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets the user ID
     * @return
     */
    public int getUserID() {
        return userID;
    }

    /**
     * Sets the user ID
     * @param userID
     */
    public void setUserID(int userID) {
        this.userID = userID;
    }

    /**
     * Gets the username
     * @return
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the username
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

}
