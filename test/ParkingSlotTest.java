import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

class ParkingSlotTest {
    private ParkingSlot slot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime conflictStart;
    private LocalDateTime conflictEnd;

    @BeforeEach
    void setUp() {
        slot = new ParkingSlot("SLOT001", ParkingSlotType.REGULAR);
        startTime = LocalDateTime.of(2023, 10, 15, 10, 0);
        endTime = LocalDateTime.of(2023, 10, 15, 12, 0);
        conflictStart = LocalDateTime.of(2023, 10, 15, 11, 0);
        conflictEnd = LocalDateTime.of(2023, 10, 15, 13, 0);
    }

    @Test
    void testParkingSlotConstructor() {
        assertEquals("SLOT001", slot.getSlotId(), "Slot ID should be set correctly");
        assertEquals(ParkingSlotType.REGULAR, slot.getSlotType(), "Slot type should be set correctly");
        assertTrue(slot.isActive(), "Slot should be active by default");
        assertNotNull(slot.getWallet(), "Slot should have a wallet");
        assertEquals(0.0, slot.getBalance(), 0.01, "Slot wallet should start with zero balance");
        assertNotNull(slot.getBookings(), "Slot should have bookings list");
        assertTrue(slot.getBookings().isEmpty(), "Bookings list should be empty initially");
    }

    @Test
    void testActivateDeactivate() {
        assertTrue(slot.isActive(), "Slot should be active initially");
        
        slot.deactivate();
        assertFalse(slot.isActive(), "Slot should be deactivated");
        
        slot.activate();
        assertTrue(slot.isActive(), "Slot should be reactivated");
    }

    @Test
    void testIsAvailableEmptySlot() {
        assertTrue(slot.isAvailable(startTime, endTime), "Empty slot should be available for any time window");
    }

    @Test
    void testIsAvailableWithNonOverlappingBooking() {
        // Add a booking that doesn't overlap
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking booking = new Booking(1, vehicle, slot, 
            LocalDateTime.of(2023, 10, 15, 8, 0), 
            LocalDateTime.of(2023, 10, 15, 9, 0), 50.0);
        slot.getBookings().add(booking);
        
        assertTrue(slot.isAvailable(startTime, endTime), "Slot should be available for non-overlapping time");
    }

    @Test
    void testIsAvailableWithOverlappingBooking() {
        // Add a booking that overlaps
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking booking = new Booking(1, vehicle, slot, startTime, endTime, 50.0);
        slot.getBookings().add(booking);
        
        assertFalse(slot.isAvailable(conflictStart, conflictEnd), "Slot should not be available for overlapping time");
    }

    @Test
    void testIsAvailableExactTimeMatch() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking booking = new Booking(1, vehicle, slot, startTime, endTime, 50.0);
        slot.getBookings().add(booking);
        
        assertFalse(slot.isAvailable(startTime, endTime), "Slot should not be available for exact same time");
    }

    @Test
    void testIsAvailableAdjacentBookings() {
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking booking = new Booking(1, vehicle, slot, startTime, endTime, 50.0);
        slot.getBookings().add(booking);
        
        // Check if available immediately after existing booking
        LocalDateTime nextStart = endTime;
        LocalDateTime nextEnd = nextStart.plusHours(2);
        assertTrue(slot.isAvailable(nextStart, nextEnd), "Slot should be available immediately after existing booking");
        
        // Check if available immediately before existing booking
        LocalDateTime prevEnd = startTime;
        LocalDateTime prevStart = prevEnd.minusHours(2);
        assertTrue(slot.isAvailable(prevStart, prevEnd), "Slot should be available immediately before existing booking");
    }

    @Test
    void testIsCompatibleDeactivatedSlot() {
        slot.deactivate();
        assertFalse(slot.isCompatible(VehicleType.CAR, startTime, endTime), 
            "Deactivated slot should not be compatible with any vehicle type");
    }

    @Test
    void testIsCompatibleMotorcycleWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertTrue(compactSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime), 
            "Motorcycle should be compatible with COMPACT slot");
        assertTrue(regularSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime), 
            "Motorcycle should be compatible with REGULAR slot");
        assertTrue(largeSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime), 
            "Motorcycle should be compatible with LARGE slot");
        assertFalse(handicappedSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime), 
            "Motorcycle should not be compatible with HANDICAPPED slot");
    }

    @Test
    void testIsCompatibleCarWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertFalse(compactSlot.isCompatible(VehicleType.CAR, startTime, endTime), 
            "Car should not be compatible with COMPACT slot");
        assertTrue(regularSlot.isCompatible(VehicleType.CAR, startTime, endTime), 
            "Car should be compatible with REGULAR slot");
        assertTrue(largeSlot.isCompatible(VehicleType.CAR, startTime, endTime), 
            "Car should be compatible with LARGE slot");
        assertFalse(handicappedSlot.isCompatible(VehicleType.CAR, startTime, endTime), 
            "Car should not be compatible with HANDICAPPED slot");
    }

    @Test
    void testIsCompatibleBusWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertFalse(compactSlot.isCompatible(VehicleType.BUS, startTime, endTime), 
            "Bus should not be compatible with COMPACT slot");
        assertFalse(regularSlot.isCompatible(VehicleType.BUS, startTime, endTime), 
            "Bus should not be compatible with REGULAR slot");
        assertTrue(largeSlot.isCompatible(VehicleType.BUS, startTime, endTime), 
            "Bus should be compatible with LARGE slot");
        assertFalse(handicappedSlot.isCompatible(VehicleType.BUS, startTime, endTime), 
            "Bus should not be compatible with HANDICAPPED slot");
    }

    @Test
    void testIsCompatibleBicycleWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertTrue(compactSlot.isCompatible(VehicleType.BICYCLE, startTime, endTime), 
            "Bicycle should be compatible with COMPACT slot");
        assertTrue(regularSlot.isCompatible(VehicleType.BICYCLE, startTime, endTime), 
            "Bicycle should be compatible with REGULAR slot");
        assertTrue(largeSlot.isCompatible(VehicleType.BICYCLE, startTime, endTime), 
            "Bicycle should be compatible with LARGE slot");
        assertTrue(handicappedSlot.isCompatible(VehicleType.BICYCLE, startTime, endTime), 
            "Bicycle should be compatible with HANDICAPPED slot");
    }

    @Test
    void testIsCompatibleMicrocarWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertTrue(compactSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime), 
            "Microcar should be compatible with COMPACT slot");
        assertTrue(regularSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime), 
            "Microcar should be compatible with REGULAR slot");
        assertFalse(largeSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime), 
            "Microcar should not be compatible with LARGE slot - DEFECT: Missing break statement");
        assertFalse(handicappedSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime), 
            "Microcar should not be compatible with HANDICAPPED slot");
    }

    @Test
    void testIsCompatibleTruckWithCompatibleSlots() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot regularSlot = new ParkingSlot("REGULAR", ParkingSlotType.REGULAR);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        assertFalse(compactSlot.isCompatible(VehicleType.TRUCK, startTime, endTime), 
            "Truck should not be compatible with any slot type according to compatibility matrix");
        assertFalse(regularSlot.isCompatible(VehicleType.TRUCK, startTime, endTime), 
            "Truck should not be compatible with any slot type according to compatibility matrix");
        assertFalse(largeSlot.isCompatible(VehicleType.TRUCK, startTime, endTime), 
            "Truck should not be compatible with any slot type according to compatibility matrix");
        assertFalse(handicappedSlot.isCompatible(VehicleType.TRUCK, startTime, endTime), 
            "Truck should not be compatible with any slot type according to compatibility matrix");
    }

    @Test
    void testIsCompatibleWithUnavailableSlot() {
        // Add existing booking
        Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        Booking booking = new Booking(1, vehicle, slot, startTime, endTime, 50.0);
        slot.getBookings().add(booking);
        
        assertFalse(slot.isCompatible(VehicleType.CAR, conflictStart, conflictEnd), 
            "Slot should not be compatible when not available due to existing booking");
    }

    @Test
    void testGetBalance() {
        assertEquals(0.0, slot.getBalance(), 0.01, "Initial balance should be zero");
        
        slot.getWallet().addFunds(100.0);
        assertEquals(100.0, slot.getBalance(), 0.01, "Balance should reflect wallet changes");
    }

    @Test
    void testMultipleOverlappingBookings() {
        Vehicle vehicle1 = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle vehicle2 = new Vehicle(2, VehicleType.CAR, 100.0);
        
        Booking booking1 = new Booking(1, vehicle1, slot, 
            LocalDateTime.of(2023, 10, 15, 9, 0), 
            LocalDateTime.of(2023, 10, 15, 11, 0), 50.0);
        
        Booking booking2 = new Booking(2, vehicle2, slot,
            LocalDateTime.of(2023, 10, 15, 13, 0),
            LocalDateTime.of(2023, 10, 15, 15, 0), 50.0);
        
        slot.getBookings().add(booking1);
        slot.getBookings().add(booking2);
        
        // Should be available between bookings
        assertTrue(slot.isAvailable(
            LocalDateTime.of(2023, 10, 15, 11, 0),
            LocalDateTime.of(2023, 10, 15, 13, 0)), 
            "Slot should be available between non-overlapping bookings");
        
        // Should not be available during existing bookings
        assertFalse(slot.isAvailable(
            LocalDateTime.of(2023, 10, 15, 10, 0),
            LocalDateTime.of(2023, 10, 15, 14, 0)),
            "Slot should not be available when overlapping with multiple bookings");
    }
}
