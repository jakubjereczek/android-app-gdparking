package pl.edu.ug.gdparkingapp.models;

import android.location.Location;

import java.io.Serializable;
import java.util.List;

import pl.edu.ug.gdparkingapp.MyLocation;
import pl.edu.ug.gdparkingapp.MyLocationSerializable;

public class ParkingName implements Serializable {

    private int id;
    private String name;
    private String shortName;
    private String address;
    private String streetEntrance;
    private MyLocationSerializable location;


    public ParkingName(int id, String name, String shortName, String address, String streetEntrance, MyLocationSerializable location) {
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

    public MyLocationSerializable getLocation() {
        return location;
    }

    public void setLocation(MyLocationSerializable location) {
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
