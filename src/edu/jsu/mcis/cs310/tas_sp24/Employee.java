package edu.jsu.mcis.cs310.tas_sp24;
import java.time.*;  
import java.time.format.DateTimeFormatter;
/**
 * Employee datatype which holds the data for a single employee instance
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

    /**
     *
     * @param id The employee id
     * @param firstname The employee's first name
     * @param middlename The employee's middle name
     * @param lastname The employee's last name
     * @param active The date in which the employee began work
     * @param badge The badge object associated with the employee
     * @param department The department object associated with employee
     * @param shift The shift object associated with the employee
     * @param employeeType The employeetype object associated with the employee
     */
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

    /**
     * Getter for the id class variable
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the firstname class variable
     * @return firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Getter for the middlename class variable
     * @return middlename
     */
    public String getMiddlename() {
        return middlename;
    }

    /**
     * Getter for the lastname class variable
     * @return lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Getter for the active class variable
     * @return active
     */
    public LocalDateTime getActive() {
        return active;
    }

    /**
     * Getter for the badge class variable
     * @return badge
     */
    public Badge getBadge() {
        return badge;
    }

    /**
     * Getter for the department class variable
     * @return department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * Getter for the shift class variable
     * @return shift
     */
    public Shift getShift() {
        return shift;
    }
    
    /**
     * Getter for the employeetype class variable
     * @return employee
     */
    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    /**
     * toString override for the class
     * @return
     */
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
