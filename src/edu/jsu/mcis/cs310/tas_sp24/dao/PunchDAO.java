
package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * @author samkb 
 * @author mgarlyyev
 */

public class PunchDAO {
    
    private static final String QUERY_FIND1 = "SELECT * FROM event WHERE id = ?";/* prepare statement for selection */
    private static final String QUERY_FIND2 = "INSERT INTO event (terminalid, badgeid, timestamp, eventtypeid) VALUES (?, ?, ?, ?)"; /*"*/
    private static final String QUERY_FIND3 = "SELECT * FROM event WHERE badgeid = ? AND DATE(timestamp) BETWEEN ? AND ? " + "ORDER BY timestamp ASC";/*"*/
    
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
                
                ps = conn.prepareStatement(QUERY_FIND1);                   /* set ps equal to statement string */
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
                        
                        punch = new Punch(id, terminalid, badge, originaltimestamp, punchtype);                     /* call Punch object constructor and pass in arguments from database*/

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

    // Create method for Punch using Punch object
    public int create(Punch punch) {

        // Default to 0, indicating failure
        int generatedId = 0; 
    
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            // Get a connection from the DAO factory
            Connection conn = daoFactory.getConnection(); 
            
            // Check if the connection is valid 
            if (conn.isValid(0)) {
                
                // Prepare the SQL statement
                ps = conn.prepareStatement(QUERY_FIND2, Statement.RETURN_GENERATED_KEYS);
    
                ps.setInt(1, punch.getTerminalid());
                ps.setString(2, punch.getBadge().getId());
                ps.setTimestamp(3, Timestamp.valueOf(punch.getOriginaltimestamp().withNano(0)));
                ps.setInt(4, punch.getPunchtype().ordinal());
    
                int affectedRows = ps.executeUpdate();
    
                // Incase it does not create punch
                if (affectedRows == 0) {
                    throw new SQLException("Creating punch failed, no rows affected.");
                }
                
                // Process the result set
                rs = ps.getGeneratedKeys();
                
                while (rs.next()) {
                    // Get the generated key for the inserted punch
                    generatedId = rs.getInt(1); 
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
    
    // List method for Punch using Badge object and LocalDate
    public ArrayList<Punch> list(Badge badge, LocalDate date) {

        // Initialize list of Punch objects
        ArrayList<Punch> punches = new ArrayList<>(); 
    
        PreparedStatement ps = null;
        ResultSet rs = null;
    
        try {
            // Get a connection from the DAO factory
            Connection conn = daoFactory.getConnection(); 
    
            // Check if the connection is valid
            if (conn.isValid(0)) {
                // Prepare the SQL statement   
                ps = conn.prepareStatement(QUERY_FIND3);
                ps.setString(1, badge.getId());
                ps.setDate(2, java.sql.Date.valueOf(date));
                ps.setDate(3, java.sql.Date.valueOf(date));

                // Execute the query
                boolean hasResults = ps.execute();
    
                if (hasResults) {
                    // Process the result set
                    rs = ps.getResultSet();
    
                    while (rs.next()) {
                        // Map the current row of the result set to the method
                        // then add to Punch ArrayList
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
    
    
    public ArrayList<Punch> list (Badge badge, LocalDate start, LocalDate end) {
        
        // Initialize an empty list list to store the punches
        ArrayList<Punch> punches = new ArrayList();
        
        // Initialize a LocalDate variable
        LocalDate givenDate = start;

        // Iterate through each date within the specified range
        for(int i = 0; i <= ChronoUnit.DAYS.between(start, end); i++) {

            // Retrieve punches for the current date and add them to the list
            punches.addAll(list(badge, givenDate));
            
            // Move to the next date in the iteration
            givenDate = givenDate.plusDays(1);

        }

        return punches;
    }

    // A method for the List method for Punch using the ResultSet 
    private Punch constructPunchFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int terminalId = rs.getInt("terminalid");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        Badge foundBadge = badgeDAO.find(rs.getString("badgeid"));
        LocalDateTime originalTimestamp = rs.getTimestamp("timestamp").toLocalDateTime();
        EventType punchType = EventType.values()[rs.getInt("eventtypeid")];
        
        return new Punch(id, terminalId, foundBadge, originalTimestamp, punchType);
    }
    
}
    
