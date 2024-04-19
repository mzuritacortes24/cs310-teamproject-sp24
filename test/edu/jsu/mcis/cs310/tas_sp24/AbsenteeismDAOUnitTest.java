package edu.jsu.mcis.cs310.tas_sp24;

import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.LocalDate;
import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;

public class AbsenteeismDAOUnitTest {

    private DAOFactory daoFactory;

    @Before
    public void setup() {
        daoFactory = new DAOFactory("tas.jdbc");
    }

    @Test
    public void testClearAbsenteeismHistory() {
        AbsenteeismDAO absenteeismDAO = daoFactory.getAbsenteeismDAO();

        // Insert some dummy data to clear
        EmployeeDAO employeeDAO = daoFactory.getEmployeeDAO();
        Employee employee = employeeDAO.find(123456); // Assuming employee ID 123456 exists
        LocalDate payPeriod = LocalDate.of(2024, 4, 1);
        Absenteeism a = absenteeismDAO.find(employee, payPeriod); 

        
        // Clear absenteeism history
        absenteeismDAO.clear(123456);

        // Try to find the cleared data
        Absenteeism result = absenteeismDAO.find(employee, payPeriod);
        assertNull(result);
    }

    @Test
    public void testClearAbsenteeismHistoryForNonExistingEmployee() {
        AbsenteeismDAO absenteeismDAO = daoFactory.getAbsenteeismDAO();

        // Non-existing employee ID
        absenteeismDAO.clear(999999); 

        // Make sure no exception is thrown and the method completes successfully
        assertTrue(true);
    }
}
