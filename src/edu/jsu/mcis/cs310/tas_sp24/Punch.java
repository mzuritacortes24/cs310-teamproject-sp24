
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author samkb
 */

public class Punch {
    
    int terminalid;                                                             /* instantiate instance fields */
    private final Badge badge;                                                  /* " */
    private final EventType punchType;                                          /* " */
    private final LocalDateTime originaltimestamp;                              /* " */
    private PunchAdjustmentType adjustmenttype;                                 /* " */
    
    public Punch(int terminalid, Badge badge, EventType punchType){
        
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchType = punchType;                                             /* " */
        this.originaltimestamp = LocalDateTime.now();                           /* set originaltimestamp equal to current time */
        
    }
    
    public Punch(int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchType){
        
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchType = punchType;                                             /* " */
        this.originaltimestamp = originaltimestamp;                             /* " */
        
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
        
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss"); /* set print pattern for originaltimestamp */
        
        return String.format("#%s %s: %s",                                              /* format and return String object */
                badge.getId(),                                                            /* " */
                punchType.toString(),                                                     /* " */
                originaltimestamp.format(dTF).toUpperCase());                     /* " */
        
    }
    
    @Override
    
    public String toString(){
        
        return printOriginal();
        
    }
    
}
