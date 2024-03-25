package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Mauricio
 */
public class Absenteeism {

    private final Employee employee;
    private final LocalDate payPeriod;
    private final BigDecimal percentage;

    public Absenteeism(Employee employee, LocalDate payPeriod, BigDecimal percentage) {
        this.employee = employee;
        this.payPeriod = payPeriod;
        this.percentage = percentage;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDate getPayPeriod() {
        return payPeriod;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
    
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
