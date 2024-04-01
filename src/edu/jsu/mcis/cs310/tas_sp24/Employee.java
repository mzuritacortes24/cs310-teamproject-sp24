package edu.jsu.mcis.cs310.tas_sp24;
import java.time.*;  
import java.time.format.DateTimeFormatter;
/**
 *
 * @author Mauricio
 */
public class Employee {
    private final int id;
    private final String firstname;
    private final String middlename;
    private final String lastname;
    private final LocalDateTime active;
    private final Badge badge;
    private final Department department;
    private final Shift shift;
    private final EmployeeType employeeType;

    public Employee(int id, String firstname, String middlename, String lastname, LocalDateTime active, Badge badge, Department department, Shift shift, EmployeeType employeeType ) {
        this.id = id;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.active = active;
        this.badge = badge;
        this.department = department;
        this.shift = shift;
        this.employeeType = employeeType;
        
    } 
    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDateTime getActive() {
        return active;
    }

    public Badge getBadge() {
        return badge;
    }

    public Department getDepartment() {
        return department;
    }

    public Shift getShift() {
        return shift;
    }
    
    public EmployeeType getEmployeeType() {
        return employeeType;
    }
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Build the name string conditionally based on the presence of a middle name
        String name = String.format("%s, %s", this.lastname, this.firstname);
        if (this.middlename != null && !this.middlename.isEmpty()) {
            // Append the first character of the middle name without a period
            name += " " + this.middlename.charAt(0);
        }

        // Format the rest of the string as per the requirements
        return String.format("ID #%d: %s (#%s), Type: %s, Department: %s, Active: %s",
            this.id,
            name,
            this.badge.getId(),
            this.employeeType.toString(),
            this.department.getDescription(),
            this.active.format(formatter));
    }
}
