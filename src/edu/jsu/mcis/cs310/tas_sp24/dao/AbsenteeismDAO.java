package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import java.time.DayOfWeek;
import java.time.temporal.TemporalAdjusters;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM absenteeism WHERE employeeid = ? AND payperiod = ?";
    
    private final DAOFactory daoFactory;

    public AbsenteeismDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public Absenteeism find(Employee employee, LocalDate payPeriod) {
        Absenteeism absenteeism = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FIND); 
                ps.setInt(1, employee.getId()); 
                ps.setDate(2, java.sql.Date.valueOf(payPeriod)); 

                boolean hasResults = ps.execute(); 

                if (hasResults) {
                    rs = ps.getResultSet(); 

                    if (rs.next()) { 
                        BigDecimal percentage = rs.getBigDecimal("percentage");
                        absenteeism = new Absenteeism(employee, payPeriod.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)), percentage);
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage()); 
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        } 

        return absenteeism;
    }

    public void create (Absenteeism absenteeism)    {
        PreparedStatement ps = null;

        // SQL query to insert a new record or update an existing one
        String SQL = "INSERT INTO absenteeism (employeeid, payperiod, percentage) VALUES (?, ?, ?) " +
        "ON DUPLICATE KEY UPDATE percentage = VALUES(percentage);";
         
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(SQL);

                // Set the parameters for the PreparedStatement from the Absenteeism object.
                ps.setInt(1, absenteeism.getEmployee().getId());
                ps.setDate(2, java.sql.Date.valueOf(absenteeism.getPayPeriod())); // Convert LocalDate to java.sql.Date
                ps.setBigDecimal(3, absenteeism.getPercentage());

                // Execute the update.
                ps.executeUpdate();

            
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
    }
}
