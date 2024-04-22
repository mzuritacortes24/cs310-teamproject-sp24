package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

/**
 * DailySchedule datatype which holds the schedule info for a single shift instance
 * @author samkb
 */
public class DailySchedule {
    
    private final LocalTime shiftstart;
    private final LocalTime shiftstop;
    private final LocalTime lunchstart;
    private final LocalTime lunchstop;
    private final int roundinterval;
    private final int graceperiod;
    private final int dockpenalty;
    private final int lunchthreshold;
    private final long lunchduration;
    private final long shiftduration;
    
    /**
     *
     * @param scheduleInfo A Map containing Strings with schedule info
     */
    public DailySchedule(Map<String, String> scheduleInfo) {
        
        this.shiftstart = LocalTime.parse(scheduleInfo.get("shiftstart"));
        this.shiftstop = LocalTime.parse(scheduleInfo.get("shiftstop"));
        this.lunchstart = LocalTime.parse(scheduleInfo.get("lunchstart"));
        this.lunchstop = LocalTime.parse(scheduleInfo.get("lunchstop"));
        this.roundinterval = Integer.parseInt(scheduleInfo.get("roundinterval"));
        this.graceperiod = Integer.parseInt(scheduleInfo.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(scheduleInfo.get("dockpenalty"));
        this.lunchthreshold = Integer.parseInt(scheduleInfo.get("lunchthreshold"));
        
        this.lunchduration = ChronoUnit.MINUTES.between(lunchstart, lunchstop);
        this.shiftduration = ChronoUnit.MINUTES.between(shiftstart, shiftstop);
        
    }

    /**
     * Getter for the shiftstart class variable
     * @return shiftstart
     */
    public LocalTime getShiftstart() {
        return shiftstart;
    }

    /**
     * Getter for the shiftstop class variable
     * @return shiftstop
     */
    public LocalTime getShiftstop() {
        return shiftstop;
    }

    /**
     * Getter for the lunchstart class variable
     * @return lunchstart
     */
    public LocalTime getLunchstart() {
        return lunchstart;
    }

    /**
     * Getter for the lunchstop class variable
     * @return lunchstop
     */
    public LocalTime getLunchstop() {
        return lunchstop;
    }

    /**
     * Getter for the roundinterval class variable
     * @return roundinterval
     */
    public int getRoundinterval() {
        return roundinterval;
    }

    /**
     * Getter for the graceperiod class variable
     * @return graceperiod
     */
    public int getGraceperiod() {
        return graceperiod;
    }

    /**
     * Getter for the dockpenalty class variable
     * @return dockpenalty
     */
    public int getDockpenalty() {
        return dockpenalty;
    }

    /**
     * Getter for the lunchthreshold class variable
     * @return lunchthreshold
     */
    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
    /**
     * Getter for the lunchduration class variable
     * @return lunchduration
     */
    public long getLunchduration() {
        return lunchduration;
    }
    
    /**
     * Getter for the shiftduration class variable
     * @return shiftduration
     */
    public long getShiftduration() {
        return shiftduration;
    }
}
