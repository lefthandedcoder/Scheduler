package model;

/**
 *
 * @author Christian Dye
 */
public class Country {

    /**
     * The country name
     */
    private String countryName;

    /**
     * Constructor with the country name
     * @param countryName
     */
    public Country(String countryName) {
        this.countryName = countryName;
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
}
