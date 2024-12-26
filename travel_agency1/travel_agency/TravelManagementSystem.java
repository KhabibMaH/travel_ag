import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.Serializable;



class TravelPackage implements Serializable {
    private static final long serialVersionUID = 1L;
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

class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

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

class Booking implements Serializable {
    private static final long serialVersionUID = 1L;

    public int bookingId;
    private Customer customer;
    private TravelPackage travelPackage;
    private boolean isPaid;
    private double amountPaid;

    public Booking(int bookingId, Customer customer, TravelPackage travelPackage) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.travelPackage = travelPackage;
        this.isPaid = false;
        this.amountPaid = 0.0;
    }
    public TravelPackage getTravelPackage() {
        return travelPackage;
    }

    public void addPayment(double amount) {
        amountPaid += amount;
        if (amountPaid >= travelPackage.getPrice()) {
            isPaid = true;
        }
    }

    public boolean isPaid() {
        return isPaid;
    }

    public double getAmountPaid() {
        return amountPaid;
    }


    @Override
    public String toString() {
        return "Booking ID: " + bookingId +
                "\nCustomer: " + customer.getName() +
                "\nDestination: " + travelPackage.getDestination() +
                "\nPrice: $" + travelPackage.getPrice();
    }
}
class Payment implements Serializable {
    private static final long serialVersionUID = 1L;

    public int paymentId;
    private int bookingId;
    private double amount;

    public Payment(int paymentId, int bookingId, double amount) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Payment ID: " + paymentId + ", Booking ID: " + bookingId + ", Amount: $" + amount;
    }
}


public class TravelManagementSystem implements Serializable {
    private static final long serialVersionUID = 1L;

    private static ArrayList<TravelPackage> packages = new ArrayList<>();
    private static ArrayList<Customer> customers = new ArrayList<>();
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static int bookingIdCounter = 1;

    private static ArrayList<Payment> payments = new ArrayList<>();
    private static int paymentIdCounter = 1;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        initializePackages();
        int choice;

        packages = loadFromFile("packages.txt");
        if (packages.isEmpty()) {
            initializePackages();
            saveToFile("packages.txt", packages); // Αποθήκευση αρχικών πακέτων
        }

        customers = loadFromFile("customers.txt");
        bookings = loadFromFile("bookings.txt");
        payments = loadFromFile("payments.txt");


        do {
            System.out.println("\n--- Travel and Tourism Management System ---");
            System.out.println("1. View Travel Packages");
            System.out.println("2. Add Customer");
            System.out.println("3. Make a Booking");
            System.out.println("4. View Bookings");
            System.out.println("5. Exit");
            System.out.println("6. Add Payment");
            System.out.println("7. View Payments");
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
                case 6:
                    addPayment(scanner);
                    break;
                case 7:
                    viewPayments(scanner);
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
        saveToFile("customers.txt", customers);
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
        saveToFile("bookings.txt", bookings);
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
    private static void addPayment(Scanner scanner) {
        System.out.print("\nEnter Booking ID: ");
        int bookingId = scanner.nextInt();

        Booking selectedBooking = bookings.stream()
                .filter(b -> b.bookingId == bookingId)
                .findFirst()
                .orElse(null);

        if (selectedBooking == null) {
            System.out.println("Invalid Booking ID!");
            return;
        }

        System.out.print("Enter Payment Amount: $");
        double amount = scanner.nextDouble();

        payments.add(new Payment(paymentIdCounter++, bookingId, amount));
        selectedBooking.addPayment(amount);
        saveToFile("payments.txt", payments);
        saveToFile("bookings.txt", bookings);
        System.out.println("Payment recorded successfully!");
        if (selectedBooking.isPaid()) {
            System.out.println("Booking is now fully paid!");
        } else {
            System.out.println("Remaining balance: $" +
                    (selectedBooking.getTravelPackage().getPrice() - selectedBooking.getAmountPaid()));
        }
    }
    private static void viewPayments(Scanner scanner) {
        System.out.print("\nEnter Booking ID to view payments: ");
        int bookingId = scanner.nextInt();

        System.out.println("\nPayments for Booking ID " + bookingId + ":");
        boolean hasPayments = false;
        for (Payment payment : payments) {
            if (payment.getBookingId() == bookingId) {
                System.out.println(payment);
                hasPayments = true;
            }
        }

        if (!hasPayments) {
            System.out.println("No payments found for this booking.");
        }
    }
    private static <T> void saveToFile(String filename, ArrayList<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (IOException e) {
            System.out.println("Error saving data to " + filename);
        }
    }
    private static <T> ArrayList<T> loadFromFile(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("File " + filename + " not found. Creating a new one.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data from " + filename + ". Starting with empty data.");
        }
        return new ArrayList<>();
    }




}