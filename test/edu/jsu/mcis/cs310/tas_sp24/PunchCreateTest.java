package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.*;
import static org.junit.Assert.*;

public class PunchCreateTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {

        daoFactory = new DAOFactory("tas.jdbc");

    }

    @Test
    public void testCreatePunch1() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p1 = new Punch(103, badgeDAO.find("021890C0"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginaltimestamp();
        int terminalid = p1.getTerminalid();
        EventType punchtype = p1.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p1);

        /* Retrieve New Punch */
        
        Punch p2 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p2.getBadge().getId());

        rts = p2.getOriginaltimestamp();

        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    @Test
    public void testCreatePunch2() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p3 = new Punch(103, badgeDAO.find("BD9BB983"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p3.getBadge().getId();
        ots = p3.getOriginaltimestamp();
        int terminalid = p3.getTerminalid();
        EventType punchtype = p3.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p3);

        /* Retrieve New Punch */
        
        Punch p4 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p4.getBadge().getId());

        rts = p4.getOriginaltimestamp();

        assertEquals(terminalid, p4.getTerminalid());
        assertEquals(punchtype, p4.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    @Test
    public void testCreatePunch3() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p5 = new Punch(104, badgeDAO.find("AB8204A4"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p5.getBadge().getId();
        ots = p5.getOriginaltimestamp();
        int terminalid = p5.getTerminalid();
        EventType punchtype = p5.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p5);

        /* Retrieve New Punch */
        
        Punch p6 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p6.getBadge().getId());

        rts = p6.getOriginaltimestamp();

        assertEquals(terminalid, p6.getTerminalid());
        assertEquals(punchtype, p6.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    @Test
    public void testCreatePunch4() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p7 = new Punch(0, badgeDAO.find("1C920A23"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p7.getBadge().getId();
        ots = p7.getOriginaltimestamp();
        int terminalid = p7.getTerminalid();
        EventType punchtype = p7.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p7);

        /* Retrieve New Punch */
        
        Punch p8 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p8.getBadge().getId());

        rts = p8.getOriginaltimestamp();

        assertEquals(terminalid, p8.getTerminalid());
        assertEquals(punchtype, p8.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
    @Test
    public void testCreatePunch5() {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        PunchDAO punchDAO = daoFactory.getPunchDAO();
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();

        /* Create New Punch Object */
        
        Punch p9 = new Punch(105, badgeDAO.find("398B1563"), EventType.CLOCK_IN);

        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;

        /* Get Punch Properties */
        
        String badgeid = p9.getBadge().getId();
        ots = p9.getOriginaltimestamp();
        int terminalid = p9.getTerminalid();
        EventType punchtype = p9.getPunchtype();

        /* Insert Punch Into Database */
        
        int punchid = punchDAO.create(p9);

        /* Retrieve New Punch */
        
        Punch p10 = punchDAO.find(punchid);

        /* Compare Punches */
        
        assertEquals(badgeid, p10.getBadge().getId());

        rts = p10.getOriginaltimestamp();

        assertEquals(terminalid, p10.getTerminalid());
        assertEquals(punchtype, p10.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));

    }
}
