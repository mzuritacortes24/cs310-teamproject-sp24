package edu.jsu.mcis.cs310.tas_sp24.dao;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ReportDAO {

    private static final String QUERY_FIND1 = 
        "SELECT b.id AS badgeid, " +
        "CONCAT(e.lastname, ', ', e.firstname, IFNULL(CONCAT(' ', e.middlename), '')) AS fullname, " +
        "d.description AS department, " +
        "et.description AS employeetype " +
        "FROM employee e " +
        "INNER JOIN badge b ON e.badgeid = b.id " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN employeetype et ON e.employeetypeid = et.id ";

    private final DAOFactory daoFactory;

    public ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public String getBadgeSummary(Integer departmentId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray badgeSummary = new JsonArray();
    
        try {
            Connection conn = daoFactory.getConnection();
    
            if (conn.isValid(0)) {
                // Start building the SQL query for null clause
                StringBuilder QUERY = new StringBuilder(QUERY_FIND1);
    
                // Conditionally add WHERE clause if departmentId is not null
                if (departmentId != null) {
                    QUERY.append("WHERE e.departmentid = ? ");
                }
    
                QUERY.append("ORDER BY e.lastname, e.firstname, e.middlename");
    
                ps = conn.prepareStatement(QUERY.toString());
    
                // Set the departmentId parameter if not null
                if (departmentId != null) {
                    ps.setInt(1, departmentId);
                }
    
                rs = ps.executeQuery();
    
                while (rs.next()) {
                    JsonObject record = new JsonObject();
                    record.put("badgeid", rs.getString("badgeid"));
                    record.put("name", rs.getString("fullname"));
                    record.put("department", rs.getString("department"));
                    record.put("type", rs.getString("employeetype"));
                    badgeSummary.add(record);
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
        
        return Jsoner.serialize(badgeSummary);
    }
}

