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

    public Employee() {
        
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
    
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String activeFormatted = active.format(formatter);

        String fullName = firstname + (middlename != null && !middlename.isEmpty() ? " " + middlename + " " : " ") + lastname;

        return "Employee{" +
                "id=" + id +
                ", name='" + fullName + '\'' +
                ", badge=" + badge.getId() + // Assuming the Badge class has a getId() method.
                ", type=" + employeeType +
                ", department=" + department.getDescription() + // Assuming the Department class has a getDescription() method.
                ", active=" + activeFormatted +
                '}';
    }
}
