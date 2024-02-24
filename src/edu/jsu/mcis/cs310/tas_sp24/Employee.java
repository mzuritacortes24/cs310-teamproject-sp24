package edu.jsu.mcis.cs310.tas_sp24;
import java.time.*;  
import java.time.format.DateTimeFormatter;

public class Employee {
    private int id;
    private String firstname;
    private String middlename;
    private String lastname;
    private LocalDateTime active;
    private Badge badge;
    private Department department;
    private Shift shift;
    private EmployeeType employeeType;

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
