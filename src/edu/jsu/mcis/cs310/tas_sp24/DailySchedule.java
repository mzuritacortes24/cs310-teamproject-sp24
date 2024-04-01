package edu.jsu.mcis.cs310.tas_sp24;

import java.time.LocalTime;
import java.util.Map;

/**
 *
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
    
    public DailySchedule(Map<String, String> scheduleInfo) {
        
        this.shiftstart = LocalTime.parse(scheduleInfo.get("shiftstart"));
        this.shiftstop = LocalTime.parse(scheduleInfo.get("shiftstop"));
        this.lunchstart = LocalTime.parse(scheduleInfo.get("lunchstart"));
        this.lunchstop = LocalTime.parse(scheduleInfo.get("lunchstop"));
        this.roundinterval = Integer.parseInt(scheduleInfo.get("roundinterval"));
        this.graceperiod = Integer.parseInt(scheduleInfo.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(scheduleInfo.get("dockpenalty"));
        this.lunchthreshold = Integer.parseInt(scheduleInfo.get("lunchthreshold"));
        
    }

    public LocalTime getShiftstart() {
        return shiftstart;
    }

    public LocalTime getShiftstop() {
        return shiftstop;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public int getRoundnterval() {
        return roundinterval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public int getDockpenalty() {
        return dockpenalty;
    }

    public int getLunchthreshold() {
        return lunchthreshold;
    }
    
}
