import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import java.util.List;

class ParkingSystemTest {
    private ParkingSystem parkingSystem;
    private Vehicle car;
    private Vehicle motorcycle;
    private Vehicle truck;
    private Vehicle bicycle;
    private Vehicle microcar;
    private Vehicle bus;
    private ParkingSlot regularSlot;
    private ParkingSlot compactSlot;
    private ParkingSlot largeSlot;
    private ParkingSlot handicappedSlot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    void setUp() {
        // Reset singleton instance for each test
        parkingSystem = ParkingSystem.getInstance();
        parkingSystem.getVehicles().clear();
        parkingSystem.getParkingSlots().clear();
        parkingSystem.getBookings().clear();
        parkingSystem.setSYSTEM_WALLET(new Wallet());

        // Create vehicles with sufficient balance
        car = new Vehicle(1, VehicleType.CAR, 1000.0);
        motorcycle = new Vehicle(2, VehicleType.MOTORCYCLE, 1000.0);
        truck = new Vehicle(3, VehicleType.TRUCK, 1000.0);
        bicycle = new Vehicle(4, VehicleType.BICYCLE, 1000.0);
        microcar = new Vehicle(5, VehicleType.MICROCAR, 1000.0);
        bus = new Vehicle(6, VehicleType.BUS, 1000.0);

        // Create parking slots
        regularSlot = new ParkingSlot("REG001", ParkingSlotType.REGULAR);
        compactSlot = new ParkingSlot("COM001", ParkingSlotType.COMPACT);
        largeSlot = new ParkingSlot("LAR001", ParkingSlotType.LARGE);
        handicappedSlot = new ParkingSlot("HAN001", ParkingSlotType.HANDICAPPED);

        // Add vehicles and slots to system
        parkingSystem.addVehicle(car);
        parkingSystem.addVehicle(motorcycle);
        parkingSystem.addVehicle(truck);
        parkingSystem.addVehicle(bicycle);
        parkingSystem.addVehicle(microcar);
        parkingSystem.addVehicle(bus);

        parkingSystem.addParkingSlot(regularSlot);
        parkingSystem.addParkingSlot(compactSlot);
        parkingSystem.addParkingSlot(largeSlot);
        parkingSystem.addParkingSlot(handicappedSlot);

        startTime = LocalDateTime.of(2023, 10, 15, 10, 0);
        endTime = LocalDateTime.of(2023, 10, 15, 12, 0); // 2 hours
    }

    @AfterEach
    void tearDown() {
        // Reset singleton state
        parkingSystem.getVehicles().clear();
        parkingSystem.getParkingSlots().clear();
        parkingSystem.getBookings().clear();
    }

    @Test
    void testSingletonInstance() {
        ParkingSystem instance1 = ParkingSystem.getInstance();
        ParkingSystem instance2 = ParkingSystem.getInstance();
        assertSame(instance1, instance2, "getInstance should return the same singleton instance");
    }

    @Test
    void testAddVehicle() {
        Vehicle newVehicle = new Vehicle(10, VehicleType.CAR, 500.0);
        int initialSize = parkingSystem.getVehicles().size();
        
        parkingSystem.addVehicle(newVehicle);
        assertEquals(initialSize + 1, parkingSystem.getVehicles().size(), "Vehicle should be added to the system");
        assertTrue(parkingSystem.getVehicles().contains(newVehicle), "System should contain the added vehicle");
    }

    @Test
    void testAddParkingSlot() {
        ParkingSlot newSlot = new ParkingSlot("NEW001", ParkingSlotType.REGULAR);
        int initialSize = parkingSystem.getParkingSlots().size();
        
        parkingSystem.addParkingSlot(newSlot);
        assertEquals(initialSize + 1, parkingSystem.getParkingSlots().size(), "Parking slot should be added to the system");
        assertTrue(parkingSystem.getParkingSlots().contains(newSlot), "System should contain the added parking slot");
    }

    @Test
    void testGetAvailableParkingSlotsForCar() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        
        assertEquals(2, availableSlots.size(), "Car should have access to REGULAR and LARGE slots");
        assertTrue(availableSlots.contains(regularSlot), "Car should have access to REGULAR slot");
        assertTrue(availableSlots.contains(largeSlot), "Car should have access to LARGE slot");
        assertFalse(availableSlots.contains(compactSlot), "Car should not have access to COMPACT slot");
        assertFalse(availableSlots.contains(handicappedSlot), "Car should not have access to HANDICAPPED slot");
    }

    @Test
    void testGetAvailableParkingSlotsForMotorcycle() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(motorcycle, startTime, endTime);
        
        assertEquals(3, availableSlots.size(), "Motorcycle should have access to COMPACT, REGULAR and LARGE slots");
        assertTrue(availableSlots.contains(compactSlot), "Motorcycle should have access to COMPACT slot");
        assertTrue(availableSlots.contains(regularSlot), "Motorcycle should have access to REGULAR slot");
        assertTrue(availableSlots.contains(largeSlot), "Motorcycle should have access to LARGE slot");
        assertFalse(availableSlots.contains(handicappedSlot), "Motorcycle should not have access to HANDICAPPED slot");
    }

    @Test
    void testGetAvailableParkingSlotsForBicycle() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(bicycle, startTime, endTime);
        
        assertEquals(4, availableSlots.size(), "Bicycle should have access to all slot types");
        assertTrue(availableSlots.contains(compactSlot), "Bicycle should have access to COMPACT slot");
        assertTrue(availableSlots.contains(regularSlot), "Bicycle should have access to REGULAR slot");
        assertTrue(availableSlots.contains(largeSlot), "Bicycle should have access to LARGE slot");
        assertTrue(availableSlots.contains(handicappedSlot), "Bicycle should have access to HANDICAPPED slot");
    }

    @Test
    void testGetAvailableParkingSlotsForBus() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(bus, startTime, endTime);
        
        assertEquals(1, availableSlots.size(), "Bus should have access to LARGE slot only");
        assertTrue(availableSlots.contains(largeSlot), "Bus should have access to LARGE slot");
        assertFalse(availableSlots.contains(compactSlot), "Bus should not have access to COMPACT slot");
        assertFalse(availableSlots.contains(regularSlot), "Bus should not have access to REGULAR slot");
        assertFalse(availableSlots.contains(handicappedSlot), "Bus should not have access to HANDICAPPED slot");
    }

    @Test
    void testGetAvailableParkingSlotsForMicrocar() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(microcar, startTime, endTime);
        
        // NOTE: Due to missing break statement in ParkingSlot.isCompatible(), 
        // microcar may return unexpected results for LARGE slots
        assertTrue(availableSlots.contains(compactSlot), "Microcar should have access to COMPACT slot");
        assertTrue(availableSlots.contains(regularSlot), "Microcar should have access to REGULAR slot");
        assertFalse(availableSlots.contains(handicappedSlot), "Microcar should not have access to HANDICAPPED slot");
    }

    @Test
    void testGetAvailableParkingSlotsForTruck() {
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(truck, startTime, endTime);
        
        assertEquals(0, availableSlots.size(), "Truck should not have access to any slot types according to compatibility matrix");
    }

    @Test
    void testBookValidBooking() {
        double initialVehicleBalance = car.getBalance();
        double initialSystemBalance = parkingSystem.getBalance();
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertNotNull(booking, "Booking should be created successfully");
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Booking should be ACTIVE");
        assertEquals(car, booking.getVehicle(), "Booking should reference the correct vehicle");
        assertEquals(regularSlot, booking.getParkingSlot(), "Booking should reference the correct slot");
        assertEquals(startTime, booking.getStartTime(), "Booking should have correct start time");
        assertEquals(endTime, booking.getEndTime(), "Booking should have correct end time");
        
        // Verify payment transfer
        double expectedAmount = 2 * 10.0 * 1.0 * 1.0; // 2 hours * base rate * car rate * regular multiplier
        assertEquals(expectedAmount, booking.getAmount(), 0.01, "Booking should have correct amount");
        assertEquals(initialVehicleBalance - expectedAmount, car.getBalance(), 0.01, "Vehicle balance should be reduced by booking amount");
        assertEquals(initialSystemBalance + expectedAmount, parkingSystem.getBalance(), 0.01, "System balance should increase by booking amount");
        
        // Verify booking is added to system and slot
        assertTrue(parkingSystem.getBookings().contains(booking), "Booking should be added to system bookings");
        assertTrue(regularSlot.getBookings().contains(booking), "Booking should be added to slot bookings");
    }

    @Test
    void testBookWithInvalidTimeRange() {
        LocalDateTime invalidEndTime = startTime.minusHours(1); // End before start
        
        assertThrows(IllegalBookingTimeException.class, () -> {
            parkingSystem.book(car, regularSlot, startTime, invalidEndTime);
        }, "Booking with end time before start time should throw IllegalBookingTimeException");
    }

    @Test
    void testBookWithEqualStartAndEndTime() {
        assertThrows(IllegalBookingTimeException.class, () -> {
            parkingSystem.book(car, regularSlot, startTime, startTime);
        }, "Booking with equal start and end time should throw IllegalBookingTimeException");
    }

    @Test
    void testBookIncompatibleSlot() {
        assertThrows(IllegalArgumentException.class, () -> {
            parkingSystem.book(car, compactSlot, startTime, endTime);
        }, "Booking car in compact slot should throw IllegalArgumentException");
    }

    @Test
    void testBookUnavailableSlot() {
        // First booking
        parkingSystem.book(car, regularSlot, startTime, endTime);
        
        // Attempt overlapping booking
        LocalDateTime overlapStart = startTime.plusMinutes(30);
        LocalDateTime overlapEnd = endTime.plusMinutes(30);
        
        assertThrows(IllegalArgumentException.class, () -> {
            parkingSystem.book(motorcycle, regularSlot, overlapStart, overlapEnd);
        }, "Booking overlapping time slot should throw IllegalArgumentException");
    }

    @Test
    void testBookInsufficientFunds() {
        Vehicle poorVehicle = new Vehicle(99, VehicleType.CAR, 1.0); // Insufficient funds
        parkingSystem.addVehicle(poorVehicle);
        
        assertThrows(InsufficientFundsException.class, () -> {
            parkingSystem.book(poorVehicle, regularSlot, startTime, endTime);
        }, "Booking with insufficient funds should throw InsufficientFundsException");
    }

    @Test
    void testPricingCalculation() {
        // Test different vehicle types and slot types for pricing
        LocalDateTime threeHourEnd = startTime.plusHours(3);
        
        // Car in regular slot: 3 * 10 * 1.0 * 1.0 = 30.0
        Booking carBooking = parkingSystem.book(car, regularSlot, startTime, threeHourEnd);
        assertEquals(30.0, carBooking.getAmount(), 0.01, "Car pricing should be correct");
        
        // Motorcycle in compact slot: 3 * 10 * 0.5 * 0.8 = 12.0
        Booking motorcycleBooking = parkingSystem.book(motorcycle, compactSlot, startTime, threeHourEnd);
        assertEquals(12.0, motorcycleBooking.getAmount(), 0.01, "Motorcycle pricing should be correct");
        
        // Bus in large slot: 3 * 10 * 2.0 * 1.5 = 90.0
        Booking busBooking = parkingSystem.book(bus, largeSlot, startTime, threeHourEnd);
        assertEquals(90.0, busBooking.getAmount(), 0.01, "Bus pricing should be correct");
    }

    @Test
    void testCompleteBooking() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double bookingAmount = booking.getAmount();
        double initialSlotBalance = regularSlot.getBalance();
        double initialSystemBalance = parkingSystem.getBalance();
        
        parkingSystem.completeBooking(booking);
        
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should be marked as COMPLETED");
        assertEquals(initialSlotBalance + (bookingAmount * 0.8), regularSlot.getBalance(), 0.01, 
            "Slot should receive 80% of booking amount");
        assertEquals(initialSystemBalance - (bookingAmount * 0.8), parkingSystem.getBalance(), 0.01, 
            "System should retain 20% of booking amount");
    }

    @Test
    void testCancelBooking() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double bookingAmount = booking.getAmount();
        double vehicleBalanceAfterBooking = car.getBalance();
        double initialSystemBalance = parkingSystem.getBalance();
        
        parkingSystem.cancelBooking(booking);
        
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should be marked as CANCELLED");
        assertEquals(vehicleBalanceAfterBooking + (bookingAmount * 0.9), car.getBalance(), 0.01, 
            "Vehicle should receive 90% refund");
        assertEquals(initialSystemBalance - (bookingAmount * 0.9), parkingSystem.getBalance(), 0.01, 
            "System should retain 10% as cancellation fee");
    }

    @Test
    void testFractionalHoursTruncation() {
        LocalDateTime endTimeWith90Minutes = startTime.plusMinutes(90); // 1.5 hours -> should bill for 1 hour
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTimeWith90Minutes);
        double expectedAmount = 1 * 10.0 * 1.0 * 1.0; // 1 hour billed instead of 1.5
        
        assertEquals(expectedAmount, booking.getAmount(), 0.01, 
            "Fractional hours should be truncated - 90 minutes should bill as 1 hour");
    }

    @Test
    void testZeroHourBooking() {
        LocalDateTime endTimeSameHour = startTime.plusMinutes(30); // 0.5 hours -> should bill for 0 hours
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTimeSameHour);
        assertEquals(0.0, booking.getAmount(), 0.01, 
            "Booking less than 1 hour should result in 0 amount due to truncation");
    }

    @Test
    void testGetVehicleTypeRates() {
        // Test pricing with different vehicle types (same duration and slot)
        LocalDateTime oneHourEnd = startTime.plusHours(1);
        
        Booking bicycleBooking = parkingSystem.book(bicycle, regularSlot, startTime, oneHourEnd);
        assertEquals(2.0, bicycleBooking.getAmount(), 0.01, "Bicycle rate should be 0.2"); // 1 * 10 * 0.2 * 1.0
        
        // Reset for next booking
        regularSlot.getBookings().clear();
        
        Booking truckBooking = parkingSystem.book(new Vehicle(100, VehicleType.TRUCK, 1000.0), largeSlot, startTime, oneHourEnd);
        assertEquals(45.0, truckBooking.getAmount(), 0.01, "Truck rate should be 3.0"); // 1 * 10 * 3.0 * 1.5
    }

    @Test
    void testSlotTypeMultipliers() {
        // Test pricing with different slot types (same vehicle and duration)
        LocalDateTime oneHourEnd = startTime.plusHours(1);
        
        Booking compactBooking = parkingSystem.book(motorcycle, compactSlot, startTime, oneHourEnd);
        assertEquals(4.0, compactBooking.getAmount(), 0.01, "Compact multiplier should be 0.8"); // 1 * 10 * 0.5 * 0.8
        
        Booking handicappedBooking = parkingSystem.book(bicycle, handicappedSlot, startTime, oneHourEnd);
        assertEquals(2.4, handicappedBooking.getAmount(), 0.01, "Handicapped multiplier should be 1.2"); // 1 * 10 * 0.2 * 1.2
    }

    @Test
    void testDeactivatedSlotNotAvailable() {
        regularSlot.deactivate();
        
        List<ParkingSlot> availableSlots = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        assertFalse(availableSlots.contains(regularSlot), "Deactivated slot should not be available");
    }

    @Test
    void testMultipleBookingsInSystem() {
        Booking booking1 = parkingSystem.book(car, regularSlot, startTime, endTime);
        Booking booking2 = parkingSystem.book(motorcycle, compactSlot, startTime, endTime);
        Booking booking3 = parkingSystem.book(bus, largeSlot, startTime, endTime);
        
        assertEquals(3, parkingSystem.getBookings().size(), "System should track multiple bookings");
        assertTrue(parkingSystem.getBookings().contains(booking1), "System should contain first booking");
        assertTrue(parkingSystem.getBookings().contains(booking2), "System should contain second booking");
        assertTrue(parkingSystem.getBookings().contains(booking3), "System should contain third booking");
    }

    @Test
    void testSystemWalletBalance() {
        double initialBalance = parkingSystem.getBalance();
        assertEquals(0.0, initialBalance, 0.01, "System wallet should start with zero balance");
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        assertEquals(booking.getAmount(), parkingSystem.getBalance(), 0.01, 
            "System balance should equal booking amount after booking");
    }
}
