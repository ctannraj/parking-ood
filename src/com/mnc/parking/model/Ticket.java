package com.mnc.parking.model;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Ticket {
    final String ticketId;
    final int lotId;
    final int floorId;
    final String spotId;
    final String entryGateId;
    String exitGateId;
    final Instant entryTime;
    Instant exitTime;
    Duration duration;

    public Ticket(int lotId, int floorId, String spotId, String entryGateId) {
        this.ticketId = UUID.randomUUID().toString();
        this.lotId = lotId;
        this.floorId = floorId;
        this.spotId = spotId;
        this.entryGateId = entryGateId;
        this.entryTime = Instant.now();
    }

    public void close(String exitGateId) {
        this.exitGateId = exitGateId;
        this.exitTime = Instant.now();
        this.duration = Duration.between(entryTime, exitTime);
    }

    public String getTicketId() {
        return ticketId;
    }

    public Duration getDuration() {
        return duration;
    }
}
