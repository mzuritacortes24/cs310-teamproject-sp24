package edu.jsu.mcis.cs310.tas_sp24;

/**
 * @author blake
 */

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Shift {
    // initializing fields
    private final String description;
    private final int id = 0, roundInterval, gracePeriod, dockPenalty, lunchThreshold;
    private final LocalTime shiftStart, shiftStop, lunchStart, lunchStop;
    private final long lunchDuration, shiftDuration;
    
    // constructor that accepts a Map as argument
    public Shift(Map<String, String> shiftInfo){
        description = (String)shiftInfo.get("Description");
        
        shiftStart = LocalTime.parse(shiftInfo.get("Shift Start"));
        shiftStop = LocalTime.parse(shiftInfo.get("Shift Stop"));
        lunchStart = LocalTime.parse(shiftInfo.get("Lunch Start"));
        lunchStop = LocalTime.parse(shiftInfo.get("Lunch Stop"));
        
        roundInterval = Integer.parseInt((String)shiftInfo.get("Round Interval"));
        gracePeriod = Integer.parseInt((String)shiftInfo.get("Grace Period"));
        dockPenalty = Integer.parseInt((String)shiftInfo.get("Dock Penalty"));
        lunchThreshold = Integer.parseInt((String)shiftInfo.get("Lunch Threshold"));
        
        lunchDuration = ChronoUnit.MINUTES.between(lunchStart, lunchStop);
        shiftDuration = ChronoUnit.MINUTES.between(shiftStart, shiftStop);
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
                description, shiftStart, shiftStop, shiftDuration, lunchStart, lunchStop, lunchDuration);
    }
    
}
