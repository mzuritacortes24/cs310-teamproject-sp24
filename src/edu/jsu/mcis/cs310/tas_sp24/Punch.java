
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;


public class Punch {
    int terminalid;
    private final Badge badge;
    private final EventType punchType;
    private final LocalDateTime originaltimestamp;
    private PunchAdjustmentType adjustmenttype;
    
    public Punch(int terminalid, Badge badge, EventType punchType){
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchType = punchType;
        this.originaltimestamp = LocalDateTime.now();
    }
    
    public Punch(int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchType){
        this.terminalid = terminalid;
        this.badge = badge;
        this.punchType = punchType;
        this.originaltimestamp = originaltimestamp;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public Badge getBadge() {
        return badge;
    }

    public EventType getPunchType() {
        return punchType;
    }

    public LocalDateTime getOriginaltimestamp() {
        return originaltimestamp;
    }

    public PunchAdjustmentType getAdjustmenttype() {
        return adjustmenttype;
    }
    
    public String printOriginal(){
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("#");
        sb.append(badge.getId()).append(" ");
        sb.append(punchType.toString()).append(": ");
        sb.append(originaltimestamp.format(dTF).toUpperCase());
        
        return sb.toString();
    }
    
    @Override
    public String toString(){
        return printOriginal();
    }
    
}
