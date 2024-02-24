package edu.jsu.mcis.cs310.tas_sp24;
          
import edu.jsu.mcis.cs310.tas_sp24.dao.*;
import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
          
public class Main {
                
    public static void main(String[] args) {
          
        // test database connectivity; get DAOs
          
        DAOFactory daoFactory = new DAOFactory("tas.jdbc");
        BadgeDAO badgeDAO = daoFactory.getBadgeDAO();
        ShiftDAO shiftDAO = daoFactory.getShiftDAO();
      
        // find badge
          
        Badge b = badgeDAO.find("C4F37EFF");
          
        // output should be "Test Badge: #C4F37EFF (Welch, Travis C)"
          
        System.err.println("Test Badge: " + b.toString());

        
        // find shift
        
        // test finding shift by ID
        Shift shiftById = shiftDAO.find(1);
        if (shiftById != null) {
            System.out.println("Shift found by ID:" + shiftById);
        } else {
            System.out.println("Shift not foudn by ID");
        }
        
        // test finding shift by badge
        Shift shiftByBadge = shiftDAO.find(b);
        if (shiftByBadge != null) {
            System.out.println("Shift found by badge:" + shiftByBadge);
        } else {
            System.out.println("Shift not found by badge");
        }
    }

}
// DENVER COMMIT
// MAURICIO COMMIT
// SAM COMMIT
// BLAKE COMMIT
// WILLIAM COMMIT
// MERDAN COMMIT 
