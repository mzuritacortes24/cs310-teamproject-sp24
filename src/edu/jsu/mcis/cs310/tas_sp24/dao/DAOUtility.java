package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Badge;
import edu.jsu.mcis.cs310.tas_sp24.DailySchedule;
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
            Boolean weekend = false;                                                /*  */
            
            for(Punch p : dailypunchlist){
                
                System.out.println(p.printOriginal());
                System.out.println(p.printAdjusted());
                
                if((p.getAdjustedtimestamp().getDayOfWeek() == DayOfWeek.SUNDAY) || (p.getAdjustedtimestamp().getDayOfWeek() == DayOfWeek.SATURDAY)){   /* check to see if the punch falls on a weekend */

                    weekend = true;

                }
            
            }

            try{

                dailytotalminutes = (int) ChronoUnit.MINUTES.between(dailypunchlist.get(0).getAdjustedtimestamp(), dailypunchlist.get(dailypunchlist.size()-1).getAdjustedtimestamp());                               /* try to calculate minutes assuming both upper and lower bounds exist */

            }
            catch(Exception e){}
            
            if((!(weekend)) && (!(dailypunchlist.isEmpty()))){
                
                DailySchedule schedule = shift.getDailySchedule(dailypunchlist.get(0).getOriginaltimestamp().getDayOfWeek());
                
                
                if(dailypunchlist.size() > 2){   /* check for lunch break taken */

                    dailytotalminutes = (int) (dailytotalminutes - schedule.getLunchduration());                                                           /* subtract lunch break from total time */

                }

                else if(dailytotalminutes >= schedule.getLunchthreshold()){                                                                                /* check if lunch threshold has been reached */

                    dailytotalminutes = (int) (dailytotalminutes - schedule.getLunchduration());                                                           /* subtract lunch break from total time */

                }
                
            }
            
            System.out.println(dailytotalminutes);
            
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

    public static BigDecimal calculateAbsenteeism(ArrayList<Punch> punchList, Shift shift) {
        //Formula: A% = (Schedule − Worked  / Schedule) × 100

        double workedMinutes = calculateTotalMinutes(punchList, shift);

        double scheduledMinutes = 0;
        
        for(int i = 1; i <= 5; i++){
            
            scheduledMinutes += ((shift.getDailySchedule(DayOfWeek.of(i)).getShiftduration()) - (shift.getDailySchedule(DayOfWeek.of(i)).getLunchduration()));
            
        }
        
        double percentage = ((scheduledMinutes - workedMinutes)/scheduledMinutes)*100;

        return BigDecimal.valueOf(percentage);
        
    }
     
}
