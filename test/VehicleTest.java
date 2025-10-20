import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehicleTest {
    private Vehicle vehicle;
    private Wallet wallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet(100.0);
        vehicle = new Vehicle(1, VehicleType.CAR, wallet);
    }

    @Test
    void testVehicleConstructorWithWallet() {
        assertEquals(1, vehicle.getVehicleId(), "Vehicle ID should be set correctly");
        assertEquals(VehicleType.CAR, vehicle.getVehicleType(), "Vehicle type should be set correctly");
        assertEquals(wallet, vehicle.getWallet(), "Wallet reference should be set correctly");
        assertEquals(100.0, vehicle.getBalance(), 0.01, "Vehicle balance should match wallet balance");
    }

    @Test
    void testVehicleConstructorWithInitialBalance() {
        Vehicle vehicleWithBalance = new Vehicle(2, VehicleType.MOTORCYCLE, 200.0);
        assertEquals(2, vehicleWithBalance.getVehicleId(), "Vehicle ID should be set correctly");
        assertEquals(VehicleType.MOTORCYCLE, vehicleWithBalance.getVehicleType(), "Vehicle type should be set correctly");
        assertEquals(200.0, vehicleWithBalance.getBalance(), 0.01, "Vehicle should have correct initial balance");
        assertNotNull(vehicleWithBalance.getWallet(), "Wallet should be created automatically");
    }

    @Test
    void testVehicleConstructorWithZeroBalance() {
        Vehicle vehicleZeroBalance = new Vehicle(3, VehicleType.BICYCLE, 0.0);
        assertEquals(0.0, vehicleZeroBalance.getBalance(), 0.01, "Vehicle should allow zero initial balance");
    }

    @Test
    void testVehicleConstructorWithNegativeBalance() {
        Vehicle vehicleNegativeBalance = new Vehicle(4, VehicleType.BUS, -50.0);
        assertEquals(-50.0, vehicleNegativeBalance.getBalance(), 0.01, "Vehicle should allow negative initial balance");
    }

    @Test
    void testGetVehicleId() {
        assertEquals(1, vehicle.getVehicleId(), "Should return correct vehicle ID");
    }

    @Test
    void testGetVehicleType() {
        assertEquals(VehicleType.CAR, vehicle.getVehicleType(), "Should return correct vehicle type");
    }

    @Test
    void testGetWallet() {
        assertEquals(wallet, vehicle.getWallet(), "Should return the correct wallet reference");
    }

    @Test
    void testGetBalance() {
        assertEquals(100.0, vehicle.getBalance(), 0.01, "Should return wallet balance");
        
        // Modify wallet balance and verify vehicle reflects the change
        vehicle.getWallet().addFunds(50.0);
        assertEquals(150.0, vehicle.getBalance(), 0.01, "Vehicle balance should reflect wallet changes");
    }

    @Test
    void testVehicleWithAllVehicleTypes() {
        Vehicle car = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle motorcycle = new Vehicle(2, VehicleType.MOTORCYCLE, 100.0);
        Vehicle truck = new Vehicle(3, VehicleType.TRUCK, 100.0);
        Vehicle bicycle = new Vehicle(4, VehicleType.BICYCLE, 100.0);
        Vehicle microcar = new Vehicle(5, VehicleType.MICROCAR, 100.0);
        Vehicle bus = new Vehicle(6, VehicleType.BUS, 100.0);

        assertEquals(VehicleType.CAR, car.getVehicleType(), "CAR type should be set correctly");
        assertEquals(VehicleType.MOTORCYCLE, motorcycle.getVehicleType(), "MOTORCYCLE type should be set correctly");
        assertEquals(VehicleType.TRUCK, truck.getVehicleType(), "TRUCK type should be set correctly");
        assertEquals(VehicleType.BICYCLE, bicycle.getVehicleType(), "BICYCLE type should be set correctly");
        assertEquals(VehicleType.MICROCAR, microcar.getVehicleType(), "MICROCAR type should be set correctly");
        assertEquals(VehicleType.BUS, bus.getVehicleType(), "BUS type should be set correctly");
    }

    @Test
    void testToString() {
        String expected = "Vehicle{vehicleId=1, vehicleType=CAR, walletBalance=100.0}";
        assertEquals(expected, vehicle.toString(), "toString should return formatted string with vehicle details");
    }

    @Test
    void testToStringWithDifferentBalance() {
        vehicle.getWallet().deductFunds(25.0);
        String expected = "Vehicle{vehicleId=1, vehicleType=CAR, walletBalance=75.0}";
        assertEquals(expected, vehicle.toString(), "toString should reflect current wallet balance");
    }

    @Test
    void testVehicleIdUniqueness() {
        Vehicle vehicle1 = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle vehicle2 = new Vehicle(2, VehicleType.CAR, 100.0);
        
        assertNotEquals(vehicle1.getVehicleId(), vehicle2.getVehicleId(), "Different vehicles should have different IDs");
    }

    @Test
    void testWalletIndependence() {
        Vehicle vehicle1 = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle vehicle2 = new Vehicle(2, VehicleType.CAR, 100.0);
        
        vehicle1.getWallet().addFunds(50.0);
        assertEquals(150.0, vehicle1.getBalance(), 0.01, "Vehicle1 balance should increase");
        assertEquals(100.0, vehicle2.getBalance(), 0.01, "Vehicle2 balance should remain unchanged");
    }
}
