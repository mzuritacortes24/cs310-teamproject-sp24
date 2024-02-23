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
        description = shiftInfo.get("description");
        
        shiftStart = LocalTime.parse(shiftInfo.get("shiftstart"));
        shiftStop = LocalTime.parse(shiftInfo.get("shiftstop"));
        lunchStart = LocalTime.parse(shiftInfo.get("luinchstart"));
        lunchStop = LocalTime.parse(shiftInfo.get("lunchstop"));
        
        roundInterval = Integer.parseInt(shiftInfo.get("roundinterval"));
        gracePeriod = Integer.parseInt(shiftInfo.get("graceperiod"));
        dockPenalty = Integer.parseInt(shiftInfo.get("dockpenalty"));
        lunchThreshold = Integer.parseInt(shiftInfo.get("lunchthreshold"));
        lunchDuration = Integer.parseInt(shiftInfo.get("lunchduration"));
        shiftDuration = Integer.parseInt(shiftInfo.get("shiftduration"));
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
