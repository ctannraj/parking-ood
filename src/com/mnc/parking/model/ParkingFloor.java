package com.mnc.parking.model;

import com.mnc.parking.enums.SpotType;

import java.util.*;

public class ParkingFloor {
    private final int floorId;
    private final int totalSpots;
    private int availableSpots;
    private final Map<String, ParkingSpot> parkingSpots = new HashMap<>();
    private final EnumMap<SpotType, Deque<String>> availableSpotsByType = new EnumMap<>(SpotType.class);

    public ParkingFloor(int id, List<ParkingSpot> spots) {
        this.floorId = id;
        this.totalSpots = spots.size();
        this.availableSpots = this.totalSpots;
        for (ParkingSpot spot : spots) {
            parkingSpots.put(spot.getSpotId(), spot);
            availableSpotsByType.putIfAbsent(spot.getType(), new ArrayDeque<>());
            if (spot.isAvailable()) {
                availableSpotsByType.get(spot.getType()).addLast(spot.getSpotId());
            }
        }
    }

    public int getFloorId() {
        return floorId;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public Map<SpotType, Deque<String>> getAvailableSpotsByType() {
        return Collections.unmodifiableMap(availableSpotsByType);
    }

    public ParkingSpot getSpot(String spotId) {
        return parkingSpots.get(spotId);
    }

    public ParkingSpot markSpotOccupied(SpotType spotType) {
        Deque<String> q = availableSpotsByType.get(spotType);
        ParkingSpot spot = null;
        while (!q.isEmpty()) {
            String spotId = q.removeFirst();
            spot = parkingSpots.get(spotId);
            if (spot != null && spot.isAvailable() && spot.getType() == spotType) {
                availableSpots--;
                break;
            }
        }
        return spot;
    }

    public void markSpotAvailable(String spotId) {
        ParkingSpot spot = parkingSpots.get(spotId);
        if (spot != null) {
            spot.markAvailable();
            availableSpotsByType.get(spot.getType()).addLast(spot.getSpotId());
            availableSpots++;
        }
    }

}
