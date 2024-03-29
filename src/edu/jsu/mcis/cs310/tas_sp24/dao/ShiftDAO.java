package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/*
 * @author blake
 */
public class ShiftDAO {
    
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    
    private static final String QUERY_FIND_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT shift.* FROM shift INNER JOIN employee ON shift.id = employee.shiftid WHERE employee.badgeid = ?";
    private static final String QUERY_GET_DAILYSCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";
    
     public Shift find(int id) {

        Shift shift = null;
        
        // initializing ps and rs
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;

        try {

            // initializing connection and query
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                // prepare statement
                ps = conn.prepareStatement(QUERY_FIND_ID);
                ps.setInt(1, id);

                if (ps.execute()) {
                    
                    // get result set and assign
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        // create shift  
                        ps2 = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                        ps2.setInt(1, rs.getInt("dailyscheduleid"));
                          
                        if (ps2.execute()) {
                            
                            rs2 = ps2.getResultSet();
                        
                            while(rs2.next()){
                                
                                shift = createShiftFromResultSet(rs, rs2);
                                
                            }
                        
                        }

                    }

                }

            }

        } catch (SQLException e) {

            //throw new DAOException(e.getMessage());

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
     

     // find shift
     public Shift find(Badge badge) {

        Shift shift = null;
       
        // initialize ps and rs
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND_BADGE);
                ps.setString(1, badge.getId());

                if (ps.execute()) {

                    // get results and assign
                    rs = ps.getResultSet();

                    while (rs.next()) {
                        // create shift  
                        ps2 = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                        ps2.setInt(1, rs.getInt("dailyscheduleid"));
                        
                        if (ps2.execute()) {
                        
                            rs2 = ps2.getResultSet();
                            
                            while(rs2.next()){
                                
                                shift = createShiftFromResultSet(rs, rs2);
                                
                            }
                        
                        }
                        
                    }

                }

            }

        } catch (SQLException e) {

            //throw new DAOException(e.getMessage());

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

    // create shift
    private Shift createShiftFromResultSet(ResultSet rs, ResultSet rs2) throws SQLException {
        
        Map<String, String> shiftInfo = new HashMap<>();

        shiftInfo.put("description", rs.getString("description"));
        shiftInfo.put("id", rs.getString("id"));
        shiftInfo.put("shiftstart", rs2.getString("shiftstart"));
        shiftInfo.put("shiftstop", rs2.getString("shiftstop"));
        shiftInfo.put("lunchstart", rs2.getString("lunchstart"));
        shiftInfo.put("lunchstop", rs2.getString("lunchstop"));
        shiftInfo.put("graceperiod", rs2.getString("graceperiod"));
        shiftInfo.put("roundinterval", rs2.getString("roundinterval"));
        shiftInfo.put("dockpenalty", rs2.getString("dockpenalty"));
        shiftInfo.put("lunchthreshold", rs2.getString("lunchthreshold"));
        
        return new Shift(shiftInfo);
        
    }
    
}
