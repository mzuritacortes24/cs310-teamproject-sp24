package edu.jsu.mcis.cs310.tas_sp24.dao;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


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
    private static final String QUERY_FIND2 = "SELECT \n" +
                                            "    *\n" +
                                            "FROM\n" +
                                            "    (SELECT \n" +
                                            "        CASE\n" +
                                            "	WHEN (TIME(evt.timestamp) < ?)\n" +
                                            "	     THEN CONCAT_WS(' ', UPPER(DATE_FORMAT(evt.timestamp, '%a')), \n" +
                                            "	     DATE_FORMAT(evt.timestamp, '%m/%d/%Y'), \n" +
                                            "	     DATE_FORMAT(evt.timestamp, '%H:%i:%s'))\n" +
                                            "	     ELSE NULL\n" +
                                            "         END AS arrived,\n" +
                                            "         et.description AS employeetype,\n" +
                                            "         e.firstname AS firstname,\n" +
                                            "         e.badgeid AS badgeid,\n" +
                                            "         s.description AS shift,\n" +
                                            "         e.lastname AS lastname,\n" +
                                            "         CASE\n" +
                                            "             WHEN evt.eventtypeid = 1 THEN 'In'\n" +
                                            "             ELSE 'Out'\n" +
                                            "         END AS status\n" +
                                            "    FROM\n" +
                                            "        employee e\n" +
                                            "    INNER JOIN department d ON e.departmentid = d.id\n" +
                                            "    INNER JOIN event evt ON e.badgeid = evt.badgeid\n" +
                                            "    INNER JOIN employeetype et ON e.employeetypeid = et.id\n" +
                                            "    INNER JOIN shift s ON e.shiftid = s.id\n" +
                                            "    INNER JOIN eventtype ett ON evt.eventtypeid = ett.id\n" +
                                            "    WHERE\n" +
                                            "        DATE(evt.timestamp) = ? \n" +
                                            "        AND d.id = ?) \n" +
                                            "        AS derived\n" +
                                            "WHERE\n" +
                                            "    (arrived IS NOT NULL)\n" +
                                            "     OR (status = 'Out' AND arrived IS NULL)\n" +
                                            "ORDER BY status = 'In' DESC , employeetype , lastname , firstname;"; 
    private static final String QUERY_FIND4 =
        "SELECT e.firstname AS firstname, " +
        "et.description AS employeetype, " +
        "e.badgeid AS badgeid, " +
        "s.description AS shift, " +
        "e.middlename AS middlename, " +
        "DATE_FORMAT(e.active, '%m/%d/%Y') AS active, " +
        "d.description AS department, " +
        "e.lastname AS lastname " +
        "FROM employee e " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN employeetype et ON e.employeetypeid = et.id " +
        "INNER JOIN shift s ON e.shiftid = s.id ";
                                                

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
    
    public String getWhosInWhosOut(LocalDateTime dateTime, Integer departmentId)   {

        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray reportData = new JsonArray();

        try {
            
            Connection conn = daoFactory.getConnection();
  
            
            if (conn.isValid(0))   {
           
                ps = conn.prepareStatement(QUERY_FIND2);
                LocalDate date = dateTime.toLocalDate();
                LocalTime time = dateTime.toLocalTime();
                ps.setTime(1, java.sql.Time.valueOf(time));
                ps.setDate(2, java.sql.Date.valueOf(date));
                if (departmentId != null)   {
                   ps.setInt(3, departmentId); 
                }

                rs = ps.executeQuery();
                
                while (rs.next()) {
                    JsonObject record = new JsonObject();
                    if (rs.getString("arrived") != null)   {
                        record.put("arrived", rs.getString("arrived"));
                    }
                    record.put("employeetype", rs.getString("employeeType"));
                    record.put("firstname", rs.getString("firstname")); 
                    record.put("badgeid", rs.getString("badgeId"));
                    record.put("shift", rs.getString("shift"));
                    record.put("lastname", rs.getString("lastname")); 
                    record.put("status", rs.getString("status"));
                    reportData.add(record);
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
        System.out.print(Jsoner.prettyPrint(Jsoner.serialize(reportData)));
        return Jsoner.serialize(reportData);
        
    }
    
    public JsonArray getHoursSummary(LocalDate date, int departmentid, EmployeeType type){
        
        return new JsonArray();
        
    }
    
    public String getEmployeeSummary(Integer departmentId)   {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray reportData = new JsonArray(); 
        
        try {
            Connection conn = daoFactory.getConnection();
    
            if (conn.isValid(0)) {
           
                StringBuilder QUERY = new StringBuilder(QUERY_FIND4);
                    
                if (departmentId != null) {
                    QUERY.append("WHERE d.id = ? ");
                }
    
                QUERY.append("ORDER BY d.id DESC,e.firstname,e.lastname, e.middlename");
    
                ps = conn.prepareStatement(QUERY.toString());
    
                if (departmentId != null) {
                    ps.setInt(1, departmentId);
                }
    
                rs = ps.executeQuery();
    
                while (rs.next()) {
                    JsonObject record = new JsonObject();
                    record.put("firstname", rs.getString("firstname"));
                    record.put("employeetype", rs.getString("employeetype"));
                    record.put("badgeid",rs.getString("badgeid") );
                    record.put("shift", rs.getString("shift"));
                    record.put("middlename", rs.getString("middlename"));
                    record.put("active",rs.getString("active") );
                    record.put("department",rs.getString("department") );
                    record.put("lastname", rs.getString("lastname"));
                    reportData.add(record);
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
        return Jsoner.serialize(reportData);
    }
}

