
package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.*;
import java.util.*;

/**
 * @author samkb
 */

public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";/* prepare statement for selection */
    
    private final DAOFactory daoFactory;                                        /* instantiate DAOFactory object */
    
    PunchDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;                                           /* set daoFactory equal to overall daoFactory */

    }
    
    public Punch find(int id){
        
        Punch punch = null;                                                     /* initialize Punch object */

        PreparedStatement ps = null;                                            /* initialize PreparedStatement */
        ResultSet rs = null;                                                    /* initialize ResultSet */
        
        try {
            
            Connection conn = daoFactory.getConnection();                       /* connect to database */
            
            if(conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND);                   /* set ps equal to statement string */
                ps.setInt(1, id);                                          /* pass arguments into ps */
            
                boolean hasresults = ps.execute();                              /* execute statement and return true/false */
                
                if (hasresults) {
                    
                    rs = ps.getResultSet();                                     /* set rs equal to ps ResultSet */
                    
                    while (rs.next()) {
                        
                        int terminalid = rs.getInt("terminalid");                                         /* populate value for Punch constructor */
                        
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();                                           /* " */
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));                          /* " */
                        
                        LocalDateTime originaltimestamp = rs.getTimestamp("timestamp").toLocalDateTime(); /* " */
                        
                        EventType punchtype = EventType.values()[rs.getInt("eventtypeid")];               /* " */
                        
                        punch = new Punch(terminalid, badge, originaltimestamp, punchtype);                     /* call Punch object constructor and pass in arguments from database*/

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
        
        return punch;
        
    }
    public int create(Punch punch) {
        int generatedId = 0; // Default to 0, indicating failure
        String SQL = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)";
    
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            Connection conn = daoFactory.getConnection(); // Connect to database
    
            if (conn.isValid(0)) {
                
                ps = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
    
                ps.setInt(1, punch.getTerminalid());
                ps.setString(2, punch.getBadge().getId());
                ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginaltimestamp().withNano(0)));
                ps.setInt(4, punch.getPunchtype().ordinal());
    
                int affectedRows = ps.executeUpdate();
    
                if (affectedRows == 0) {
                    throw new SQLException("Creating punch failed, no rows affected.");
                }
                    rs = ps.getGeneratedKeys();
                
                while (rs.next()) {
                    generatedId = rs.getInt(1); // Get the generated key for the inserted punch
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage()); // Handle SQL exceptions
        } finally {
            // Close resources
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
    
        return generatedId; // Return the generated ID or 0 if failed
    }
    public ArrayList<Punch> list(Badge badge, LocalDate date) {
        ArrayList<Punch> punches = new ArrayList<>(); // Initialize list of Punch objects
        String SQL = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) BETWEEN ? AND ? " +
                                    "ORDER BY timestamp ASC";
    
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            Connection conn = daoFactory.getConnection(); // Connect to database
    
            if (conn.isValid(0)) {
                // Adjust SQL to match your SQL query for retrieving punches
                
                ps = conn.prepareStatement(SQL);
                ps.setString(1, badge.getId());
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setDate(3, java.sql.Date.valueOf(date.plusDays(0)));

                boolean hasResults = ps.execute();
    
                if (hasResults) {
                    rs = ps.getResultSet();
    
                    while (rs.next()) {
                        punches.add(constructPunchFromResultSet(rs));
                    }
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            // Resource cleanup
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
    
        return punches;
    }
    
    private Punch constructPunchFromResultSet(ResultSet rs) throws SQLException {
        int terminalId = rs.getInt("terminalid");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        Badge foundBadge = badgeDAO.find(rs.getString("badgeid"));
        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        EventType punchType = EventType.values()[rs.getInt("eventtypeid")];
    
        return new Punch(terminalId, foundBadge, originalTimestamp, punchType);
    }
    
}
    
