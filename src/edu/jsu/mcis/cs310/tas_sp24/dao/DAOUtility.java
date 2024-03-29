package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.EventType;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
                                         
/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {
    
    public static int calculateTotalMinutes(ArrayList<Punch> punchlist, Shift shift){
        
        int totalminutes = 0;
        int firstday = punchlist.get(0).getAdjustedtimestamp().getDayOfMonth();
        int lastday = punchlist.get(punchlist.size()-1).getAdjustedtimestamp().getDayOfMonth();
        int currentday = firstday;
        
        while(!(currentday > lastday)){
        
            ArrayList<Punch> dailypunchlist = new ArrayList();
            
            for(Punch punch : punchlist){
                
                if(punch.getOriginaltimestamp().getDayOfMonth() == currentday){
                
                    dailypunchlist.add(punch);
                    
                }
            
            }
            
            int dailytotalminutes = 0;                                              /* initialize variables to hold punches */
            LocalDateTime clock_in = null;                                          /*  */
            LocalDateTime clock_out = null;                                         /*  */
            LocalDateTime lunch_in = null;                                          /*  */
            LocalDateTime lunch_out = null;                                         /*  */
            LocalDateTime time_out = null;                                          /*  */
            Boolean weekend = false;                                                /*  */

            for(Punch p : dailypunchlist){
                
                System.out.println("Lunch subtract="+shift.getLunchThreshold());
                System.out.println(p.getAdjustmenttype());
                System.out.println(p.printOriginal());
                System.out.println(p.printAdjusted());
                System.out.println(shift);
                
                if((p.getAdjustedtimestamp().getDayOfWeek() == DayOfWeek.SUNDAY) || (p.getAdjustedtimestamp().getDayOfWeek() == DayOfWeek.SATURDAY)){   /* check to see if the punch falls on a weekend */

                    weekend = true;

                }
            
                switch (p.getAdjustmenttype()){                                     /* sort punches based on adjustment type and event type */

                    case NONE -> {

                        switch (p.getPunchtype()) {

                            case CLOCK_OUT -> {

                                clock_out = p.getAdjustedtimestamp();

                            }

                            case CLOCK_IN -> {

                                clock_in = p.getAdjustedtimestamp();

                            }

                            case TIME_OUT -> {

                                time_out = p.getAdjustedtimestamp();

                            }

                            default -> {

                                throw new AssertionError(p.getPunchtype().name());

                            }

                        }

                    }

                    case SHIFT_START -> {

                        clock_in = p.getAdjustedtimestamp();

                    }

                    case SHIFT_STOP -> {

                        clock_out = p.getAdjustedtimestamp();

                    }

                    case SHIFT_DOCK -> {

                        if(p.getPunchtype() == EventType.CLOCK_OUT){

                            clock_out = p.getAdjustedtimestamp();

                        }

                        else if(p.getPunchtype() == EventType.CLOCK_IN){

                            clock_in = p.getAdjustedtimestamp();

                        }

                    }

                    case LUNCH_START -> {

                        lunch_out = p.getAdjustedtimestamp();

                    }

                    case LUNCH_STOP -> {

                        lunch_in = p.getAdjustedtimestamp();

                    }

                    case INTERVAL_ROUND -> {

                        if(p.getPunchtype() == EventType.CLOCK_OUT){

                            clock_out = p.getAdjustedtimestamp();

                        }

                        else if(p.getPunchtype() == EventType.CLOCK_IN){

                            clock_in = p.getAdjustedtimestamp();

                        }

                    }

                    default -> {

                        throw new AssertionError(p.getPunchtype().name());

                    }

                }

            }

            try{

                dailytotalminutes = (int) ChronoUnit.MINUTES.between(clock_in, clock_out);                               /* try to calculate minutes assuming both upper and lower bounds exist */

            }
            catch(Exception e){}

            try{

                if((lunch_out != null) && (lunch_in == null)){

                    dailytotalminutes = (int) ChronoUnit.MINUTES.between(clock_in, lunch_out);                           /* try to calculate minutes assuming both upper and lower bounds exist */

                }

            }
            catch(Exception e){}

            if(!(weekend)){

                System.out.println("Total="+dailytotalminutes);
                
                try{

                    if((lunch_out.toLocalTime().equals(shift.getLunchStart())) && (lunch_in.toLocalTime().equals(shift.getLunchStop()))){   /* check for lunch break taken */
                        System.out.println("Lunch 1");
                        dailytotalminutes = (int) (dailytotalminutes - shift.getLunchDuration());                                                           /* subtract lunch break from total time */

                    }

                }
                catch(Exception e){

                    if(dailytotalminutes >= shift.getLunchThreshold()){                                                                                /* check if lunch threshold has been reached */
                        System.out.println("Lunch 2");
                        dailytotalminutes = (int) (dailytotalminutes - shift.getLunchDuration());                                                           /* subtract lunch break from total time */

                    }

                }
                
            }
            
            else if(lunch_out != null){
                
                dailytotalminutes = (int) (dailytotalminutes - shift.getLunchDuration());
                
            }
            
            System.out.println("Total after lunch="+dailytotalminutes);
            totalminutes += dailytotalminutes;
            currentday++;
                
        }
        
        return totalminutes;
        
    }
    
    // getPunchListAsJSON Function
    @SuppressWarnings("null")
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist)  {
        Badge badge = null;
        ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

        for (Punch punch : dailypunchlist) {
            HashMap<String, String> punchData = new HashMap<>();
            punchData.put("id", String.valueOf(badge.getId().toString()));
            punchData.put("badgeid", punch.getBadge().toString());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", punch.getPunchtype().toString());
            punchData.put("adjustmenttype", punch.getAdjustmenttype().toString());
            punchData.put("originaltimestamp", punch.getOriginaltimestamp().toString());
            punchData.put("adjustedtimestamp", punch.getAdjustedtimestamp().toString());

            jsonData.add(punchData);
        }

        return Jsoner.serialize(jsonData);
    
    }
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift shift){
        JsonObject json = new JsonObject();
        JsonArray punchArray = new JsonArray();
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        long totalMinutes = calculateTotalMinutes(punchlist, shift);
        String absenteeism = df.format(calculateAbsenteeism(punchlist, shift)) + "%";   //NOTE: calculateAbsenteeism is a placeholder variable
        
        Badge badge = null; 
        
        ArrayList<HashMap<String, String>> punchlistData = new ArrayList<>();
        
        for (Punch punch : punchlist) {
            HashMap<String, String> punchData = new HashMap<>();
            punchData.put("id", String.valueOf(badge.getId().toString()));
            punchData.put("badgeid", punch.getBadge().toString());
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", punch.getPunchtype().toString());
            punchData.put("adjustmenttype", punch.getAdjustmenttype().toString());
            punchData.put("originaltimestamp", punch.getOriginaltimestamp().toString());
            punchData.put("adjustedtimestamp", punch.getAdjustedtimestamp().toString());

            punchlistData.add(punchData);
        }
        
        json.put("absenteesim", absenteeism);
        json.put("totalminutes", String.valueOf(totalMinutes));
        json.put("punchlist", punchArray);
        
        return Jsoner.serialize(json);
    }

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        //Formula: A% = (Schedule − Worked  / Schedule) × 100

        double workedMinutes = calculateTotalMinutes(punchList, shift);

        double scheduledMinutes = (double) ((shift.getShiftDuration() - shift.getLunchDuration())*5);
        
        System.out.println("Worked "+workedMinutes);
        
        double percentage = ((scheduledMinutes - workedMinutes)/scheduledMinutes)*100;

        return BigDecimal.valueOf(percentage);
        
    }
     
}
