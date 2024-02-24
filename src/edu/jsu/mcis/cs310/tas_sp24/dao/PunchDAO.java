
package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.LocalDateTime;


public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";/* prepare statment for selection */
    
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
                        
                        int terminalid = rs.getInt("terminalid");                           /* populate value for Punch constructor */
                        
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();                             /* " */
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));            /* " */
                        
                        java.sql.Timestamp timestamp = rs.getTimestamp("timestamp");        /* " */
                        LocalDateTime originaltimestamp = timestamp.toLocalDateTime();            /* " */
                        
                        EventType punchType = EventType.values()[rs.getInt("eventtypeid")]; /* " */
                        
                        punch = new Punch(terminalid, badge, originaltimestamp, punchType);       /* call Punch object constructor and pass in arguments from database*/

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
    
}
    
