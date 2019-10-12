package com.cz2006.helloworld.models;

import com.google.android.gms.maps.model.Marker;

import java.util.Map;

/**
 * Represents Details of Map Locations
 *
 * @author Rosario Gelli Ann
 *
 */
public class MapDetail {
    private String name;
    private String addressPostalCode;
    private String addressUnitNumber;
    private String addressBuildingName;
    private String addressBlockHouseNumber;
    private String addressStreetName;
    private String description;
    private String coordinates;
    private String hyperlink;
    private Marker marker;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }

    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getAddressUnitNumber() {
        return addressUnitNumber;
    }

    public void setAddressUnitNumber(String addressUnitNumber) {
        this.addressUnitNumber = addressUnitNumber;
    }

    public String getAddressBuildingName() {
        return addressBuildingName;
    }

    public void setAddressBuildingName(String addressBuildingName) {
        this.addressBuildingName = addressBuildingName;
    }

    public String getAddressBlockHouseNumber() {
        return addressBlockHouseNumber;
    }

    public void setAddressBlockHouseNumber(String addressBlockHouseNumber) {
        this.addressBlockHouseNumber = addressBlockHouseNumber;
    }

    public String getAddressStreetName() {
        return addressStreetName;
    }

    public void setAddressStreetName(String addressStreetName) {
        this.addressStreetName = addressStreetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getHyperlink() {
        return hyperlink;
    }

    public void setHyperlink(String hyperlink) {
        this.hyperlink = hyperlink;
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }
}
