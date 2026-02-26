package com.mnc.parking.model;

import com.mnc.parking.enums.SpotStatus;
import com.mnc.parking.enums.SpotType;

public class ParkingSpot {
    private final String spotId;
    private final SpotType type;
    private SpotStatus status;

    public ParkingSpot(String id, SpotType type) {
        this.spotId = id;
        this.type = type;
        this.status = SpotStatus.AVAILABLE;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getType() {
        return type;
    }

    public SpotStatus getStatus() {
        return status;
    }

    public synchronized void markOccupied() {
        if (this.status == SpotStatus.AVAILABLE) {
            this.status = SpotStatus.OCCUPIED;
        }
    }

    public synchronized void markAvailable() {
        if (this.status == SpotStatus.OCCUPIED) {
            this.status = SpotStatus.AVAILABLE;
        }
    }

    public boolean isAvailable() {
        return (this.status == SpotStatus.AVAILABLE);
    }

    public String toString() {
        return String.format("%s %s", spotId, type);
    }
}
