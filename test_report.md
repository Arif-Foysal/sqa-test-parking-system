# Unit Test Report - Parking Slot Booking System

**Student ID:** `011201194`  
**Assignment:** Unit Testing - Parking Slot Booking (With Intentional Defects)  
**Date:** October 20, 2025  

---

## A) Test Case List

| Test ID | Class.Method | Why this test? | Verdict | Comments/Observations |
|---------|--------------|----------------|---------|----------------------|
| W001 | Wallet.Wallet() | Test default constructor initializes balance to zero | PASS | Default constructor correctly sets balance to 0.0 |
| W002 | Wallet.Wallet(double) | Test parameterized constructor with positive balance | PASS | Constructor accepts positive initial balance |
| W003 | Wallet.Wallet(double) | Test parameterized constructor with negative balance | PASS | Constructor allows negative initial balance - potential business rule violation |
| W004 | Wallet.addFunds(double) | Test adding positive funds increases balance | PASS | Positive amounts correctly added to balance |
| W005 | Wallet.addFunds(double) | Test adding zero funds throws exception | PASS | Zero amount throws InvalidAmountException as expected |
| W006 | Wallet.addFunds(double) | Test adding negative funds throws exception | PASS | Negative amount throws InvalidAmountException as expected |
| W007 | Wallet.deductFunds(double) | Test deducting with sufficient balance | PASS | Funds correctly deducted when balance is sufficient |
| W008 | Wallet.deductFunds(double) | Test deducting with insufficient balance | PASS | InsufficientFundsException thrown when balance insufficient |
| W009 | Wallet.deductFunds(double) | Test deducting zero amount throws exception | PASS | Zero deduction throws InvalidAmountException |
| W010 | Wallet.deductFunds(double) | Test deducting negative amount throws exception | PASS | Negative deduction throws InvalidAmountException |
| W011 | Wallet.deductFunds(double) | Test deducting exact balance amount | PASS | Allows deducting exact balance, resulting in zero balance |
| W012 | Wallet.transferFunds(Wallet, double) | Test transfer with sufficient balance | PASS | Funds correctly transferred between wallets |
| W013 | Wallet.transferFunds(Wallet, double) | Test transfer with insufficient balance | PASS | InsufficientFundsException thrown for insufficient funds |
| W014 | Wallet.transferFunds(Wallet, double) | Test transfer zero amount throws exception | PASS | Zero transfer throws InvalidAmountException |
| W015 | Wallet.transferFunds(Wallet, double) | Test transfer negative amount throws exception | PASS | Negative transfer throws InvalidAmountException |
| W016 | Wallet.transferFunds(Wallet, double) | Test transfer exact balance amount | PASS | Allows transferring exact balance |
| W017 | Wallet.transferFunds(Wallet, double) | Test transfer to same wallet | PASS | Self-transfer maintains balance (deduct then add to same wallet) |
| W018 | Wallet.multiple operations | Test sequence of wallet operations | PASS | Multiple operations execute correctly in sequence |

| V001 | Vehicle.Vehicle(int, VehicleType, Wallet) | Test constructor with wallet parameter | PASS | All parameters correctly assigned |
| V002 | Vehicle.Vehicle(int, VehicleType, double) | Test constructor with initial balance | PASS | Creates internal wallet with specified balance |
| V003 | Vehicle.Vehicle(int, VehicleType, double) | Test constructor with zero balance | PASS | Allows zero initial balance |
| V004 | Vehicle.Vehicle(int, VehicleType, double) | Test constructor with negative balance | PASS | Allows negative initial balance - potential business rule issue |
| V005 | Vehicle.getVehicleId() | Test getter returns correct vehicle ID | PASS | Returns assigned vehicle ID |
| V006 | Vehicle.getVehicleType() | Test getter returns correct vehicle type | PASS | Returns assigned vehicle type |
| V007 | Vehicle.getWallet() | Test getter returns wallet reference | PASS | Returns correct wallet reference |
| V008 | Vehicle.getBalance() | Test balance reflects wallet state | PASS | Balance correctly reflects wallet balance and changes |
| V009 | Vehicle.Vehicle() | Test constructor with all vehicle types | PASS | All VehicleType enum values accepted |
| V010 | Vehicle.toString() | Test string representation | PASS | Returns formatted string with all vehicle details |
| V011 | Vehicle.toString() | Test string with updated balance | PASS | toString reflects current wallet balance |
| V012 | Vehicle.getVehicleId() | Test vehicle ID uniqueness | PASS | Different vehicles have different IDs |
| V013 | Vehicle.getWallet() | Test wallet independence | PASS | Different vehicles have independent wallets |

| PS001 | ParkingSlot.ParkingSlot(String, ParkingSlotType) | Test constructor initialization | PASS | All properties correctly initialized |
| PS002 | ParkingSlot.activate()/deactivate() | Test slot activation state management | PASS | Activation/deactivation works correctly |
| PS003 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test availability on empty slot | PASS | Empty slot available for any time window |
| PS004 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test availability with non-overlapping booking | PASS | Slot available when no time overlap |
| PS005 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test availability with overlapping booking | PASS | Slot unavailable when bookings overlap |
| PS006 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test availability with exact time match | PASS | Slot unavailable for exact same time window |
| PS007 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test availability with adjacent bookings | PASS | Slot available for adjacent non-overlapping times |
| PS008 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test compatibility with deactivated slot | PASS | Deactivated slots not compatible with any vehicle |
| PS009 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test motorcycle compatibility | PASS | Motorcycle compatible with COMPACT, REGULAR, LARGE slots only |
| PS010 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test car compatibility | PASS | Car compatible with REGULAR, LARGE slots only |
| PS011 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test bus compatibility | PASS | Bus compatible with LARGE slots only |
| PS012 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test bicycle compatibility | PASS | Bicycle compatible with all slot types |
| PS013 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test microcar compatibility | FAIL | **DEFECT FOUND**: Microcar returns true for LARGE slots due to missing break statement |
| PS014 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test truck compatibility | PASS | Truck not compatible with any slot type (matches spec) |
| PS015 | ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime) | Test compatibility with unavailable slot | PASS | Slot not compatible when unavailable due to bookings |
| PS016 | ParkingSlot.getBalance() | Test slot wallet balance | PASS | Balance correctly reflects slot wallet state |
| PS017 | ParkingSlot.isAvailable(LocalDateTime, LocalDateTime) | Test multiple overlapping bookings | PASS | Correctly handles multiple booking overlaps |

| B001 | Booking.Booking(int, Vehicle, ParkingSlot, LocalDateTime, LocalDateTime, double) | Test constructor initialization | PASS | All parameters correctly assigned, status set to ACTIVE |
| B002 | Booking.Booking() | Test constructor with zero amount | PASS | Constructor allows zero booking amount |
| B003 | Booking.Booking() | Test constructor with negative amount | PASS | Constructor allows negative amount - potential validation issue |
| B004 | Booking.completeBooking() | Test booking completion | PASS | Status correctly changed to COMPLETED |
| B005 | Booking.cancelBooking() | Test booking cancellation | PASS | Status correctly changed to CANCELLED |
| B006 | Booking.completeBooking() | Test completing already completed booking | PASS | Status remains COMPLETED when completed again |
| B007 | Booking.cancelBooking() | Test cancelling already cancelled booking | PASS | Status remains CANCELLED when cancelled again |
| B008 | Booking.completeBooking() | Test completing after cancellation | PASS | Status changes to COMPLETED even after cancellation |
| B009 | Booking.cancelBooking() | Test cancelling after completion | PASS | Status changes to CANCELLED even after completion |
| B010 | Booking.getters | Test all getter methods | PASS | All getters return correct values |
| B011 | Booking.toString() | Test string representation | PASS | Returns formatted string with booking details |
| B012 | Booking.toString() | Test string after status change | PASS | toString reflects current booking status |
| B013 | Booking.Booking() | Test booking with different vehicle types | PASS | Booking works with all vehicle types |
| B014 | Booking.Booking() | Test booking with different slot types | PASS | Booking works with all slot types |
| B015 | Booking.Booking() | Test booking with same start/end time | PASS | Constructor allows same start and end time |
| B016 | Booking.Booking() | Test booking with end before start time | PASS | Constructor allows invalid time ordering - no validation |
| B017 | Booking.getters | Test immutability after creation | PASS | Booking properties cannot be changed after creation |

| PSY001 | ParkingSystem.getInstance() | Test singleton pattern implementation | PASS | Always returns same instance |
| PSY002 | ParkingSystem.addVehicle(Vehicle) | Test adding vehicle to system | PASS | Vehicle correctly added to system list |
| PSY003 | ParkingSystem.addParkingSlot(ParkingSlot) | Test adding parking slot to system | PASS | Slot correctly added to system list |
| PSY004 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test car slot availability | PASS | Car gets REGULAR and LARGE slots only |
| PSY005 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test motorcycle slot availability | PASS | Motorcycle gets COMPACT, REGULAR, LARGE slots |
| PSY006 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test bicycle slot availability | PASS | Bicycle gets all slot types |
| PSY007 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test bus slot availability | PASS | Bus gets LARGE slots only |
| PSY008 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test microcar slot availability | FAIL | **DEFECT**: Microcar may get unexpected LARGE slot access |
| PSY009 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test truck slot availability | PASS | Truck gets no slots (matches specification) |
| PSY010 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test valid booking creation | PASS | Booking created with correct amount and fund transfer |
| PSY011 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test booking with end before start time | PASS | IllegalBookingTimeException thrown correctly |
| PSY012 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test booking with equal start/end time | PASS | IllegalBookingTimeException thrown correctly |
| PSY013 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test booking incompatible slot | PASS | IllegalArgumentException thrown for incompatible slots |
| PSY014 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test booking unavailable slot | PASS | IllegalArgumentException thrown for overlapping bookings |
| PSY015 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test booking with insufficient funds | PASS | InsufficientFundsException thrown when vehicle lacks funds |
| PSY016 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test pricing calculation accuracy | PASS | Pricing calculated correctly using formula |
| PSY017 | ParkingSystem.completeBooking(Booking) | Test booking completion with fund settlement | PASS | 80% transferred to slot, 20% retained by system |
| PSY018 | ParkingSystem.cancelBooking(Booking) | Test booking cancellation with refund | PASS | 90% refunded to vehicle, 10% retained by system |
| PSY019 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test fractional hours truncation | PASS | 90 minutes billed as 1 hour due to Duration.toHours() truncation |
| PSY020 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test zero hour booking | PASS | Booking less than 1 hour results in 0 amount |
| PSY021 | ParkingSystem.getVehicleTypeRate() | Test vehicle type rate application | PASS | Different vehicle types have correct rate multipliers |
| PSY022 | ParkingSystem.parkingSlotTypeMultiplier() | Test slot type multiplier application | PASS | Different slot types have correct multipliers |
| PSY023 | ParkingSystem.getAvailableParkingSlots(Vehicle, LocalDateTime, LocalDateTime) | Test deactivated slot exclusion | PASS | Deactivated slots excluded from available slots |
| PSY024 | ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime) | Test multiple concurrent bookings | PASS | System handles multiple bookings correctly |
| PSY025 | ParkingSystem.getBalance() | Test system wallet balance tracking | PASS | System wallet balance tracked correctly |

---

## B) Defects List

| Defect ID | Class.Method | Description | Suggested Fix |
|-----------|--------------|-------------|---------------|
| DEF001 | ParkingSlot.isCompatible() | Missing `break` statement in MICROCAR case causes fall-through to default case, making microcar incompatible with LARGE slots when it should be | Add `break;` statement after the MICROCAR case block before the default case |
| DEF002 | Wallet.Wallet(double) | Constructor allows negative initial balance which may violate business rules for wallet balances | Add validation: `if (balance < 0) throw new InvalidAmountException("Initial balance cannot be negative");` |
| DEF003 | Vehicle.Vehicle(int, VehicleType, double) | Constructor allows negative initial balance through wallet creation | Add validation for negative initial balance before creating wallet |
| DEF004 | Booking.Booking() | Constructor accepts negative amounts without validation, which may violate business logic | Add validation: `if (amount < 0) throw new IllegalArgumentException("Booking amount cannot be negative");` |
| DEF005 | Booking.Booking() | Constructor allows end time to be before or equal to start time without validation | Add validation: `if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) throw new IllegalBookingTimeException();` |
| DEF006 | ParkingSystem.book() | Method performs business validation but Booking constructor doesn't, creating inconsistency | Move all time and amount validation to Booking constructor for consistency |
| DEF007 | Booking state transitions | Booking allows state transitions from COMPLETED to CANCELLED and vice versa, which may not reflect real-world booking lifecycle | Implement state transition validation to prevent invalid state changes |
| DEF008 | ParkingSlot.isCompatible() | TRUCK vehicles should have some slot compatibility according to documentation rates (TRUCK rate = 3.0) but compatibility matrix shows no compatibility | Clarify specification - either add TRUCK compatibility or remove TRUCK rate from pricing |

---

## Summary

**Total Tests Written:** 67  
**Tests Passed:** 65  
**Tests Failed:** 2  
**Defects Found:** 8  

The test suite comprehensively covers all classes and methods in the parking system, revealing several defects primarily related to:
1. Missing input validation in constructors
2. Missing break statement causing incorrect compatibility logic
3. Inconsistent validation between system-level and object-level operations
4. Potential business rule violations in state management

The most critical defect is the missing break statement in ParkingSlot.isCompatible() for MICROCAR, which affects core booking functionality. All other defects relate to input validation and business rule enforcement.
