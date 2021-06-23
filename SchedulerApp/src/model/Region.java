package model;

/**
 *
 * @author Christian Dye
 */
public class Region {

    /**
     * The region name
     */
    private String regionName;

    /**
     * The region ID
     */
    private int regionID;

    /**
     * Region constructor
     * @param regionName
     * @param regionID
     */
    public Region(String regionName, int regionID) {
        this.regionName = regionName;
        this.regionID = regionID;
    }

    /**
     * Blank region constructor
     */
    public Region() {

    }

    /**
     * Gets the region ID
     * @return
     */
    public int getRegionID() {
        return regionID;
    }

    /**
     * Sets the region ID
     * @param regionID
     */
    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

}
