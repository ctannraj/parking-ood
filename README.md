# Parking Lot Management System

A comprehensive Object-Oriented Design (OOD) implementation of a multi-floor parking lot management system built in Java.

## Project Overview

This project demonstrates core OOD principles through a realistic parking lot management system. It simulates a parking facility with multiple floors, different vehicle types, parking spot allocation, and ticket-based vehicle tracking.

## Architecture

### Core Classes

#### Model Classes
- **ParkingLot**: Main controller managing the entire parking lot
  - Manages multiple floors
  - Handles vehicle parking and exit operations
  - Maintains ticket records for all parked vehicles
  
- **ParkingFloor**: Represents a single floor in the parking lot
  - Tracks available and occupied parking spots
  - Organizes spots by type (Compact, Large, Motorcycle)
  - Manages spot allocation and deallocation

- **ParkingSpot**: Represents an individual parking spot
  - Has a unique identifier and spot type
  - Maintains availability status
  - Provides thread-safe status transitions

- **Ticket**: Represents a parking session
  - Generated when a vehicle parks
  - Stores entry/exit information and timestamps
  - Tracks location (lot, floor, spot) and gate information
  - Calculates parking duration upon vehicle exit

#### Enums
- **SpotType**: Defines available parking spot types
  - `COMPACT`: For compact vehicles
  - `LARGE`: For large vehicles
  - `MOTORCYCLE`: For motorcycles

- **SpotStatus**: Tracks spot availability
  - `AVAILABLE`: Spot is free
  - `OCCUPIED`: Spot is taken

### Simulation

- **ParkingLotSimulation**: Main entry point that demonstrates the system
  - Dynamically generates parking floors with random spot distributions
  - Simulates realistic parking and exit patterns
  - Provides real-time status tracking via tick-based updates

## Features

### Parking Operations
- **Smart Spot Allocation**: Automatically finds available spots based on vehicle type
- **Multi-Floor Management**: Distributes parking across multiple floors
- **Flexible Spot Distribution**: 40% compact, 40% large, 20% motorcycle per floor

### Exit Operations
- **Ticket Validation**: Validates tickets before allowing vehicle exit
- **Spot Liberation**: Properly deallocates spots when vehicles exit
- **Tracking**: Records entry and exit gate information with timestamps

### Simulation Capabilities
- **Random Parking Events**: Simulates real-world parking patterns (99% park chance per tick)
- **Random Exit Events**: Simulates vehicles leaving (15% exit chance per tick)
- **Real-time Monitoring**: Displays lot status with available spots by type
- **Time-based Simulation**: 5-second intervals between simulation ticks

## Project Structure

```
parking-ood/
├── src/
│   └── com/
│       └── mnc/
│           └── parking/
│               ├── ParkingLotSimulation.java
│               ├── enums/
│               │   ├── SpotStatus.java
│               │   └── SpotType.java
│               └── model/
│                   ├── ParkingLot.java
│                   ├── ParkingFloor.java
│                   ├── ParkingSpot.java
│                   └── Ticket.java
```

## Usage

### Running the Simulation

```bash
javac -d out src/com/mnc/parking/**/*.java
java -cp out com.mnc.parking.ParkingLotSimulation
```

### Sample Output

```
     >>>>>>>> Lot 1 <<<<<<<<
 > tick 0
 Floor 1 has 16/16 available spots : 
      COMPACT 6, LARGE 7, MOTORCYCLE 3, 
----------------------------------------
 ---> Park LARGE
 ---> Exit [ticket-id] Duration 20s
 Floor 1 has 15/16 available spots : 
      COMPACT 6, LARGE 6, MOTORCYCLE 3,
```

## Key Design Patterns Used

1. **Object Composition**: ParkingLot contains ParkingFloors which contain ParkingSpots
2. **Encapsulation**: Private attributes with controlled access methods
3. **Collections API**: Efficient use of HashMap, EnumMap, and Deque for data management
4. **Immutability**: Unmodifiable collections returned to prevent external state corruption
5. **Thread Safety**: Synchronized methods on ParkingSpot for concurrent access

## Technical Details

### Spot Allocation Algorithm
- Iterates through floors searching for available spots of the requested type
- Uses a queue (Deque) to maintain order and efficiency
- Validates spot availability before allocation

### Status Management
- Bi-directional state transitions (AVAILABLE ↔ OCCUPIED)
- Synchronized methods prevent race conditions in concurrent scenarios
- Automatic update of available spot counts

### Ticket System
- UUID-based unique ticket identification
- Timestamp recording for entry and exit operations
- Duration automatically calculated as the difference between exit and entry times
- Provides parking duration for billing and analytics purposes
- Immutable ticket records for audit trails

## Dependencies

- **Java 8+** (Uses Collections, UUID, Instant for timestamps)
- **Standard Library Only** (No external dependencies)

## Future Enhancements

- Vehicle pricing model based on parking duration and spot type
- Payment processing system
- Entrance/Exit gate management
- Reservation system
- Real-time occupancy rate monitoring
- Database integration for persistent records
- REST API for parking queries
- Mobile app integration

## Author

Created as an Object-Oriented Design exercise for parking lot management systems.

## License

This project is provided as-is for educational purposes.

