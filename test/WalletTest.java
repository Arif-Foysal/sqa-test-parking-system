import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private Wallet wallet;
    private Wallet targetWallet;

    @BeforeEach
    void setUp() {
        wallet = new Wallet();
        targetWallet = new Wallet();
    }

    @Test
    void testWalletDefaultConstructor() {
        assertEquals(0.0, wallet.getBalance(), 0.01, "Default wallet should start with zero balance");
    }

    @Test
    void testWalletConstructorWithBalance() {
        Wallet walletWithBalance = new Wallet(100.0);
        assertEquals(100.0, walletWithBalance.getBalance(), 0.01, "Wallet should initialize with specified balance");
    }

    @Test
    void testWalletConstructorWithNegativeBalance() {
        Wallet walletWithNegativeBalance = new Wallet(-50.0);
        assertEquals(-50.0, walletWithNegativeBalance.getBalance(), 0.01, "Wallet allows negative initial balance");
    }

    @Test
    void testAddFundsPositiveAmount() {
        wallet.addFunds(50.0);
        assertEquals(50.0, wallet.getBalance(), 0.01, "Should add positive amount to balance");
    }

    @Test
    void testAddFundsZeroAmount() {
        assertThrows(InvalidAmountException.class, () -> {
            wallet.addFunds(0.0);
        }, "Adding zero amount should throw InvalidAmountException");
    }

    @Test
    void testAddFundsNegativeAmount() {
        assertThrows(InvalidAmountException.class, () -> {
            wallet.addFunds(-10.0);
        }, "Adding negative amount should throw InvalidAmountException");
    }

    @Test
    void testDeductFundsSufficientBalance() {
        wallet.addFunds(100.0);
        wallet.deductFunds(30.0);
        assertEquals(70.0, wallet.getBalance(), 0.01, "Should deduct amount from sufficient balance");
    }

    @Test
    void testDeductFundsInsufficientBalance() {
        wallet.addFunds(50.0);
        assertThrows(InsufficientFundsException.class, () -> {
            wallet.deductFunds(100.0);
        }, "Deducting more than balance should throw InsufficientFundsException");
    }

    @Test
    void testDeductFundsZeroAmount() {
        wallet.addFunds(50.0);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.deductFunds(0.0);
        }, "Deducting zero amount should throw InvalidAmountException");
    }

    @Test
    void testDeductFundsNegativeAmount() {
        wallet.addFunds(50.0);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.deductFunds(-10.0);
        }, "Deducting negative amount should throw InvalidAmountException");
    }

    @Test
    void testDeductFundsExactBalance() {
        wallet.addFunds(100.0);
        wallet.deductFunds(100.0);
        assertEquals(0.0, wallet.getBalance(), 0.01, "Should allow deducting exact balance");
    }

    @Test
    void testTransferFundsSufficientBalance() {
        wallet.addFunds(100.0);
        wallet.transferFunds(targetWallet, 30.0);
        
        assertEquals(70.0, wallet.getBalance(), 0.01, "Source wallet should have reduced balance");
        assertEquals(30.0, targetWallet.getBalance(), 0.01, "Target wallet should receive funds");
    }

    @Test
    void testTransferFundsInsufficientBalance() {
        wallet.addFunds(50.0);
        assertThrows(InsufficientFundsException.class, () -> {
            wallet.transferFunds(targetWallet, 100.0);
        }, "Transferring more than balance should throw InsufficientFundsException");
    }

    @Test
    void testTransferFundsZeroAmount() {
        wallet.addFunds(50.0);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.transferFunds(targetWallet, 0.0);
        }, "Transferring zero amount should throw InvalidAmountException");
    }

    @Test
    void testTransferFundsNegativeAmount() {
        wallet.addFunds(50.0);
        assertThrows(InvalidAmountException.class, () -> {
            wallet.transferFunds(targetWallet, -10.0);
        }, "Transferring negative amount should throw InvalidAmountException");
    }

    @Test
    void testTransferFundsExactBalance() {
        wallet.addFunds(100.0);
        wallet.transferFunds(targetWallet, 100.0);
        
        assertEquals(0.0, wallet.getBalance(), 0.01, "Source wallet should be empty after transferring exact balance");
        assertEquals(100.0, targetWallet.getBalance(), 0.01, "Target wallet should receive exact amount");
    }

    @Test
    void testTransferFundsToSameWallet() {
        wallet.addFunds(100.0);
        wallet.transferFunds(wallet, 50.0);
        assertEquals(100.0, wallet.getBalance(), 0.01, "Transferring to same wallet should maintain balance");
    }

    @Test
    void testMultipleOperations() {
        wallet.addFunds(100.0);
        wallet.deductFunds(20.0);
        wallet.addFunds(30.0);
        wallet.transferFunds(targetWallet, 40.0);
        
        assertEquals(70.0, wallet.getBalance(), 0.01, "Multiple operations should result in correct balance");
        assertEquals(40.0, targetWallet.getBalance(), 0.01, "Target wallet should have transferred amount");
    }
}
