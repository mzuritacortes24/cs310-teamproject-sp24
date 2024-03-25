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
import java.text.DecimalFormat;
                                         
/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
        int totalminutes = 0;                                                   /* initialize variables to hold punches */
        LocalDateTime clock_in = null;                                          /*  */
        LocalDateTime clock_out = null;                                         /*  */
        LocalDateTime lunch_in = null;                                          /*  */
        LocalDateTime lunch_out = null;                                         /*  */
        LocalDateTime time_out = null;                                          /*  */
        Boolean weekend = false;                                                /*  */
        
        for(Punch p : dailypunchlist){
            
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
            
            totalminutes = (int) ChronoUnit.MINUTES.between(clock_in, clock_out);                               /* try to calculate minutes assuming both upper and lower bounds exist */
        
        }
        catch(Exception e){}
        
        if(!(weekend)){
            
            try{

                if((lunch_out.toLocalTime().equals(shift.getLunchStart())) && (lunch_in.toLocalTime().equals(shift.getLunchStop()))){   /* check for lunch break taken */

                    totalminutes = (int) (totalminutes - shift.getLunchDuration());                                                           /* subtract lunch break from total time */

                }

            }
            catch(Exception e){
                
                if(totalminutes >= shift.getLunchThreshold()){                                                                                /* check if lunch threshold has been reached */
                    
                    totalminutes = (int) (totalminutes - shift.getLunchDuration());                                                           /* subtract lunch break from total time */
                
                }

            }
        
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
    
    public static String getPunchListPlusTotalAsJSON(ArrayList<Punch> punchlist, Shift shift){
        JsonObject json = new JsonObject();
        JsonArray punchArray = new JsonArray();
        
        DecimalFormat df = new DecimalFormat("#.00");
        
        long totalMinutes = calculateTotalMinutes(punchlist, shift);
        String absenteeism = df.format(calculateAbsenteeism(punchlist, shift)) + "%";   //NOTE: calculateAbsenteeism is a placeholder for now
        
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
     
}
