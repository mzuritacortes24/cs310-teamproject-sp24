package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 *
 * @author blake
 */
public class ShiftDAO {
    
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    
     public Shift find(int id) {

        Shift shift = null;
        
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();
            String query = "SELECT * FROM shift WHERE id = ?";

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(query);
                ps.setInt(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        //String description = rs.getString("description");
                        shift = createShiftFromResultSet(rs);

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

        return shift;

    }
     

     
     public Shift find(Badge badge) {

        Shift shift = null;
       
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();
            
            String query = "SELECT shift.* FROM shift INNER JOIN employee ON shift.id = employee.shiftid WHERE employee.badgeid = ?";

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(query);
                ps.setString(1, badge.getId());

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        //String description = rs.getString("description");
                        shift = createShiftFromResultSet(rs);

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

        return shift;

    }

    
    
    private Shift createShiftFromResultSet(ResultSet rs) throws SQLException {
        
        Map<String, String> shiftInfo = new HashMap<>();
        
        shiftInfo.put("Description", rs.getString("description"));
        
        shiftInfo.put("Shift Start", rs.getString("shiftstart"));
        shiftInfo.put("Shift Stop", rs.getString("shiftstop"));
        shiftInfo.put("Lunch Start", rs.getString("lunchstart"));
        shiftInfo.put("Lunch Stop", rs.getString("lunchstop"));
        
        shiftInfo.put("Round Interval", rs.getString("roundinterval"));
        shiftInfo.put("Grace Period", rs.getString("graceperiod"));
        shiftInfo.put("Dock Penalty", rs.getString("dockpenalty"));
        shiftInfo.put("Lunch Threshold", rs.getString("lunchthreshold"));

        return new Shift(shiftInfo);

    }
}
