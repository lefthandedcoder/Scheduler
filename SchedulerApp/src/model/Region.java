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
