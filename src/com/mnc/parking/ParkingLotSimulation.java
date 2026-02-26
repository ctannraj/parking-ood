package com.mnc.parking;

import com.mnc.parking.enums.SpotType;
import com.mnc.parking.model.ParkingFloor;
import com.mnc.parking.model.ParkingLot;
import com.mnc.parking.model.ParkingSpot;
import com.mnc.parking.model.Ticket;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class ParkingLotSimulation {

    private ParkingLot parkingLot = null;

    private ParkingFloor getParkingFloor(int floor) {
        int MIN_SPOTS = 16;
        int MAX_SPOTS = 21;
        int numSpots = ThreadLocalRandom.current().nextInt(MIN_SPOTS, MAX_SPOTS);
        int cSpots = (int) Math.round(numSpots * 0.4);
        int lSpots = (int) Math.round(numSpots * 0.4);
        int mSpots = numSpots - cSpots - lSpots;

        List<ParkingSpot> spots = new ArrayList<>();

        for (int i=1; i<=cSpots; i++) {
            spots.add(new ParkingSpot(floor+"-C-"+i, SpotType.COMPACT));
        }
        for (int i=1; i<=lSpots; i++) {
            spots.add(new ParkingSpot(floor+"-L-"+i, SpotType.LARGE));
        }
        for (int i=1; i<=mSpots; i++) {
            spots.add(new ParkingSpot(floor+"-M-"+i, SpotType.MOTORCYCLE));
        }
        return new ParkingFloor(floor, spots);
    }

    private void initParkingLot(int numFloors) {
        List<ParkingFloor> floors = new ArrayList<>();
        for (int i=1; i<= numFloors; i++) {
            floors.add(getParkingFloor(i));
        }
        parkingLot = new ParkingLot(1, floors);
        System.out.printf("     >>>>>>>> Lot %d <<<<<<<<\n", parkingLot.getLotId());
        printLot();
    }

    public void printLot() {
        for (ParkingFloor floor : parkingLot.getFloors()) {
            System.out.printf(" Floor %d has %d/%d available spots : \n      ", floor.getFloorId(), floor.getAvailableSpots(), floor.getTotalSpots());
            for (Map.Entry<SpotType, Deque<String>> entry : floor.getAvailableSpotsByType().entrySet()) {
                System.out.printf("%s %s, ", entry.getKey(), entry.getValue().size());
            }
            System.out.println();
        }
        System.out.println("----------------------------------------");
    }

    private SpotType getRandomSpotType() {
        int index = ThreadLocalRandom.current().nextInt(0,SpotType.values().length);
        return SpotType.values()[index];
    }

    private String getRandomTicketId() {
        List<String> tickets = parkingLot.getOpenTicketIds();
        int index = ThreadLocalRandom.current().nextInt(0,tickets.size());
        return tickets.get(index);
    }

    private void run(int numFloors, int ticks) {

        if (parkingLot == null) {
            initParkingLot(numFloors);
        }

        Random random = new Random();
        double parkChance = 0.99;
        double exitChance = 0.25;
        boolean parked = false;
        for (int tick=1; tick<=ticks; tick++) {
            System.out.printf(" > tick %d\n", tick);
            if (parked && random.nextDouble() < exitChance) {
                String ticketId = getRandomTicketId();
                Ticket ticket = parkingLot.exit(ticketId, "");
                System.out.printf(" ---> Exit %s, duration %ds, freed spot %s\n", ticketId, ticket.getDuration().toSeconds(), ticket.getSpotType());
            }

            if (random.nextDouble() < parkChance) {
                SpotType spotType = getRandomSpotType();
                System.out.printf(" ---> Park %s\n", spotType.toString());
                parkingLot.park(spotType, "");
                parked = true;
            }

            printLot();

            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public static void main(String[] args) {
        ParkingLotSimulation simulation = new ParkingLotSimulation();
        simulation.run(5, 20);

        simulation.run(5, 5);
    }
}
