package pl.edu.ug.gdparkingapp;

import android.location.Location;

import java.io.Serializable;

public class MyLocation extends Location {

    public MyLocation(String provider) {
        super(provider);
    }

    public MyLocation() {
        super("");
    }
}
