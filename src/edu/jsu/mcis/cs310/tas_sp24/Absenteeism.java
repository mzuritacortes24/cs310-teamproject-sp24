package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Absenteeism datatype which contains data for a single employee and payPeriod's absenteeism
 * @author Mauricio
 */
public class Absenteeism {

    private final Employee employee;
    private final LocalDate payPeriod;
    private final BigDecimal percentage;

    /**
     * 
     * @param employee The employee object for the absenteeism instance
     * @param payPeriod The LocalDate object for the payPeriod for the absenteeism calculation
     * @param percentage The calculated absenteeism percentage for the employee
     */
    public Absenteeism(Employee employee, LocalDate payPeriod, BigDecimal percentage) {
        this.employee = employee;
        this.payPeriod = payPeriod;
        this.percentage = percentage;
    }

    /**
     * Getter for the employee class variable
     * @return
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Getter for the payPeriod class variable
     * @return
     */
    public LocalDate getPayPeriod() {
        return payPeriod;
    }

    /**
     * Getter for the percentage class variable
     * @return
     */
    public BigDecimal getPercentage() {
        return percentage;
    }
    
    /**
     * toString override for the class
     * @return
     */
    @Override
    public String toString(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String formattedDate = payPeriod.format(formatter); // Reformat the date
        return String.format("#%s (Pay Period Starting %s): %.2f%%",
                employee.getBadge().getId(),
                formattedDate,
                percentage);
    }

}
