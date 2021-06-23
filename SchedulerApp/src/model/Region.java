package model;

/**
 *
 * @author Christian Dye
 */
public class Region {

    private String regionName;
    private int regionID;

    public Region(String regionName, int regionID) {
        this.regionName = regionName;
        this.regionID = regionID;
    }

    public Region() {

    }

    public int getRegionID() {
        return regionID;
    }

    public void setRegionID(int regionID) {
        this.regionID = regionID;
    }

}
