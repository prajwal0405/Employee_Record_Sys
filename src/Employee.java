
public class Employee {

    private int id;
    private String name;
    private String department;
    private double salary;
    private String email;

    public Employee(String name, String department, double salary, String email) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.email = email;
    }

    public Employee(int id, String name, String department, double salary, String email) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.email = email;
    }

    public int getId()           { return id; }
    public String getName()      { return name; }
    public String getDepartment(){ return department; }
    public double getSalary()    { return salary; }
    public String getEmail()     { return email; }

    public void setName(String name)           { this.name = name; }
    public void setDepartment(String dept)     { this.department = dept; }
    public void setSalary(double salary)       { this.salary = salary; }
    public void setEmail(String email)         { this.email = email; }

    @Override
    public String toString() {
        return String.format("| %-4d | %-20s | %-15s | %-10.2f | %-25s |",
                id, name, department, salary, email);
    }
}
