package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * @author mgarlyyev
 */

public class PunchListMultiFindTest {

    private DAOFactory daoFactory;

    @Before
    public void setUp() {
        daoFactory = new DAOFactory("tas.jdbc");
    }

    @Test
    public void testPunchListMultiFind1() {
        
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        StringBuilder expectedOutput = new StringBuilder();
        StringBuilder actualOutput = new StringBuilder();

        LocalDate startDate = LocalDate.of(2018, Month.SEPTEMBER, 17);
        LocalDate endDate = LocalDate.of(2018, Month.SEPTEMBER, 17);

        Badge badge = badgeDAO.find("67637925");
        
        ArrayList<Punch> expectedPunches = new ArrayList<>();


        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            expectedPunches.addAll(punchDAO.list(badge, date));
        }


        for (Punch p : expectedPunches) {
            expectedOutput.append(p.printOriginal()).append("\n");
        }

        ArrayList<Punch> actualPunches = punchDAO.list(badge, startDate, endDate);


        for (Punch p : actualPunches) {
            actualOutput.append(p.printOriginal()).append("\n");
        }

        assertEquals(expectedOutput.toString(), actualOutput.toString());
    }

    @Test
    public void testPunchListMultiFind2() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        StringBuilder expectedOutput = new StringBuilder();
        StringBuilder actualOutput = new StringBuilder();

        LocalDate startDate = LocalDate.of(2018, Month.SEPTEMBER, 27);
        LocalDate endDate = LocalDate.of(2018, Month.SEPTEMBER, 27);

        Badge badge = badgeDAO.find("87176FD7");
        ArrayList<Punch> expectedPunches = new ArrayList<>();


        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            expectedPunches.addAll(punchDAO.list(badge, date));
        }


        for (Punch p : expectedPunches) {
            expectedOutput.append(p.printOriginal()).append("\n");
        }

        ArrayList<Punch> actualPunches = punchDAO.list(badge, startDate, endDate);


        for (Punch p : actualPunches) {
            actualOutput.append(p.printOriginal()).append("\n");
        }

        assertEquals(expectedOutput.toString(), actualOutput.toString());
    }

    @Test
    public void testPunchListMultiFind3() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        StringBuilder expectedOutput = new StringBuilder();
        StringBuilder actualOutput = new StringBuilder();

        LocalDate startDate = LocalDate.of(2018, Month.SEPTEMBER, 5);
        LocalDate endDate = LocalDate.of(2018, Month.SEPTEMBER, 5);

        Badge badge = badgeDAO.find("95497F63");
        ArrayList<Punch> expectedPunches = new ArrayList<>();


        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            expectedPunches.addAll(punchDAO.list(badge, date));
        }


        for (Punch p : expectedPunches) {
            expectedOutput.append(p.printOriginal()).append("\n");
        }

        ArrayList<Punch> actualPunches = punchDAO.list(badge, startDate, endDate);


        for (Punch p : actualPunches) {
            actualOutput.append(p.printOriginal()).append("\n");
        }

        assertEquals(expectedOutput.toString(), actualOutput.toString());
    }
    
    @Test
    public void testPunchListMultiFind4() {
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        PunchDAO punchDAO = daoFactory.getPunchDAO();

        StringBuilder expectedOutput = new StringBuilder();
        StringBuilder actualOutput = new StringBuilder();

        LocalDate startDate = LocalDate.of(2018, Month.SEPTEMBER, 17);
        LocalDate endDate = LocalDate.of(2018, Month.SEPTEMBER, 20);

        Badge badge = badgeDAO.find("67637925");
        ArrayList<Punch> expectedPunches = new ArrayList<>();


        for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            expectedPunches.addAll(punchDAO.list(badge, date));
        }


        for (Punch p : expectedPunches) {
            expectedOutput.append(p.printOriginal()).append("\n");
        }

        ArrayList<Punch> actualPunches = punchDAO.list(badge, startDate, endDate);


        for (Punch p : actualPunches) {
            actualOutput.append(p.printOriginal()).append("\n");
        }

        assertEquals(expectedOutput.toString(), actualOutput.toString());
    }

}

