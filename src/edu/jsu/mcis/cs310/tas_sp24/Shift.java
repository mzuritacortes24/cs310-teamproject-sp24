package edu.jsu.mcis.cs310.tas_sp24;

/**
 * @author blake
 */

import java.time.LocalTime;
import java.util.Map;

public class Shift {
    // initializing fields
    private String description;
    private int id, roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    private int lunchDuration, shiftDuration;
    
    // constructor that accepts a Map as argument
    public Shift(Map<String, String> shiftInfo){
        description = shiftInfo.get("Description");
        
        shiftStart = LocalTime.parse(shiftInfo.get("Shift Start"));
        shiftStop = LocalTime.parse(shiftInfo.get("Shift Stop"));
        lunchStart = LocalTime.parse(shiftInfo.get("Lunch Start"));
        lunchStop = LocalTime.parse(shiftInfo.get("Lunch Stop"));
        
        roundInterval = Integer.parseInt(shiftInfo.get("Round Interval"));
        gracePeriod = Integer.parseInt(shiftInfo.get("Grace Period"));
        dockPenalty = Integer.parseInt(shiftInfo.get("Dock Penalty"));
        lunchThreshold = Integer.parseInt(shiftInfo.get("Lunch Threshold"));
        lunchDuration = Integer.parseInt(shiftInfo.get("Lunch Duration"));
        shiftDuration = Integer.parseInt(shiftInfo.get("Shift Duration"));
    }

    
    // getter methods for all instance fields
    
    public int getId() {
        return id;
    }
    
    public String getDescription() {
        return description;
    }

    public int getRoundInterval() {
        return roundInterval;
    }

    public int getGracePeriod() {
        return gracePeriod;
    }

    public int getDockPenalty() {
        return dockPenalty;
    }

    public int getLunchThreshold() {
        return lunchThreshold;
    }

    public LocalTime getShiftStart() {
        return shiftStart;
    }

    public LocalTime getShiftStop() {
        return shiftStop;
    }

    public LocalTime getLunchStart() {
        return lunchStart;
    }

    public LocalTime getLunchStop() {
        return lunchStop;
    }

    public int getLunchDuration() {
        return lunchDuration;
    }

    public int getShiftDuration() {
        return shiftDuration;
    }

    // override toString() method
    @Override
    public String toString() {
        return String.format("%s: %s - %s (%d minutes); Lunch: %s - %s (%d minutes)",
                description, shiftStart, shiftStop, shiftDuration, lunchStart, lunchStop, lunchDuration);
    }
    
}
