package edu.jsu.mcis.cs310.tas_sp24;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Mauricio
 */
public class Absenteeism {

    private Employee employee;
    private LocalDateTime payPeriod;
    private BigDecimal percentage;

    public Absenteeism(Employee employee, LocalDateTime payPeriod, BigDecimal percentage) {
        this.employee = employee;
        this.payPeriod = payPeriod;
        this.percentage = percentage;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LocalDateTime getPayPeriod() {
        return payPeriod;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }
    
    @Override
    public String toString(){
        return String.format("#%s (Pay Period Starting %s): %.2f%%",
                employee.getBadge().getId(),
                payPeriod.toString(),
                percentage.doubleValue());
    }

}
