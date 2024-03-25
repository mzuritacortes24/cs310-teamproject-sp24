package edu.jsu.mcis.cs310.tas_sp24.dao;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.sql.*;
import java.time.LocalDateTime;
/**
 *
 * @author Mauricio
 */
public class EmployeeDAO {
    
    private static final String QUERY_FIND1 = "SELECT * FROM employee WHERE id = ?";
    private static final String QUERY_FIND2 = "SELECT * FROM employee WHERE badgeid = ?";
    
    private final DAOFactory daoFactory;

    // Constructor: Initializes the DAO with a factory for creating connections
    public EmployeeDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    // Finds an employee by their ID
    public Employee find(int id) {
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Get a connection from the DAO factory
            Connection conn = daoFactory.getConnection();

            // Check if the connection is valid
            if (conn.isValid(0)) {

                // Prepare the SQL statement
                ps = conn.prepareStatement(QUERY_FIND1);
                ps.setInt(1, id);

                // Execute the query
                boolean hasresults = ps.execute();

                if (hasresults) {
                    // Process the result set
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // Map the current row of the result set to an Employee object
                        return mapToEmployee(rs);
                    }
                }

            }
        }  catch (SQLException e) {
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

    // Finds an employee by their badge
    public Employee find(Badge badge) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Get a connection from the DAO factory
            Connection conn = daoFactory.getConnection();
            
            // Check if the connection is valid
            if (conn.isValid(0)) {
                // Prepare the SQL statement
                ps = conn.prepareStatement(QUERY_FIND2);
                ps.setString(1, badge.getId());

                // Execute the query
                boolean hasresults = ps.execute();

                if (hasresults) {
                    // Process the result set
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // Map the current row of the result set to an Employee object
                        return mapToEmployee(rs);

                    }

                }

            }

        } catch (SQLException e) {
            // Throw a custom DAOException in case of SQL errors
            throw new DAOException(e.getMessage());

        } finally {
            // Ensure resources are closed to avoid leaks
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
    // Maps a row of the result set to an Employee object
    public Employee mapToEmployee(ResultSet rs) throws SQLException {
       // Extract employee details from the result set
       int id = rs.getInt("id");
       String firstname = rs.getString("firstname");
       String middlename = rs.getString("middlename");
       String lastname = rs.getString("lastname");
       LocalDateTime active = rs.getTimestamp("active").toLocalDateTime();
       String badgeId = rs.getString("badgeid");
       int departmentId = rs.getInt("departmentid");
       int shiftId = rs.getInt("shiftid");
       EmployeeType employeeType = EmployeeType.values()[rs.getInt("employeetypeid")];
       
       // Use DAOFactory to find related objects by their IDs
       Badge badge = daoFactory.getBadgeDAO().find(badgeId);
       Department department = daoFactory.getDepartmentDAO().find(departmentId);
       Shift shift = daoFactory.getShiftDAO().find(shiftId);
       // Return a new Employee object constructed with the extracted details
       return new Employee(id, firstname, middlename, lastname, active, badge, department, shift, employeeType);
    }
}
