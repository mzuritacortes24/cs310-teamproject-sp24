
package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import java.sql.*;
import java.time.LocalDateTime;


public class PunchDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM event WHERE id = ?";
    
    private final DAOFactory daoFactory;
    
    PunchDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }
    
    public Punch find(int id){
        Punch punch = null;

        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            Connection conn = daoFactory.getConnection();
            
            if(conn.isValid(0)) {
                
                ps = conn.prepareStatement(QUERY_FIND);
                ps.setInt(1, id);
            
                boolean hasresults = ps.execute();
                
                if (hasresults) {
                    
                    rs = ps.getResultSet();
                    
                    while (rs.next()) {
                        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
                        int terminalid = rs.getInt("terminalid");
                        Badge badge = badgeDAO.find(rs.getString("badgeid"));
                        java.sql.Timestamp timestamp = rs.getTimestamp("timestamp");
                        LocalDateTime originaltimestamp = timestamp.toLocalDateTime();
                        EventType punchType = null;
                        
                        switch(rs.getInt("eventtypeid")){
                            case 0 -> punchType = EventType.valueOf("CLOCK_OUT");
                            
                            case 1 -> punchType = EventType.valueOf("CLOCK_IN");
                                
                            case 2 -> punchType = EventType.valueOf("TIME_OUT");
                                
                        }
                        
                        punch = new Punch(terminalid, badge, originaltimestamp, punchType);

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
    
