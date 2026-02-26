package com.mnc.parking.model;

import com.mnc.parking.enums.SpotType;

import java.util.*;

public class ParkingLot {
    private final int lotId;
    private final int numFloors;
    final List<ParkingFloor> floors;
    Map<String, Ticket> tickets = new HashMap<>();

    public ParkingLot(int id, List<ParkingFloor> floors) {
        this.lotId = id;
        this.floors = floors;
        this.numFloors = floors.size();
    }

    public int getLotId() {
        return lotId;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public List<String> getTicketIds() {
        return Collections.unmodifiableList(tickets.keySet().stream().toList());
    }

    public Ticket park(SpotType spotType, String entryGateId) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.markSpotOccupied(spotType);
            if (spot != null) {
                spot.markOccupied();
                Ticket ticket = new Ticket(lotId, floor.getFloorId(), spot.getSpotId(), entryGateId);
                tickets.put(ticket.getTicketId(), ticket);
                return ticket;
            }
        }
        return null;
    }

    public Ticket exit(String ticketId, String exitGateId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket != null) {
            ticket.close(exitGateId);
            ParkingFloor floor = this.floors.stream().filter(f -> f.getFloorId() == ticket.floorId).findFirst().orElseThrow();
            ParkingSpot spot = floor.getSpot(ticket.spotId);
            spot.markAvailable();
            floor.markSpotAvailable(spot.getSpotId());
        }
        return ticket;
    }

    public String toString() {
        return String.format("%d %s", lotId, floors);
    }

}
