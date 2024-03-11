package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.sql.*;
import java.time.LocalDate;
import java.math.BigDecimal;
import edu.jsu.mcis.cs310.tas_sp24.Absenteeism;
import edu.jsu.mcis.cs310.tas_sp24.Employee;

public class AbsenteeismDAO {

    private static final String QUERY_FIND = "SELECT * FROM Absenteeism WHERE employee_id = ? AND pay_period = ?";
    
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
                        absenteeism = new Absenteeism(employee, payPeriod.atStartOfDay(), percentage);
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
}
