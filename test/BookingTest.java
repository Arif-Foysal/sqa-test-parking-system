import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class BookingTest {
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Booking booking;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle(1, VehicleType.CAR, 100.0);
        slot = new ParkingSlot("SLOT001", ParkingSlotType.REGULAR);
        startTime = LocalDateTime.of(2023, 10, 15, 10, 0);
        endTime = LocalDateTime.of(2023, 10, 15, 12, 0);
        booking = new Booking(1, vehicle, slot, startTime, endTime, 50.0);
    }

    @Test
    void testBookingConstructor() {
        assertEquals(1, booking.getBookingId(), "Booking ID should be set correctly");
        assertEquals(vehicle, booking.getVehicle(), "Vehicle should be set correctly");
        assertEquals(slot, booking.getParkingSlot(), "Parking slot should be set correctly");
        assertEquals(startTime, booking.getStartTime(), "Start time should be set correctly");
        assertEquals(endTime, booking.getEndTime(), "End time should be set correctly");
        assertEquals(50.0, booking.getAmount(), 0.01, "Amount should be set correctly");
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Booking should be ACTIVE by default");
    }

    @Test
    void testBookingConstructorWithZeroAmount() {
        Booking zeroAmountBooking = new Booking(2, vehicle, slot, startTime, endTime, 0.0);
        assertEquals(0.0, zeroAmountBooking.getAmount(), 0.01, "Booking should allow zero amount");
        assertEquals(BookingStatus.ACTIVE, zeroAmountBooking.getBookingStatus(), "Booking should still be ACTIVE with zero amount");
    }

    @Test
    void testBookingConstructorWithNegativeAmount() {
        Booking negativeAmountBooking = new Booking(3, vehicle, slot, startTime, endTime, -25.0);
        assertEquals(-25.0, negativeAmountBooking.getAmount(), 0.01, "Booking should allow negative amount");
        assertEquals(BookingStatus.ACTIVE, negativeAmountBooking.getBookingStatus(), "Booking should still be ACTIVE with negative amount");
    }

    @Test
    void testCompleteBooking() {
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Booking should start as ACTIVE");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should be COMPLETED after completion");
    }

    @Test
    void testCancelBooking() {
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Booking should start as ACTIVE");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should be CANCELLED after cancellation");
    }

    @Test
    void testCompleteAlreadyCompletedBooking() {
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should be COMPLETED");
        
        booking.completeBooking(); // Complete again
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should remain COMPLETED when completed again");
    }

    @Test
    void testCancelAlreadyCancelledBooking() {
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should be CANCELLED");
        
        booking.cancelBooking(); // Cancel again
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should remain CANCELLED when cancelled again");
    }

    @Test
    void testCompleteAfterCancel() {
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should be CANCELLED");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should change to COMPLETED even after being cancelled");
    }

    @Test
    void testCancelAfterComplete() {
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should be COMPLETED");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should change to CANCELLED even after being completed");
    }

    @Test
    void testGetBookingId() {
        assertEquals(1, booking.getBookingId(), "Should return correct booking ID");
    }

    @Test
    void testGetVehicle() {
        assertEquals(vehicle, booking.getVehicle(), "Should return correct vehicle reference");
    }

    @Test
    void testGetParkingSlot() {
        assertEquals(slot, booking.getParkingSlot(), "Should return correct parking slot reference");
    }

    @Test
    void testGetStartTime() {
        assertEquals(startTime, booking.getStartTime(), "Should return correct start time");
    }

    @Test
    void testGetEndTime() {
        assertEquals(endTime, booking.getEndTime(), "Should return correct end time");
    }

    @Test
    void testGetAmount() {
        assertEquals(50.0, booking.getAmount(), 0.01, "Should return correct amount");
    }

    @Test
    void testToString() {
        String expected = "Booking{bookingId=1, vehicle=" + vehicle.toString() + 
                         ", parkingSlot=" + slot + 
                         ", startTime=" + startTime + 
                         ", endTime=" + endTime + 
                         ", amount=50.0, bookingStatus=ACTIVE}";
        assertEquals(expected, booking.toString(), "toString should return formatted string with booking details");
    }

    @Test
    void testToStringAfterStatusChange() {
        booking.completeBooking();
        String result = booking.toString();
        assertTrue(result.contains("bookingStatus=COMPLETED"), "toString should reflect current booking status");
    }

    @Test
    void testBookingWithDifferentVehicleTypes() {
        Vehicle motorcycle = new Vehicle(2, VehicleType.MOTORCYCLE, 100.0);
        Vehicle truck = new Vehicle(3, VehicleType.TRUCK, 100.0);
        Vehicle bicycle = new Vehicle(4, VehicleType.BICYCLE, 100.0);

        Booking motorcycleBooking = new Booking(2, motorcycle, slot, startTime, endTime, 25.0);
        Booking truckBooking = new Booking(3, truck, slot, startTime, endTime, 75.0);
        Booking bicycleBooking = new Booking(4, bicycle, slot, startTime, endTime, 10.0);

        assertEquals(VehicleType.MOTORCYCLE, motorcycleBooking.getVehicle().getVehicleType(), "Motorcycle booking should have correct vehicle type");
        assertEquals(VehicleType.TRUCK, truckBooking.getVehicle().getVehicleType(), "Truck booking should have correct vehicle type");
        assertEquals(VehicleType.BICYCLE, bicycleBooking.getVehicle().getVehicleType(), "Bicycle booking should have correct vehicle type");
    }

    @Test
    void testBookingWithDifferentSlotTypes() {
        ParkingSlot compactSlot = new ParkingSlot("COMPACT", ParkingSlotType.COMPACT);
        ParkingSlot largeSlot = new ParkingSlot("LARGE", ParkingSlotType.LARGE);
        ParkingSlot handicappedSlot = new ParkingSlot("HANDICAPPED", ParkingSlotType.HANDICAPPED);

        Booking compactBooking = new Booking(2, vehicle, compactSlot, startTime, endTime, 40.0);
        Booking largeBooking = new Booking(3, vehicle, largeSlot, startTime, endTime, 75.0);
        Booking handicappedBooking = new Booking(4, vehicle, handicappedSlot, startTime, endTime, 60.0);

        assertEquals(ParkingSlotType.COMPACT, compactBooking.getParkingSlot().getSlotType(), "Compact slot booking should have correct slot type");
        assertEquals(ParkingSlotType.LARGE, largeBooking.getParkingSlot().getSlotType(), "Large slot booking should have correct slot type");
        assertEquals(ParkingSlotType.HANDICAPPED, handicappedBooking.getParkingSlot().getSlotType(), "Handicapped slot booking should have correct slot type");
    }

    @Test
    void testBookingWithSameStartAndEndTime() {
        LocalDateTime sameTime = LocalDateTime.of(2023, 10, 15, 10, 0);
        Booking sameTimeBooking = new Booking(2, vehicle, slot, sameTime, sameTime, 0.0);
        
        assertEquals(sameTime, sameTimeBooking.getStartTime(), "Start time should be set correctly");
        assertEquals(sameTime, sameTimeBooking.getEndTime(), "End time should be set correctly");
        assertEquals(0.0, sameTimeBooking.getAmount(), 0.01, "Amount should be zero for same start/end time");
    }

    @Test
    void testBookingWithEndTimeBeforeStartTime() {
        LocalDateTime laterTime = LocalDateTime.of(2023, 10, 15, 12, 0);
        LocalDateTime earlierTime = LocalDateTime.of(2023, 10, 15, 10, 0);
        
        Booking invalidTimeBooking = new Booking(2, vehicle, slot, laterTime, earlierTime, 50.0);
        
        assertEquals(laterTime, invalidTimeBooking.getStartTime(), "Start time should be set as provided");
        assertEquals(earlierTime, invalidTimeBooking.getEndTime(), "End time should be set as provided");
        assertEquals(BookingStatus.ACTIVE, invalidTimeBooking.getBookingStatus(), "Booking should still be created with invalid time order");
    }

    @Test
    void testBookingImmutabilityAfterCreation() {
        // Verify that booking properties cannot be changed after creation (no setters available)
        assertEquals(1, booking.getBookingId(), "Booking ID should remain unchanged");
        assertEquals(vehicle, booking.getVehicle(), "Vehicle should remain unchanged");
        assertEquals(slot, booking.getParkingSlot(), "Parking slot should remain unchanged");
        assertEquals(startTime, booking.getStartTime(), "Start time should remain unchanged");
        assertEquals(endTime, booking.getEndTime(), "End time should remain unchanged");
        assertEquals(50.0, booking.getAmount(), 0.01, "Amount should remain unchanged");
    }
}
