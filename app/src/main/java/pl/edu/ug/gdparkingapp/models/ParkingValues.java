package pl.edu.ug.gdparkingapp.models;

import java.io.Serializable;
import java.util.Date;

public class ParkingValues implements Serializable{

    private ParkingName parkingName;
    private int availableSpots;
    private Date lastUpdate;


    public ParkingValues(ParkingName parkingName, int availableSpots, Date lastUpdate) {
        this.parkingName = parkingName;
        this.availableSpots = availableSpots;
        this.lastUpdate = lastUpdate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public ParkingName getParkingName() {
        return parkingName;
    }

}
