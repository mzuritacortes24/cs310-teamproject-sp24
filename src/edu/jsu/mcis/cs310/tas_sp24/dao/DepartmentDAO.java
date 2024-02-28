package edu.jsu.mcis.cs310.tas_sp24.dao;
import edu.jsu.mcis.cs310.tas_sp24.Department;
import java.sql.*;
/**
 *
 * @author Denver @author William
 */
public class DepartmentDAO {

    private static final String QUERY_FIND = "SELECT * FROM department WHERE id = ?"; //set QUERY_FIND as final

    private final DAOFactory daoFactory; // 

    DepartmentDAO(DAOFactory daoFactory) { //William's Test Commit
        this.daoFactory = daoFactory;
    }

    public Department find(int id) { // set department, ps and rs all as nulls
        Department department = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        // use try catch
        try {
            Connection conn = daoFactory.getConnection();
            if (conn.isValid(0)) { 
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
                boolean hasResults = ps.execute(); //bool

                if (hasResults) {
                    rs = ps.getResultSet();
                    while (rs.next()) { //while loop used 
                        String description = rs.getString("description"); // string variable used
                        int terminalid = rs.getInt("terminalid"); // int variable used
                        department = new Department(id, description, terminalid); // id, description, terminalid
                    }
                }
            }
        } catch (SQLException e) { // catch
            throw new DAOException(e.getMessage()); //throw
        } finally {
            if (rs != null) {
                try { // try catch again
                    rs.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage()); 
                }
            }
            if (ps != null) {
                try {
                    ps.close(); //close
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return department; //last return statement
    }
}