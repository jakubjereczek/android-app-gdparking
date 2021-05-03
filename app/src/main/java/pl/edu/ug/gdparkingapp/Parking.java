package pl.edu.ug.gdparkingapp;

import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class Parking {
    private ParkingValues parking;
    private List<ParkingValues> parkings = new ArrayList<>();
    private int currentParkingId = 0;

    public Parking(List<ParkingValues> parkings) {
        this.parkings = parkings;
    }

    public ParkingValues getParking() {
        return parkings.get(currentParkingId);
    }

    public ParkingValues next() {
        currentParkingId++;
        if (currentParkingId > parkings.size() - 1) {
            currentParkingId = 0;
        }
        return getParking();
    }

    public ParkingValues prev() {
        currentParkingId--;
        if (currentParkingId < 0) {
            currentParkingId = parkings.size() - 1;
        }
        return getParking();
    }
}

