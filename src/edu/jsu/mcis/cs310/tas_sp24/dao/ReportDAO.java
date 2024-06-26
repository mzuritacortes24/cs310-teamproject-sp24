package edu.jsu.mcis.cs310.tas_sp24.dao;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import edu.jsu.mcis.cs310.tas_sp24.Employee;
import edu.jsu.mcis.cs310.tas_sp24.EmployeeType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * ReportDAO class to create reports for the user to view the data from the database
 * @author samkb
 */
public class ReportDAO {

    private static final String QUERY_BADGE_SUMMARY_FOR_A_DEPARTMENT = 
        "SELECT b.id AS badgeid, " +
        "CONCAT(e.lastname, ', ', e.firstname, IFNULL(CONCAT(' ', e.middlename), '')) AS fullname, " +
        "d.description AS department, " +
        "et.description AS employeetype " +
        "FROM employee e " +
        "INNER JOIN badge b ON e.badgeid = b.id " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN employeetype et ON e.employeetypeid = et.id " +
        "WHERE e.departmentid = ? " + 
        "ORDER BY e.lastname, e.firstname, e.middlename";
    private static final String QUERY_BADGE_SUMMARY_FOR_ALL_DEPARTMENTS = 
        "SELECT b.id AS badgeid, " +
        "CONCAT(e.lastname, ', ', e.firstname, IFNULL(CONCAT(' ', e.middlename), '')) AS fullname, " +
        "d.description AS department, " +
        "et.description AS employeetype " +
        "FROM employee e " +
        "INNER JOIN badge b ON e.badgeid = b.id " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN employeetype et ON e.employeetypeid = et.id " +
        "ORDER BY e.lastname, e.firstname, e.middlename";
    private static final String QUERY_FIND2 = 
        "SELECT * " +
        "FROM(SELECT " +
        "CASE " +
        "    WHEN (TIME(evt.timestamp) < ?) " +
        "    THEN CONCAT_WS(' ', UPPER(DATE_FORMAT(evt.timestamp, '%a')), " +
        "    DATE_FORMAT(evt.timestamp, '%m/%d/%Y'), " +
        "    DATE_FORMAT(evt.timestamp, '%H:%i:%s')) " +
        "    ELSE NULL " +
        "END AS arrived, " +
        "et.description AS employeetype, " +
        "e.firstname AS firstname, " +
        "e.badgeid AS badgeid, " +
        "s.description AS shift, " +
        "e.lastname AS lastname, " +
        "CASE " +
        "    WHEN evt.eventtypeid = 1 THEN 'In' " +
        "    ELSE 'Out' " +
        "END AS status " +
        "FROM employee e " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN event evt ON e.badgeid = evt.badgeid " +
        "INNER JOIN employeetype et ON e.employeetypeid = et.id " +
        "INNER JOIN shift s ON e.shiftid = s.id " +
        "INNER JOIN eventtype ett ON evt.eventtypeid = ett.id " +
        "WHERE DATE(evt.timestamp) = ? "; 
 
    
    private static final String QUERY_GET_PUNCHES_FOR_EMPLOYEETYPE_AND_DEPARTMENT = "SELECT * FROM event INNER JOIN employee ON event.badgeid = employee.badgeid \n" +
                                                                                    "                    INNER JOIN (SELECT shift.description AS shiftdescription, shift.id AS sid FROM shift) AS s ON employee.shiftid = s.sid \n" +
                                                                                    "                    INNER JOIN (SELECT department.description AS departmentdescription, department.id AS did FROM department) AS d ON employee.departmentid = d.did\n" +
                                                                                    "                    INNER JOIN (SELECT employeetype.description AS employeetypedescription, employeetype.id AS eid FROM employeetype) AS e ON employee.employeetypeid = e.eid\n" +
                                                                                    "WHERE ((employee.departmentid = ?) AND (timestamp >= ? AND timestamp <= ?) AND (employee.employeetypeid = ?))" +
                                                                                    "ORDER BY lastname, firstname, middlename";
    
    private static final String QUERY_GET_PUNCHES_FOR_DEPARTMENT = "SELECT * FROM event INNER JOIN employee ON event.badgeid = employee.badgeid \n" +
                                                                   "                    INNER JOIN (SELECT shift.description AS shiftdescription, shift.id AS sid FROM shift) AS s ON employee.shiftid = s.sid \n" +
                                                                   "                    INNER JOIN (SELECT department.description AS departmentdescription, department.id AS did FROM department) AS d ON employee.departmentid = d.did\n" +
                                                                   "                    INNER JOIN (SELECT employeetype.description AS employeetypedescription, employeetype.id AS eid FROM employeetype) AS e ON employee.employeetypeid = e.eid\n" +
                                                                   "WHERE ((employee.departmentid = ?) AND (timestamp >= ? AND timestamp <= ?))" +
                                                                   "ORDER BY lastname, firstname, middlename";
    
    private static final String QUERY_GET_PUNCHES_FOR_EMPLOYEETYPE = "SELECT * FROM event INNER JOIN employee ON event.badgeid = employee.badgeid \n" +
                                                                     "                    INNER JOIN (SELECT shift.description AS shiftdescription, shift.id AS sid FROM shift) AS s ON employee.shiftid = s.sid \n" +
                                                                     "                    INNER JOIN (SELECT department.description AS departmentdescription, department.id AS did FROM department) AS d ON employee.departmentid = d.did\n" +
                                                                     "                    INNER JOIN (SELECT employeetype.description AS employeetypedescription, employeetype.id AS eid FROM employeetype) AS e ON employee.employeetypeid = e.eid\n" +
                                                                     "WHERE ((timestamp >= ? AND timestamp <= ?) AND (employee.employeetypeid = ?))" +
                                                                     "ORDER BY lastname, firstname, middlename";
    
    private static final String QUERY_GET_PUNCHES_FOR_EVERYONE = "SELECT * FROM event INNER JOIN employee ON event.badgeid = employee.badgeid \n" +
                                                                 "                    INNER JOIN (SELECT shift.description AS shiftdescription, shift.id AS sid FROM shift) AS s ON employee.shiftid = s.sid \n" +
                                                                 "                    INNER JOIN (SELECT department.description AS departmentdescription, department.id AS did FROM department) AS d ON employee.departmentid = d.did\n" +
                                                                 "                    INNER JOIN (SELECT employeetype.description AS employeetypedescription, employeetype.id AS eid FROM employeetype) AS e ON employee.employeetypeid = e.eid\n" +
                                                                 "WHERE (timestamp >= ? AND timestamp <= ?)" + 
                                                                 "ORDER BY lastname, firstname, middlename";
    
    private static final String QUERY_EMPLOYEE_SUMMARY_FOR_A_DEPARTMENT =
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
        "INNER JOIN shift s ON e.shiftid = s.id " +
        "WHERE d.id = ? " +
        "ORDER BY d.id DESC,e.firstname,e.lastname, e.middlename";
    
    private static final String QUERY_EMPLOYEE_SUMMARY_FOR_ALL_DEPARTMENTS =
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
        "INNER JOIN shift s ON e.shiftid = s.id " +
        "ORDER BY d.id DESC,e.firstname,e.lastname, e.middlename";
    
    private static final String QUERY_FOR_ABSENTEEISM_HISTORY =
        "SELECT * " +
        "FROM( " +
        "SELECT  e.badgeid AS badgeid, " +
        "CONCAT(e.lastname, ', ', e.firstname, IFNULL(CONCAT(' ', e.middlename), '')) AS name, " +
        "d.description AS department, " +
        "DATE_SUB(DATE(evt.timestamp), INTERVAL (DAYOFWEEK(DATE(evt.timestamp)) - 1) DAY) AS date " +
        "FROM employee e " +
        "INNER JOIN department d ON e.departmentid = d.id " +
        "INNER JOIN event evt ON e.badgeid = evt.badgeid WHERE e.id = ?) as absenteesim " +
        "WHERE(date >= '2018-08-05') " +
        "GROUP BY date;";
                                                
    
    private final DAOFactory daoFactory;

    /**
     *
     * @param daoFactory A connection to the database
     */
    public ReportDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    /**
     * A get method to create a report of all employees in a specific department
     * @param departmentId The department whose badges should be retrieved
     * @return
     */
    public String getBadgeSummary(Integer departmentId) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray badgeSummary = new JsonArray();
    
        try {
            Connection conn = daoFactory.getConnection();
    
            if (conn.isValid(0)) {
                    
                if (departmentId != null) {
                    ps = conn.prepareStatement(QUERY_BADGE_SUMMARY_FOR_A_DEPARTMENT);
                    ps.setInt(1, departmentId);
                }
                else if(departmentId == null)   {
                    ps = conn.prepareStatement(QUERY_BADGE_SUMMARY_FOR_ALL_DEPARTMENTS);
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
    
    /**
     * A get method to create a report of who is currently clocked in and who is not clocked in for a specific department
     * @param dateTime The time in which to check for who is in and who is out
     * @param departmentId The department to whose employees are to be included in the report
     * @return
     */
    public String getWhosInWhosOut(LocalDateTime dateTime, Integer departmentId)   {

        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray reportData = new JsonArray();

        try {
            
            Connection conn = daoFactory.getConnection();
  
            
            if (conn.isValid(0))   {
                
                StringBuilder QUERY = new StringBuilder(QUERY_FIND2);
                if (departmentId != null) {
                    QUERY.append("AND d.id = ?) AS derived ");
                }
                else {
                    QUERY.append(") AS derived ");
                }
                QUERY.append("WHERE (arrived IS NOT NULL) OR (status = 'Out' AND arrived IS NULL) " +
                             "ORDER BY status = 'In' DESC , employeetype , lastname , firstname;");
                
                ps = conn.prepareStatement(QUERY.toString());
                
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
    
    /**
     * A get method to create a report of the hours worked by employees in a specific department or of a specific type
     * @param date The date of the pay period in which the hours occurred
     * @param departmentid The department whose employees should be included
     * @param type The type of employees who should be included
     * @return
     */
    public String getHoursSummary(LocalDate date, Integer departmentid, EmployeeType type){
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray hourSummary = new JsonArray();
        List<String> badgenum = new ArrayList();
        
        LocalDate begin = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));

        try {
            
            Connection conn = daoFactory.getConnection();
            PunchDAO punchDAO = daoFactory.getPunchDAO();
            BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
            ShiftDAO shiftDAO = daoFactory.getShiftDAO();
            
            if (conn.isValid(0)){
                
                if(departmentid != null && type != null){
                    
                    ps = conn.prepareStatement(QUERY_GET_PUNCHES_FOR_EMPLOYEETYPE_AND_DEPARTMENT);
                    ps.setInt(1, departmentid);
                    ps.setDate(2, java.sql.Date.valueOf(begin));
                    ps.setDate(3, java.sql.Date.valueOf(end));
                    ps.setInt(4, type.ordinal());
                        
                }
                
                else if(departmentid != null){
                    
                    ps = conn.prepareStatement(QUERY_GET_PUNCHES_FOR_DEPARTMENT);
                    ps.setInt(1, departmentid);
                    ps.setDate(2, java.sql.Date.valueOf(begin));
                    ps.setDate(3, java.sql.Date.valueOf(end));
                    
                }
                
                else if (type != null){
                    
                    ps = conn.prepareStatement(QUERY_GET_PUNCHES_FOR_EMPLOYEETYPE);
                    ps.setDate(1, java.sql.Date.valueOf(begin));
                    ps.setDate(2, java.sql.Date.valueOf(end));
                    ps.setInt(3, type.ordinal());
                    
                }
                
                else{
                    
                    ps = conn.prepareStatement(QUERY_GET_PUNCHES_FOR_EVERYONE);
                    ps.setDate(1, java.sql.Date.valueOf(begin));
                    ps.setDate(2, java.sql.Date.valueOf(end));
                    
                }
                
                rs = ps.executeQuery();
                        
                while(rs.next()){
                            
                    if(!(badgenum.contains(rs.getString("badgeid")))){
                            
                        JsonObject summary = new JsonObject();

                        summary.put("employeetype", rs.getString("employeetypedescription"));
                        summary.put("shift", rs.getString("shiftdescription"));
                        summary.put("name", (rs.getString("lastname")+", "+rs.getString("firstname")+" "+rs.getString("middlename")));
                        summary.put("middlename", rs.getString("middlename"));

                        ArrayList<Punch> pl = punchDAO.list(badgeDAO.find(rs.getString("badgeid")), begin, end);
                        Shift s = shiftDAO.find(badgeDAO.find(rs.getString("badgeid")), date);

                        for (Punch p : pl) {

                            p.adjust(s);

                        }

                        BigDecimal totalminutes = BigDecimal.valueOf(DAOUtility.calculateTotalMinutes(pl, s));
                        BigDecimal overtime = BigDecimal.valueOf(0);
                        System.out.println(totalminutes);
                        BigDecimal regular = totalminutes.divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);
                        
                        BigDecimal regularhours = BigDecimal.valueOf((s.getShiftDuration()/60)*5);
                        
                        System.out.println(regular);
                        
                        if(regular.compareTo(regularhours) == 1){
                            
                            overtime = regular.subtract(regularhours);
                            regular = regularhours;
                            
                        }
                        
                        regular = regular.setScale(2);
                        overtime = overtime.setScale(2);

                        summary.put("overtime", overtime.toString());
                        summary.put("department", rs.getString("departmentdescription"));
                        summary.put("regular", regular.toString());

                        summary.put("lastname", rs.getString("lastname"));

                        hourSummary.add(summary);
                                
                        badgenum.add(rs.getString("badgeid"));
                                
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
        
        return Jsoner.serialize(hourSummary);
        
    }
    
    /**
     * A get method to create a report of a specific departments employees
     * @param departmentId The department whose employee should be included
     * @return
     */
    public String getEmployeeSummary(Integer departmentId)   {
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonArray reportData = new JsonArray(); 
        
        try {
            Connection conn = daoFactory.getConnection();
    
            if (conn.isValid(0)) {
     
                if (departmentId != null) { 
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_SUMMARY_FOR_A_DEPARTMENT);
                    ps.setInt(1, departmentId);
                }
                else if (departmentId == null)  {
                    ps = conn.prepareStatement(QUERY_EMPLOYEE_SUMMARY_FOR_ALL_DEPARTMENTS);
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
    
    /**
     * A get method that should create a report of the history of a specific employee's absenteeism
     * @param employeeId The employee whose absenteeism should be included
     * @return
     */
     public String getAbsenteeismHistory(Integer employeeId)   {
        PreparedStatement ps = null;
        ResultSet rs = null;
        JsonObject absenteeismHistory = new JsonObject();
        JsonArray dataArray = new JsonArray();
        String name = null, badgeid = null, department = null;
        ArrayList<Double> lifeTime = new ArrayList();
        
        Employee employee = daoFactory.getEmployeeDAO().find(employeeId);
        Shift shift = employee.getShift();
        
        try {
            Connection conn = daoFactory.getConnection();
    
            if (conn.isValid(0)) {
                ps = conn.prepareStatement(QUERY_FOR_ABSENTEEISM_HISTORY);
                ps.setInt(1, employeeId);
                
                rs = ps.executeQuery();
    
                
                while (rs.next()) {
                    
                    JsonObject dataObject = new JsonObject();
                    LocalDate date = LocalDate.parse(rs.getString("date"));
                    LocalDate begin = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
                    LocalDate end = begin.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
                    ArrayList<Punch> punchList = daoFactory.getPunchDAO().list(employee.getBadge(), begin, end);
                    for (Punch p : punchList) {
                        p.adjust(shift);
                    }
                    BigDecimal percentage = DAOUtility.calculateAbsenteeism(punchList, shift);
                    
                    
                    double lifetimePercentage = percentage.doubleValue();
                    String formatLifetime = String.format("%.2f", lifetimePercentage);
                    
                    lifeTime.add(lifetimePercentage);
                    int i = 0;
                    double value = 0.0;
                    for(double num : lifeTime)   {
                        ++i;
                        value += num;
                    }
                    
                    value /= i;
                    String formatValue = String.format( "%.2f", value);

                    name = rs.getString("name");
                    badgeid = rs.getString("badgeid");
                    department = rs.getString("department");
                    
                    dataObject.put("payperiod", begin.toString());
                    dataObject.put("percentage", formatLifetime);
                    dataObject.put("lifetime", formatValue);
                    
                    dataArray.add(dataObject);
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
        absenteeismHistory.put("badgeid", badgeid);
        absenteeismHistory.put("absenteeismhistory", dataArray.reversed());
        System.out.print(Jsoner.prettyPrint(Jsoner.serialize(absenteeismHistory)));
        absenteeismHistory.put("name", name);
        absenteeismHistory.put("department", department);
        return Jsoner.serialize(absenteeismHistory);
    }

}

