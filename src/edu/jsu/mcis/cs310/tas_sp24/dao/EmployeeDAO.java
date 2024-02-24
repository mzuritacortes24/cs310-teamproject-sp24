package edu.jsu.mcis.cs310.tas_sp24.dao;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import java.time.LocalDateTime;

public class EmployeeDAO {
    
    private final DAOFactory daoFactory;

    EmployeeDAO(DAOFactory daoFactory) {
        
        this.daoFactory = daoFactory;
    }
    
    public Employee find(int id) {
        Employee employee = null;
        String SQL = "SELECT * FROM employee WHERE id = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try { 

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(SQL);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        
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

        
        return employee;
    }
    
    public Employee find(Badge badge)   {
        String badgeId = badge.getId();
        String SQL = "SELECT * FROM employee WHERE badgeid = ?";
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try { 

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(SQL);
                ps.setString(1, badgeId);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {
                        int employeeId = rs.getInt("id");
                        return find(employeeId);  
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
        return null;
    }
}
