package com.mnc.parking.model;

import com.mnc.parking.enums.SpotType;
import com.mnc.parking.enums.TicketStatus;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

public class Ticket {
    final String ticketId;
    final int lotId;
    final int floorId;
    final String spotId;
    final SpotType spotType;
    final String entryGateId;
    TicketStatus status;
    String exitGateId;
    final Instant entryTime;
    Instant exitTime;
    Duration duration;

    public Ticket(int lotId, int floorId, String spotId, SpotType spotType, String entryGateId) {
        this.ticketId = UUID.randomUUID().toString().split("-")[0];
        this.lotId = lotId;
        this.floorId = floorId;
        this.spotId = spotId;
        this.spotType = spotType;
        this.entryGateId = entryGateId;
        this.entryTime = Instant.now();
        this.status = TicketStatus.OPENED;
    }

    public synchronized void close(String exitGateId) {
        this.exitGateId = exitGateId;
        this.exitTime = Instant.now();
        this.duration = Duration.between(entryTime, exitTime);
        this.status = TicketStatus.CLOSED;
    }

    public String getTicketId() {
        return ticketId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public Duration getDuration() {
        return duration;
    }

    public boolean isOpen() {
        return this.status == TicketStatus.OPENED;
    }
}
