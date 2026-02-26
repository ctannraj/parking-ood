package com.mnc.parking.model;

import com.mnc.parking.enums.SpotType;

import java.util.*;

public class ParkingLot {
    private final int lotId;
    private final int numFloors;
    final Map<Integer, ParkingFloor> floors = new HashMap<>();
    Map<String, Ticket> tickets = new HashMap<>();

    public ParkingLot(int id, List<ParkingFloor> _floors) {
        this.lotId = id;
        for (ParkingFloor floor : _floors) {
            floors.put(floor.getFloorId(), floor);
        }
        this.numFloors = floors.size();
    }

    public int getLotId() {
        return lotId;
    }

    public int getNumFloors() {
        return numFloors;
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors.values().stream().toList());
    }

    public List<String> getOpenTicketIds() {
        return Collections.unmodifiableList(tickets.values().stream().filter(Ticket::isOpen).map(Ticket::getTicketId).toList());
    }

    public void park(SpotType spotType, String entryGateId) {
        for (Map.Entry<Integer, ParkingFloor> entry : floors.entrySet()) {
            ParkingFloor floor = entry.getValue();
            ParkingSpot spot = floor.markSpotOccupied(spotType);
            if (spot != null) {
                Ticket ticket = new Ticket(lotId, floor.getFloorId(), spot.getSpotId(), spot.getType(), entryGateId);
                tickets.put(ticket.getTicketId(), ticket);
                return;
            }
        }
    }

    public Ticket exit(String ticketId, String exitGateId) {
        Ticket ticket = tickets.get(ticketId);
        if (ticket != null) {
            ParkingFloor floor = floors.get(ticket.floorId);
            floor.markSpotAvailable(ticket.spotId);
            ticket.close(exitGateId);
        }
        return ticket;
    }

}
