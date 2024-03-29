package edu.jsu.mcis.cs310.tas_sp24;

/**
 * @author blake
 */

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Shift {
    // initializing fields
    private String description;
    private final int id;
    private final DailySchedule defaultschedule;
    private final long lunchDuration, shiftDuration;
    
    // constructor that accepts a Map as argument
    public Shift(Map<String, String> shiftInfo){
        this.description = (String)shiftInfo.get("description");
        this.id = Integer.parseInt(shiftInfo.get("id"));
        
        this.defaultschedule = new DailySchedule(shiftInfo);
        
        this.lunchDuration = ChronoUnit.MINUTES.between(defaultschedule.getLunchstart(), defaultschedule.getLunchstop());
        this.shiftDuration = ChronoUnit.MINUTES.between(defaultschedule.getShiftstart(), defaultschedule.getShiftstop());
    
    }

    
    // getter methods for all instance fields
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public int getRoundInterval() {
        return defaultschedule.getRoundnterval();
    }

    public int getGracePeriod() {
        return defaultschedule.getGraceperiod();
    }

    public int getDockPenalty() {
        return defaultschedule.getDockpenalty();
    }

    public int getLunchThreshold() {
        return defaultschedule.getLunchthreshold();
    }

    public LocalTime getShiftStart() {
        return defaultschedule.getShiftstart();
    }

    public LocalTime getShiftStop() {
        return defaultschedule.getShiftstop();
    }

    public LocalTime getLunchStart() {
        return defaultschedule.getLunchstart();
    }

    public LocalTime getLunchStop() {
        return defaultschedule.getLunchstop();
    }
    
    public DailySchedule getDefaultSchedule() {
        return defaultschedule;
    }

    public long getLunchDuration() {
        return lunchDuration;
    }

    public long getShiftDuration() {
        return shiftDuration;
    }

    // override toString() method
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, defaultschedule.getShiftstart(), defaultschedule.getShiftstop(), shiftDuration, defaultschedule.getLunchstart(), defaultschedule.getLunchstop(), lunchDuration);
    }
    
}
