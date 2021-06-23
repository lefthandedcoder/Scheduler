package model;

/**
 *
 * @author Christian Dye
 */
public class Customer {

    private int customerID;
    private String customerName;
    private String address;
    private String regionName;
    private String countryName;
    private String postalCode;
    private String phone;

    public Customer(int customerID, String customerName, String address, String regionName, String countryName, String postalCode, String phone) {
        this.customerID = customerID;
        this.customerName = customerName;
        this.address = address;
        this.regionName = regionName;
        this.countryName = countryName;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Customer() {
    }

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

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
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

}
