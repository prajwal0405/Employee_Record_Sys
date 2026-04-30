import java.sql.SQLException;
import java.util.List;

public class EmployeeManager {

    private EmployeeDAO dao;

    public EmployeeManager() {
        this.dao = new EmployeeDAO();
    }


    public void initialize() throws SQLException {
        dao.createTableIfNotExists();
    }


    public void addEmployee(String name, String dept, double salary, String email) throws SQLException {
        Employee e = new Employee(name, dept, salary, email);
        boolean success = dao.addEmployee(e);
        System.out.println(success ? "Employee added successfully." : "Failed to add employee.");
    }

    public void viewAll() throws SQLException {
        List<Employee> list = dao.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        printHeader();
        for (Employee e : list) System.out.println(e);
        printDivider();
        System.out.println("Total employees: " + list.size());
    }


    public boolean updateEmployee(int id, String name, String dept, double salary, String email) throws SQLException {
        Employee e = new Employee(id, name, dept, salary, email);
        return dao.updateEmployee(e);
    }

    public boolean deleteEmployee(int id) throws SQLException {
        return dao.deleteEmployee(id);
    }

    public void searchByName(String keyword) throws SQLException {
        List<Employee> list = dao.searchByName(keyword);
        if (list.isEmpty()) {
            System.out.println("No employee found with name: " + keyword);
            return;
        }
        printHeader();
        for (Employee e : list) System.out.println(e);
        printDivider();
    }

    public Employee findById(int id) throws SQLException {
        return dao.getEmployeeById(id);
    }

    private void printHeader() {
        printDivider();
        System.out.printf("| %-4s | %-20s | %-15s | %-10s | %-25s |%n",
                "ID", "Name", "Department", "Salary", "Email");
        printDivider();
    }

    private void printDivider() {
        System.out.println("+------+----------------------+-----------------+------------+---------------------------+");
    }
}
