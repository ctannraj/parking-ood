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

    public void park(SpotType spotType, String entryGateId) {
        for (ParkingFloor floor : floors) {
            ParkingSpot spot = floor.markSpotOccupied(spotType);
            if (spot != null) {
                Ticket ticket = new Ticket(lotId, floor.getFloorId(), spot.getSpotId(), entryGateId);
                tickets.put(ticket.getTicketId(), ticket);
                return;
            }
        }
    }

    public Ticket exit(String ticketId, String exitGateId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket != null) {
            ParkingFloor floor = this.floors.stream().filter(f -> f.getFloorId() == ticket.floorId).findFirst().orElseThrow();
            floor.markSpotAvailable(ticket.spotId);
            ticket.close(exitGateId);
        }
        return ticket;
    }

}
