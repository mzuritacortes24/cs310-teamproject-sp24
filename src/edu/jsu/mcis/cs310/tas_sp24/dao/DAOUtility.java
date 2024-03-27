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
        
        try{
            
            if((lunch_out != null) && (lunch_in == null)){
                
                totalminutes = (int) ChronoUnit.MINUTES.between(clock_in, lunch_out);                           /* try to calculate minutes assuming both upper and lower bounds exist */
                
            }
            
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
        
        //ArrayList for each day of the week
        ArrayList<Punch> sunday = new ArrayList<>();
        ArrayList<Punch> monday = new ArrayList<>();
        ArrayList<Punch> tuesday = new ArrayList<>();
        ArrayList<Punch> wednesday= new ArrayList<>();
        ArrayList<Punch> thursday = new ArrayList<>();
        ArrayList<Punch> friday = new ArrayList<>();
        ArrayList<Punch> saturday = new ArrayList<>();
  
        // Variables for how many minutes per day
        double SUNDAY = 0.0, MONDAY = 0.0, TUESDAY = 0.0, WEDNESDAY = 0.0, THURSDAY = 0.0, FRIDAY = 0.0, SATURDAY = 0.0;
        ArrayList<Double> week = new ArrayList<>();
        LinkedHashMap<String, Double> weekday = new LinkedHashMap<>();
        
        // Seperates punchList into each day
        int i = 0;
        for (Punch punch : punchList) {
            System.out.println(punch.printAdjusted());
            if (null == punch.getAdjustedtimestamp().getDayOfWeek())  {
                System.out.println("No Shift");
            }
            else switch (punch.getAdjustedtimestamp().getDayOfWeek())  {
                case SUNDAY -> sunday.add(punchList.get(i));
                case MONDAY -> monday.add(punchList.get(i));
                case TUESDAY -> tuesday.add(punchList.get(i));
                case WEDNESDAY -> wednesday.add(punchList.get(i));
                case THURSDAY -> thursday.add(punchList.get(i));
                case FRIDAY -> friday.add(punchList.get(i));
                case SATURDAY -> saturday.add(punchList.get(i));
                default -> System.out.println("No Shift");
            }
            ++i;
        }
        
        // Calculates the minutes per day for each arraylist
        i = 0;
        while (i < 7)   {
            switch(i)   {
                case 0 -> {
                    SUNDAY = calculateTotalMinutes(sunday, shift);
                    weekday.put("SUNDAY", SUNDAY);  
                    week.add(0, SUNDAY);
                }
                case 1 -> {
                    MONDAY = calculateTotalMinutes(monday, shift);                
                    weekday.put("MONDAY", MONDAY);
                    week.add(1, MONDAY);
                }
                case 2 -> {
                    TUESDAY = calculateTotalMinutes(tuesday, shift);
                    weekday.put("TUESDAY", TUESDAY);        
                    week.add(2, TUESDAY);
                }
                case 3 -> {
                    WEDNESDAY = calculateTotalMinutes(wednesday, shift);                
                    weekday.put("WEDNESDAY", WEDNESDAY);
                    week.add(3, WEDNESDAY);
                }
                case 4 -> {        
                    THURSDAY = calculateTotalMinutes(thursday, shift);
                    weekday.put("THURSDAY", THURSDAY);
                    week.add(4, THURSDAY);
                }
                case 5 -> {
                    FRIDAY = calculateTotalMinutes(friday, shift);
                    weekday.put("FRIDAY", FRIDAY);
                    week.add(5, FRIDAY);
                }
                case 6 -> {
                    SATURDAY = calculateTotalMinutes(saturday, shift);
                    weekday.put("SATURDAY", SATURDAY);
                    week.add(6, SATURDAY);
                }   
                default -> System.out.println("No days!!");
            }
            ++i;
        }
        
        // Adds all the minutes of each day
        double totalMinutes = 0.0;
        for (double value  : week)   {
            if (value != 0.0)   {
                totalMinutes += value;
            }
            else {
                
            }
        }
        
        System.out.println(weekday.toString());
        double scheduledMinutes = (double) (shift.getShiftDuration() - shift.getLunchDuration());    

        System.out.println("Total Minutes worked: "+ (totalMinutes));
        System.out.println("Minutes Scehduled: " + (scheduledMinutes * 5));
        System.out.println("Difference: " + ((scheduledMinutes * 5)- totalMinutes));
        System.out.println((((scheduledMinutes * 5) - totalMinutes) / 2400 ) * 100);

        BigDecimal hundred = new BigDecimal(100);
        BigDecimal minutesWorked = new BigDecimal(totalMinutes);
        BigDecimal minutesScheduled = new BigDecimal(scheduledMinutes * 5);
        BigDecimal difference = minutesScheduled.subtract(minutesWorked);


        BigDecimal absenteeismPercentage = difference
                .divide(minutesScheduled, 2, RoundingMode.HALF_UP)
                .multiply(hundred);

        return absenteeismPercentage.setScale(4, RoundingMode.HALF_UP);
    }
     
}
