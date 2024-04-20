package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Shift;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/*
 * @author blake
 */

/**
 * ShiftDAO class for creating shift objects from the database
 * @author samkb
 */

public class ShiftDAO {
    
    private final DAOFactory daoFactory;
    
    ShiftDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    
    private static final String QUERY_FIND_ID = "SELECT * FROM shift WHERE id = ?";
    private static final String QUERY_FIND_BADGE = "SELECT shift.* FROM shift INNER JOIN employee ON shift.id = employee.shiftid WHERE employee.badgeid = ?";
    private static final String QUERY_GET_SCHEDULEOVERRIDE = "SELECT * FROM scheduleoverride WHERE ((? >= start) AND ((? <= end) OR (end IS NULL)))";
    private static final String QUERY_GET_DAILYSCHEDULE = "SELECT * FROM dailyschedule WHERE id = ?";
    
    /**
     * A find method which retrieves the shift instance from the database
     * @param id The shift id to retrieve from the database
     * @return
     */
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

    /**
     * A find method which retrieves the shift instance from the database
     * @param badge The badge whose shift should be retrieved from the database
     * @return
     */
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
     
    /**
     * A find method which retrieves the shift instance from the database for a specific date
     * @param badge The badge whose shift should be retrieved from the database
     * @param punchdate The date of the shift to retrieve from the database
     * @return
     */
    public Shift find(Badge badge, LocalDate punchdate) {

        Shift shift = null;
        
        LocalDateTime punchdatetime = LocalDateTime.of(punchdate, LocalTime.MIN);
        
        java.sql.Timestamp punchdatets = java.sql.Timestamp.valueOf(punchdatetime);
       
        // initialize ps and rs
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        PreparedStatement ps2;
        ResultSet rs2;

        PreparedStatement ps3;
        ResultSet rs3;
         
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
                                
                                ps3 = conn.prepareStatement(QUERY_GET_SCHEDULEOVERRIDE);
                                ps3.setTimestamp(1, punchdatets);
                                ps3.setTimestamp(2, punchdatets);
                                
                                if (ps3.execute()) {
                                    
                                    rs3 = ps3.getResultSet();
                                    
                                    while(rs3.next()){
                                        
                                        shift = createShiftFromResultSet(rs, rs2, rs3, badge);
                                        
                                    }
                                    
                                    if(!(rs3.next())){
                                        
                                        shift = createShiftFromResultSet(rs, rs2);
                                        
                                    }
                                    
                                }
                                
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
    
    private Shift createShiftFromResultSet(ResultSet rs, ResultSet rs2, ResultSet rs3, Badge badge) throws SQLException {
        
        Shift shift = null;
        
        // initializing ps and rs
        PreparedStatement ps = null;
        ResultSet rs4 = null;

        try {

            // initializing connection and query
            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {
                
                // prepare statement
                ps = conn.prepareStatement(QUERY_GET_DAILYSCHEDULE);
                ps.setInt(1, rs3.getInt("dailyscheduleid"));

                if (ps.execute()) {
                    
                    // get result set and assign
                    rs4 = ps.getResultSet();
                    
                    while (rs4.next()) {

                        Map<String, String> shiftInfo = new HashMap<>();
                        Map<String, String> shiftExceptionInfo = new HashMap<>();

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

                        shift = new Shift(shiftInfo);
                        
                        if((badge.getId().equals(rs3.getString("badgeid"))) || (rs3.getString("badgeid") == null)){
                            
                            shiftExceptionInfo.put("shiftstart", rs4.getString("shiftstart"));
                            shiftExceptionInfo.put("shiftstop", rs4.getString("shiftstop"));
                            shiftExceptionInfo.put("lunchstart", rs4.getString("lunchstart"));
                            shiftExceptionInfo.put("lunchstop", rs4.getString("lunchstop"));
                            shiftExceptionInfo.put("graceperiod", rs4.getString("graceperiod"));
                            shiftExceptionInfo.put("roundinterval", rs4.getString("roundinterval"));
                            shiftExceptionInfo.put("dockpenalty", rs4.getString("dockpenalty"));
                            shiftExceptionInfo.put("lunchthreshold", rs4.getString("lunchthreshold"));

                            shift.setDailySchedule(rs3.getInt("day"), shiftExceptionInfo);

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
    
}
