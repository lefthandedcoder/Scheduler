package model;

/**
 *
 * @author Christian Dye
 */
public class Customer {

    /**
     * The customer ID
     */
    private int customerID;

    /**
     * The customer name
     */
    private String customerName;

    /**
     * The address
     */
    private String address;

    /**
     * The region name
     */
    private String regionName;

    /**
     * The region ID
     */
    private int regionID;
    
    /**
     * The country name
     */
    private String countryName;

    /**
     * The postal code
     */
    private String postalCode;

    /**
     * The phone
     */
    private String phone;

    /**
     *
     * @param customerID
     * @param customerName
     * @param address
     * @param regionName
     * @param regionID
     * @param countryName
     * @param postalCode
     * @param phone
     */
    public Customer(int customerID, String customerName, String address, String regionName, int regionID, String countryName, String postalCode, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.regionName = regionName;
        this.regionID = regionID;
        this.countryName = countryName;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    /**
     * Blank customer constructor
     */
    public Customer() {
    }

    /**
     * Gets the customer ID
     * @return
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     * Sets the customer ID
     * @param customerID
     */
    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     * Gets the customer name
     * @return
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Gets the address
     * @return
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the customer address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Gets the region name
     * @return
     */
    public String getRegionName() {
        return regionName;
    }

    /**
     * Sets the region name
     * @param regionName
     */
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    /**
     * Gets the region ID
     * @return
     */
    public int getRegionID() {
        return regionID;
    }

    /**
     * Gets the country name
     * @return
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the country name
     * @param countryName
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
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

}
