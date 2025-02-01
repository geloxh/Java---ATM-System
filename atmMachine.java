import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Scanner;

// Account class for storing account details
class Account {
    private int customerNumber;
    private int pinNumber;
    private double checkingBalance = 0;
    private double savingBalance = 0;

    public int getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber = customerNumber;
    }

    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    public double getCheckingBalance() {
        return checkingBalance;
    }

    public double getSavingBalance() {
        return savingBalance;
    }

    public void withdrawFromChecking(double amount) {
        if (amount <= checkingBalance) {
            checkingBalance -= amount;
            System.out.println("Withdrawal successful! New Checking Balance: " + formatCurrency(checkingBalance));
        } else {
            System.out.println("Insufficient funds in Checking Account.");
        }
    }

    public void depositToChecking(double amount) {
        checkingBalance += amount;
        System.out.println("Deposit successful! New Checking Balance: " + formatCurrency(checkingBalance));
    }

    public void withdrawFromSaving(double amount) {
        if (amount <= savingBalance) {
            savingBalance -= amount;
            System.out.println("Withdrawal successful! New Saving Balance: " + formatCurrency(savingBalance));
        } else {
            System.out.println("Insufficient funds in Saving Account.");
        }
    }

    public void depositToSaving(double amount) {
        savingBalance += amount;
        System.out.println("Deposit successful! New Saving Balance: " + formatCurrency(savingBalance));
    }

    private String formatCurrency(double amount) {
        DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
        return moneyFormat.format(amount);
    }
}

// ATM system implementation
public class atmMachine extends Account {

    private final Scanner input = new Scanner(System.in);
    private final HashMap<Integer, Integer> accounts = new HashMap<>();

    public atmMachine() {
        // Adding some accounts for demonstration
        accounts.put(12345, 6789); // customerNumber -> PIN
        accounts.put(98765, 4321);
    }

    public void start() {
        System.out.println("Welcome to the ATM!");
        if (authenticateUser()) { // Authenticate user before showing main menu
            showMainMenu();
        } else {
            System.out.println("Authentication failed. Exiting...");
        }
    }

    private boolean authenticateUser() {
        System.out.print("Enter your Customer Number: ");
        int customerNumber = input.nextInt();

        System.out.print("Enter your PIN: ");
        int pin = input.nextInt();

        // Check if the entered customer number and PIN match any account
        if (accounts.containsKey(customerNumber) && accounts.get(customerNumber) == pin) {
            setCustomerNumber(customerNumber); // Set authenticated customer number
            setPinNumber(pin); // Set authenticated PIN
            return true;
        } else {
            System.out.println("Invalid Customer Number or PIN.");
            return false;
        }
    }

    private void showMainMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Access Checking Account");
            System.out.println("2. Access Saving Account");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            switch (choice) {
                case 1 -> accessChecking(); // Access checking account menu
                case 2 -> accessSaving(); // Access saving account menu
                case 3 -> {
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    exit = true; // Exit the main menu
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void accessChecking() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nChecking Account Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Deposit Funds");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Checking Balance: " + formatCurrency(getCheckingBalance())); // View checking balance
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = input.nextDouble();
                    withdrawFromChecking(amount); // Withdraw from checking account
                }
                case 3 -> {
                    System.out.print("Enter amount to deposit: ");
                    double amount = input.nextDouble();
                    depositToChecking(amount); // Deposit to checking account
                }
                case 4 -> exit = true; // Return to main menu
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void accessSaving() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nSaving Account Menu:");
            System.out.println("1. View Balance");
            System.out.println("2. Withdraw Funds");
            System.out.println("3. Deposit Funds");
            System.out.println("4. Back to Main Menu");
            System.out.print("Choose an option: ");

            int choice = input.nextInt();
            switch (choice) {
                case 1 -> System.out.println("Saving Balance: " + formatCurrency(getSavingBalance())); // View saving balance
                case 2 -> {
                    System.out.print("Enter amount to withdraw: ");
                    double amount = input.nextDouble();
                    withdrawFromSaving(amount); // Withdraw from saving account
                }
                case 3 -> {
                    System.out.print("Enter amount to deposit: ");
                    double amount = input.nextDouble();
                    depositToSaving(amount); // Deposit to saving account
                }
                case 4 -> exit = true; // Return to main menu
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private String formatCurrency(double amount) {
        DecimalFormat moneyFormat = new DecimalFormat("'$'###,##0.00");
        return moneyFormat.format(amount);
    }

    public static void main(String[] args) {
        atmMachine atm = new atmMachine();
        atm.start(); // Start the ATM system
    }
}
