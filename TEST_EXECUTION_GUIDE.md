# Unit Test Execution Guide

## Setup Instructions

1. **Compilation Requirements:**
   - Java 17 or higher
   - JUnit 5 (Jupiter) testing framework
   - Standard Java compilation tools (javac, java)

2. **Project Structure:**
   ```
   sqa-test-parking-system/
   ├── src/                    # Source code (DO NOT MODIFY)
   │   ├── Booking.java
   │   ├── BookingStatus.java
   │   ├── ParkingSlot.java
   │   ├── ParkingSlotType.java
   │   ├── ParkingSystem.java
   │   ├── Vehicle.java
   │   ├── VehicleType.java
   │   └── Wallet.java
   ├── test/                   # Test files (YOUR WORK)
   │   ├── BookingTest.java
   │   ├── ParkingSlotTest.java
   │   ├── ParkingSystemTest.java
   │   ├── VehicleTest.java
   │   └── WalletTest.java
   └── README.md
   ```

## Test Coverage Summary

### WalletTest.java (18 tests)
- Default and parameterized constructors
- Add funds validation (positive, zero, negative amounts)
- Deduct funds validation (sufficient/insufficient balance)
- Transfer funds between wallets
- Edge cases (exact balance, self-transfer)

### VehicleTest.java (13 tests)
- Constructor variations (with wallet, with balance)
- Getter method validation
- All vehicle type support
- Wallet integration and independence
- String representation

### ParkingSlotTest.java (17 tests)
- Constructor and initialization
- Activation/deactivation functionality
- Availability checking with time overlaps
- Vehicle type compatibility matrix validation
- **Critical defect detection**: Missing break in MICROCAR compatibility

### BookingTest.java (17 tests)
- Constructor validation with various inputs
- Status transition testing (ACTIVE → COMPLETED/CANCELLED)
- State management edge cases
- Immutability verification
- String representation

### ParkingSystemTest.java (25 tests)
- Singleton pattern implementation
- Vehicle and slot management
- Availability filtering by vehicle type
- Booking creation with validation
- Payment processing and settlement
- Pricing calculation with all multipliers
- Error handling (insufficient funds, invalid times, incompatible slots)

## Key Defects Identified

1. **DEF001 (Critical)**: Missing `break` statement in ParkingSlot.isCompatible() MICROCAR case
2. **DEF002-DEF003**: Negative balance acceptance in constructors
3. **DEF004-DEF005**: Missing validation in Booking constructor
4. **DEF006**: Validation inconsistency between system and object levels
5. **DEF007**: Invalid booking state transitions allowed
6. **DEF008**: TRUCK compatibility specification ambiguity

## Execution Notes

- Tests are designed to be independent and can run in any order
- Each test method includes descriptive assertions explaining expected behavior
- Tests capture current system behavior even when defective
- Singleton ParkingSystem state is reset between tests

## Business Rules Verified

✅ **Pricing Formula**: `hours × base × vehicleTypeRate × slotTypeMultiplier`  
✅ **Vehicle Type Rates**: BICYCLE=0.2, MOTORCYCLE=0.5, MICROCAR=1.5, BUS=2.0, TRUCK=3.0, default=1.0  
✅ **Slot Type Multipliers**: COMPACT=0.8, REGULAR=1.0, LARGE=1.5, HANDICAPPED=1.2  
✅ **Compatibility Matrix**: All vehicle-slot combinations per specification  
✅ **Payment Flow**: 100% upfront → 80% to slot on completion → 90% refund on cancellation  
✅ **Time Truncation**: Fractional hours truncated (90min → 1hr billing)  

## Submission Structure

```
<student_id>_unit_test.zip
├── test/
│   ├── BookingTest.java
│   ├── ParkingSlotTest.java
│   ├── ParkingSystemTest.java
│   ├── VehicleTest.java
│   └── WalletTest.java
└── <student_id>_unit_test_report.pdf
```

**Total Test Count**: 67 tests  
**Defects Found**: 8 defects  
**Coverage**: All classes, methods, and business rules tested
