package edu.jsu.mcis.cs310.tas_sp24.dao;

import edu.jsu.mcis.cs310.tas_sp24.Badge;
import java.sql.*;

/**
 * BadgeDAO class for creating badge objects from the database
 * @author samkb
 */
public class BadgeDAO {

    private static final String QUERY_FIND1 = "SELECT * FROM badge WHERE id = ?"; 
    private static final String QUERY_FIND2 = "INSERT INTO badge (id, description) VALUES (?, ?)";
    private static final String QUERY_FIND3 = "DELETE FROM badge WHERE id = ?";

    private final DAOFactory daoFactory;

    BadgeDAO(DAOFactory daoFactory) {

        this.daoFactory = daoFactory;

    }

    /**
     * A find method which retrieves the badge instance from the database
     * @param id The badge id to retrieve from the database
     * @return
     */
    public Badge find(String id) {

        Badge badge = null;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {

            Connection conn = daoFactory.getConnection();

            if (conn.isValid(0)) {

                ps = conn.prepareStatement(QUERY_FIND1);
                ps.setString(1, id);

                boolean hasresults = ps.execute();

                if (hasresults) {

                    rs = ps.getResultSet();

                    while (rs.next()) {

                        String description = rs.getString("description");
                        badge = new Badge(id, description);

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

        return badge;

    }

    // Added boolean create method

    /**
     * A create method which adds the badge instance to the database
     * @param badge The badge object to add to the database
     * @return
     */
    public boolean create(Badge badge)   {
        
        PreparedStatement ps = null;
        
        try  {
        
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0))   {
            
                ps = conn.prepareStatement(QUERY_FIND2);
                ps.setString(1, badge.getId());
                ps.setString(2, badge.getDescription());
                
                int rowsAffected = ps.executeUpdate();
                return rowsAffected == 1;
                
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return false;
    }
    
    // Added boolean delete method

    /**
     * A delete method which removes the badge instance from the database
     * @param badgeId The badge id to remove from the database
     * @return
     */
    public boolean delete(String badgeId)   {
        
        PreparedStatement ps = null;
        
        try  {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0))   {
                
                ps = conn.prepareStatement(QUERY_FIND3);
                ps.setString(1, badgeId);
                                
                int rowsAffected = ps.executeUpdate();
                return rowsAffected == 1;
                
            }
        }catch (SQLException e) {
            throw new DAOException(e.getMessage());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    throw new DAOException(e.getMessage());
                }
            }
        }
        return false;
    }

    
}
