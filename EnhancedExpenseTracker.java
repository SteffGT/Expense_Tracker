import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

class Expense {
    String date;
    String category;
    double amount;

    public Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: $" + amount;
    }

    public String toCSV() {
        return date + "," + category + "," + amount;
    }
}

public class EnhancedExpenseTracker {
    private static final ArrayList<Expense> expenses = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "expenses.csv";

    public static void main(String[] args) {
        loadExpenses();
        System.out.println("Welcome to the Enhanced Personal Expense Tracker!");

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Filter Expenses");
            System.out.println("4. Save Expenses");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the buffer

            switch (choice) {
                case 1 -> addExpense();
                case 2 -> viewExpenses();
                case 3 -> filterExpenses();
                case 4 -> saveExpenses();
                case 5 -> {
                    System.out.println("Goodbye!");
                    saveExpenses(); // Ensure data is saved before exiting
                    return;
                }
                default -> System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void addExpense() {
        System.out.print("Enter the date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter the category: ");
        String category = scanner.nextLine();

        System.out.print("Enter the amount: ");
        double amount = scanner.nextDouble();

        expenses.add(new Expense(date, category, amount));
        System.out.println("Expense added successfully!");
    }

    private static void viewExpenses() {
        System.out.println("\nYour Expenses:");
        for (Expense expense : expenses) {
            System.out.println(expense);
        }
    }

    private static void filterExpenses() {
        System.out.println("Filter by:");
        System.out.println("1. Category");
        System.out.println("2. Date Range");
        System.out.print("Choose an option: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Clear the buffer

        switch (choice) {
            case 1 -> {
                System.out.print("Enter category to filter by: ");
                String category = scanner.nextLine();
                System.out.println("Filtered Expenses:");
                for (Expense expense : expenses) {
                    if (expense.category.equalsIgnoreCase(category)) {
                        System.out.println(expense);
                    }
                }
            }
            case 2 -> {
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();
                System.out.println("Filtered Expenses:");
                for (Expense expense : expenses) {
                    if (expense.date.compareTo(startDate) >= 0 && expense.date.compareTo(endDate) <= 0) {
                        System.out.println(expense);
                    }
                }
            }
            default -> System.out.println("Invalid option.");
        }
    }

    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Expense expense : expenses) {
                writer.write(expense.toCSV());
                writer.newLine();
            }
            System.out.println("Expenses saved to file successfully!");
        } catch (IOException e) {
            System.out.println("An error occurred while saving expenses: " + e.getMessage());
        }
    }

    private static void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
            System.out.println("Expenses loaded from file successfully!");
        } catch (FileNotFoundException e) {
            System.out.println("No saved expenses found. Starting fresh!");
        } catch (IOException e) {
            System.out.println("An error occurred while loading expenses: " + e.getMessage());
        }
    }
}
