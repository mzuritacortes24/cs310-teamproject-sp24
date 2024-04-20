package edu.jsu.mcis.cs310.tas_sp24;

/**
 * @author blake
 */

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * Shift datatype that holds the data for a single shift instance
 * @author samkb
 */
public class Shift {
    // initializing fields
    private String description;
    private final int id;
    private final DailySchedule defaultschedule;
    private final HashMap<DayOfWeek, DailySchedule> weeklyschedule = new HashMap();
    
    // constructor that accepts a Map as argument

    /**
     *
     * @param shiftInfo A Map containing Strings with shift info
     */
    public Shift(Map<String, String> shiftInfo){
        this.description = (String)shiftInfo.get("description");
        this.id = Integer.parseInt(shiftInfo.get("id"));
        
        this.defaultschedule = new DailySchedule(shiftInfo);
        
        for(int i = 1; i <= 5; i++){
            
            weeklyschedule.put(DayOfWeek.of(i), defaultschedule);
            
        }
    
    }

    
    // getter methods for all instance fields

    /**
     * Getter for the id class variable
     * @return
     */
    
    public int getId() {
        return id;
    }
    
    /**
     * Getter for the description class variable
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the roundinterval class variable
     * @return
     */
    public int getRoundInterval() {
        return defaultschedule.getRoundinterval();
    }

    /**
     * Getter for the graceperiod class variable
     * @return
     */
    public int getGracePeriod() {
        return defaultschedule.getGraceperiod();
    }

    /**
     * Getter for the dockpenalty class variable
     * @return
     */
    public int getDockPenalty() {
        return defaultschedule.getDockpenalty();
    }

    /**
     * Getter for the lunchthreshold class variable
     * @return
     */
    public int getLunchThreshold() {
        return defaultschedule.getLunchthreshold();
    }

    /**
     * Getter for the shiftstart class variable
     * @return
     */
    public LocalTime getShiftStart() {
        return defaultschedule.getShiftstart();
    }

    /**
     * Getter for the shiftstop class variable
     * @return
     */
    public LocalTime getShiftStop() {
        return defaultschedule.getShiftstop();
    }

    /**
     * Getter for the lunchstart class variable
     * @return
     */
    public LocalTime getLunchStart() {
        return defaultschedule.getLunchstart();
    }

    /**
     * Getter for the lunchstop class variable
     * @return
     */
    public LocalTime getLunchStop() {
        return defaultschedule.getLunchstop();
    }
    
    /**
     * Getter for the defaultschedule class variable
     * @return
     */
    public DailySchedule getDefaultSchedule() {
        return defaultschedule;
    }
    
    /**
     * Getter for the day specific schedule class variable
     * @param dayofweek DayOfWeek object that selects the schedule to return
     * @return
     */
    public DailySchedule getDailySchedule(DayOfWeek dayofweek) {  
        return weeklyschedule.get(dayofweek);
    }

    /**
     * Getter for the lunchduration class variable
     * @return
     */
    public long getLunchDuration() {
        return defaultschedule.getLunchduration();
    }

    /**
     * Getter for the shiftduration class variable
     * @return
     */
    public long getShiftDuration() {
        return defaultschedule.getShiftduration();
    }
    
    /**
     * Setter for the day specific schedule class variable
     * @param dayofweek DayOfWeek object that selects the schedule to set
     * @param shiftInfo A Map containing Strings with shift info
     */
    public void setDailySchedule(int dayofweek, Map<String, String> shiftInfo){
        
        weeklyschedule.put(DayOfWeek.of(dayofweek), new DailySchedule(shiftInfo));
        
    }

    // override toString() method

    /**
     * toString override for the class
     * @return
     */
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, defaultschedule.getShiftstart(), defaultschedule.getShiftstop(), defaultschedule.getShiftduration(), defaultschedule.getLunchstart(), defaultschedule.getLunchstop(), defaultschedule.getLunchduration());
    }
    
}
