package pl.edu.ug.gdparkingapp.models;

import android.location.Location;

import java.util.List;

public class ParkingName {

    private int id;
    private String name;
    private String shortName;
    private String address;
    private String streetEntrance;
    private Location location;


    public ParkingName(int id, String name, String shortName, String address, String streetEntrance, Location location) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.address = address;
        this.streetEntrance = streetEntrance;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStreetEntrance() {
        return streetEntrance;
    }

    public void setStreetEntrance(String streetEntrance) {
        this.streetEntrance = streetEntrance;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    static public ParkingName findParkingNameById(List<ParkingName> list, int id) {
        for (ParkingName parkingName : list) {
            if (parkingName.getId() == id) {
                return parkingName;
            }
        }
        return null;

    }
}
