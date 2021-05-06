package pl.edu.ug.gdparkingapp;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import pl.edu.ug.gdparkingapp.models.ParkingValues;

public class Parking implements Serializable {
    private ParkingValues parking;
    private List<ParkingValues> parkings = new ArrayList<>();
    private int currentParkingId = 0;

    public Parking(ArrayList<ParkingValues> parkings) {
        this.parkings = parkings;
    }

    public ParkingValues getParking() {
        return parkings.get(currentParkingId);
    }

    public List<ParkingValues> getParkingsList() {
        return this.parkings;
    }

    // Metodę używam w przypadku gdy wchodzę z listy parkingów i ustawiam ID elementu od którego należy zaczać przewijanie.
    public void setCurrent(ParkingValues parkingValues) {
        for (int i=0; i<parkings.size(); i++) {
            if (parkingValues.getParkingName().getId() == parkings.get(i).getParkingName().getId()) {
                currentParkingId = i;
                return;
            }
        }
    }

    public int getCurrentParkingId() {
        return currentParkingId;
    }

//    public ParkingValues next() {
//        currentParkingId++;
//        if (currentParkingId > parkings.size() - 1) {
//            currentParkingId = 0;
//        }
//        return getParking();
//    }
//
//    public ParkingValues prev() {
//        currentParkingId--;
//        if (currentParkingId < 0) {
//            currentParkingId = parkings.size() - 1;
//        }
//        return getParking();
//    }
}

