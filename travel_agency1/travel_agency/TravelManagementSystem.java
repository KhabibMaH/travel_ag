import java.util.ArrayList;
import java.util.Scanner;

class TravelPackage {
    private int packageId;
    private String destination;
    private double price;
    private int duration; // in days

    public TravelPackage(int packageId, String destination, double price, int duration) {
        this.packageId = packageId;
        this.destination = destination;
        this.price = price;
        this.duration = duration;
    }

    public int getPackageId() {
        return packageId;
    }

    public String getDestination() {
        return destination;
    }

    public double getPrice() {
        return price;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Package ID: " + packageId + ", Destination: " + destination +
                ", Price: $" + price + ", Duration: " + duration + " days";
    }
}

class Customer {
    private int customerId;
    private String name;
    private String email;

    public Customer(int customerId, String name, String email) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer ID: " + customerId + ", Name: " + name + ", Email: " + email;
    }
}

class Booking {
    private int bookingId;
    private Customer customer;
    private TravelPackage travelPackage;

    public Booking(int bookingId, Customer customer, TravelPackage travelPackage) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.travelPackage = travelPackage;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                "\nCustomer: " + customer.getName() +
                "\nDestination: " + travelPackage.getDestination() +
                "\nPrice: $" + travelPackage.getPrice();
    }
}

public class TravelManagementSystem {
    private static ArrayList<TravelPackage> packages = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static int bookingIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializePackages();
        int choice;

        do {
            System.out.println("\n--- Travel and Tourism Management System ---");
            System.out.println("1. View Travel Packages");
            System.out.println("2. Add Customer");
            System.out.println("3. Make a Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    viewPackages();
                    break;
                case 2:
                    addCustomer(scanner);
                    break;
                case 3:
                    makeBooking(scanner);
                    break;
                case 4:
                    viewBookings();
                    break;
                case 5:
                    System.out.println("Thank you for using the system!");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 5);
    }

    private static void initializePackages() {
        packages.add(new TravelPackage(1, "Paris", 1200.0, 7));
        packages.add(new TravelPackage(2, "New York", 1500.0, 5));
        packages.add(new TravelPackage(3, "Tokyo", 2000.0, 10));
        packages.add(new TravelPackage(4, "Maldives", 2500.0, 6));
    }

    private static void viewPackages() {
        System.out.println("\nAvailable Travel Packages:");
        for (TravelPackage travelPackage : packages) {
            System.out.println(travelPackage);
        }
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("\nEnter Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Customer Email: ");
        String email = scanner.nextLine();

        customers.add(new Customer(customerId, name, email));
        System.out.println("Customer added successfully!");
    }

    private static void makeBooking(Scanner scanner) {
        if (customers.isEmpty()) {
            System.out.println("No customers available! Please add a customer first.");
            return;
        }

        if (packages.isEmpty()) {
            System.out.println("No packages available!");
            return;
        }

        System.out.println("\nSelect a Customer:");
        for (Customer customer : customers) {
            System.out.println(customer);
        }
        System.out.print("Enter Customer ID: ");
        int customerId = scanner.nextInt();

        Customer selectedCustomer = customers.stream()
                .filter(c -> c.getCustomerId() == customerId)
                .findFirst()
                .orElse(null);

        if (selectedCustomer == null) {
            System.out.println("Invalid Customer ID!");
            return;
        }

        System.out.println("\nSelect a Travel Package:");
        for (TravelPackage travelPackage : packages) {
            System.out.println(travelPackage);
        }
        System.out.print("Enter Package ID: ");
        int packageId = scanner.nextInt();

        TravelPackage selectedPackage = packages.stream()
                .filter(p -> p.getPackageId() == packageId)
                .findFirst()
                .orElse(null);

        if (selectedPackage == null) {
            System.out.println("Invalid Package ID!");
            return;
        }

        bookings.add(new Booking(bookingIdCounter++, selectedCustomer, selectedPackage));
        System.out.println("Booking created successfully!");
    }

    private static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings available!");
            return;
        }

        System.out.println("\nAll Bookings:");
        for (Booking booking : bookings) {
            System.out.println(booking + "\n");
        }
    }
}
