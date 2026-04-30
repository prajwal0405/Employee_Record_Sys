import java.sql.SQLException;
import java.util.Scanner;

// Main class - entry point with full menu system
public class Main {

    static Scanner sc = new Scanner(System.in);
    static EmployeeManager manager = new EmployeeManager();

    public static void main(String[] args) {

        System.out.println("==============================================");
        System.out.println("       EMPLOYEE RECORD SYSTEM - Java + DB    ");
        System.out.println("==============================================");

        // Connect to DB and create table if not exists
        try {
            manager.initialize();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
            System.out.println("Please check your MySQL credentials in DBConnection.java");
            return;
        }

        int choice;
        do {
            printMenu();
            choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        manager.viewAll();
                        break;
                    case 3:
                        updateEmployee();
                        break;
                    case 4:
                        deleteEmployee();
                        break;
                    case 5:
                        searchEmployee();
                        break;
                    case 6:
                        System.out.println("Exiting. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter 1-6.");
                }
            } catch (SQLException e) {
                System.out.println("Database error: " + e.getMessage());
            }

        } while (choice != 6);

        DBConnection.closeConnection();
        sc.close();
    }

    // ─── Menu ─────────────────────────────────────────────────────────────
    static void printMenu() {
        System.out.println("\n===== Employee Record System =====");
        System.out.println("1. Add Employee");
        System.out.println("2. View All Employees");
        System.out.println("3. Update Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Search Employee by Name");
        System.out.println("6. Exit");
    }

    // ─── Add ──────────────────────────────────────────────────────────────
    static void addEmployee() throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        System.out.print("Enter department: ");
        String dept = sc.nextLine().trim();
        if (dept.isEmpty()) { System.out.println("Department cannot be empty."); return; }

        double salary = getDoubleInput("Enter salary: ");
        if (salary < 0) { System.out.println("Salary cannot be negative."); return; }

        System.out.print("Enter email: ");
        String email = sc.nextLine().trim();
        if (!isValidEmail(email)) { System.out.println("Invalid email format."); return; }

        manager.addEmployee(name, dept, salary, email);
    }

    // ─── Update ───────────────────────────────────────────────────────────
    static void updateEmployee() throws SQLException {
        int id = getIntInput("Enter Employee ID to update: ");
        Employee existing = manager.findById(id);

        if (existing == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }

        System.out.println("Found: " + existing);
        System.out.print("Enter new name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }

        System.out.print("Enter new department: ");
        String dept = sc.nextLine().trim();
        if (dept.isEmpty()) { System.out.println("Department cannot be empty."); return; }

        double salary = getDoubleInput("Enter new salary: ");
        if (salary < 0) { System.out.println("Salary cannot be negative."); return; }

        System.out.print("Enter new email: ");
        String email = sc.nextLine().trim();
        if (!isValidEmail(email)) { System.out.println("Invalid email format."); return; }

        boolean updated = manager.updateEmployee(id, name, dept, salary, email);
        System.out.println(updated ? "Employee updated successfully." : "Update failed.");
    }

    // ─── Delete ───────────────────────────────────────────────────────────
    static void deleteEmployee() throws SQLException {
        int id = getIntInput("Enter Employee ID to delete: ");
        Employee existing = manager.findById(id);

        if (existing == null) {
            System.out.println("No employee found with ID: " + id);
            return;
        }

        System.out.println("Found: " + existing);
        System.out.print("Are you sure you want to delete? (yes/no): ");
        String confirm = sc.nextLine().trim();

        if (confirm.equalsIgnoreCase("yes")) {
            boolean deleted = manager.deleteEmployee(id);
            System.out.println(deleted ? "Employee deleted successfully." : "Delete failed.");
        } else {
            System.out.println("Delete cancelled.");
        }
    }

    // ─── Search ───────────────────────────────────────────────────────────
    static void searchEmployee() throws SQLException {
        System.out.print("Enter name to search: ");
        String keyword = sc.nextLine().trim();
        manager.searchByName(keyword);
    }

    // ─── Input helpers ────────────────────────────────────────────────────
    static int getIntInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }
    }

    static double getDoubleInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }
}
