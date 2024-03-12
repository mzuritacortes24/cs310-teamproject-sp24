
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * @author samkb
 */

public class Punch {
    
    int id;
    int terminalid;                                                             /* instantiate instance fields */
    private final Badge badge;                                                  /* " */
    private final EventType punchtype;                                          /* " */
    private final LocalDateTime originaltimestamp;                              /* " */
    private LocalDateTime adjustedtimestamp;                                    /* " */
    private PunchAdjustmentType adjustmenttype;                                 /* " */
    
    public Punch(int terminalid, Badge badge, EventType punchtype){
        
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchtype = punchtype;                                             /* " */
        this.originaltimestamp = LocalDateTime.now();                           /* set originaltimestamp equal to current time */
        this.adjustedtimestamp = LocalDateTime.now().withSecond(0);       /* " */
        this.adjustmenttype = PunchAdjustmentType.valueOf("NONE");          /* " */
        
    }
    
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
        
        this.id = id;
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchtype = punchtype;                                             /* " */
        this.originaltimestamp = originaltimestamp;                             /* " */
        this.adjustedtimestamp = originaltimestamp.withSecond(0);         /* " */
        this.adjustmenttype = PunchAdjustmentType.valueOf("NONE");          /* " */
        
    }
    
    public void adjust(Shift s){
        
        LocalTime punchtime = originaltimestamp.toLocalTime();
        LocalDate punchdate = originaltimestamp.toLocalDate();
        boolean midnightroundup = false;
        
        switch(punchtype){
            
            case CLOCK_OUT -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){

                    if((punchtime != (s.getLunchStart())) && (!(punchtime.isBefore(s.getLunchStop())))){

                        if(punchtime.isBefore(s.getShiftStop())){

                            if(((ChronoUnit.MINUTES.between( punchtime, s.getShiftStop())) > s.getGracePeriod()) && ((ChronoUnit.MINUTES.between( punchtime, s.getShiftStop())) <= s.getRoundInterval())){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             //War Room F

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStop().minusMinutes(s.getDockPenalty()));
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                                
                            }
                            
                            else if((((ChronoUnit.MINUTES.between( punchtime, s.getShiftStop())) > s.getRoundInterval()))){
                                
                                try{
                        
                                    if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){

                                        punchtime = punchtime.plusMinutes(1);

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);

                                    if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){
                                        
                                        midnightroundup = true;

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));

                                }
                                catch(Exception e){

                                    midnightroundup = false;

                                    punchtime = punchtime.withMinute(0).plusHours(1);

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){

                                        punchdate = punchdate.plusDays(1);

                                    }

                                }

                                if(midnightroundup){

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){

                                        punchdate = punchdate.plusDays(1);

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;

                            }

                            else if((ChronoUnit.MINUTES.between(punchtime, s.getShiftStop())) <= s.getGracePeriod()){

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStop());
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;

                            }

                        }

                        else if((punchtime.isAfter(s.getShiftStop()))){

                            if((ChronoUnit.MINUTES.between( s.getShiftStop(), punchtime)) <= s.getRoundInterval()){

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStop());
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;
                                
                            }
                            
                        }

                    }
                    
                    
                    else if((punchtime == (s.getLunchStart())) || ((punchtime.isBefore(s.getLunchStop())))){

                        adjustedtimestamp = LocalDateTime.of(punchdate, s.getLunchStart());
                        adjustmenttype = PunchAdjustmentType.LUNCH_START;

                    }

                    else{

                        try{

                            if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){

                                punchtime = punchtime.plusMinutes(1);

                            }

                            punchtime = punchtime.withSecond(0).withNano(0);

                            if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){
                                midnightroundup = true;

                            }

                            punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));

                        }
                        catch(Exception e){

                            midnightroundup = false;

                            punchtime = punchtime.withMinute(0).plusHours(1);

                            if(punchtime.equals(LocalTime.MIDNIGHT)){

                                punchdate = punchdate.plusDays(1);

                            }

                        }

                        if(midnightroundup){
                            
                            if(punchtime.equals(LocalTime.MIDNIGHT)){

                                punchdate = punchdate.plusDays(1);

                            }

                        }

                        adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;

                    }
                    
                }
                
                else{

                    try{
                        
                        if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){
                            
                            punchtime = punchtime.plusMinutes(1);
                            
                        }
                        
                        punchtime = punchtime.withSecond(0).withNano(0);
                        
                        if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){                            
                            
                            midnightroundup = true;
                            
                        }
                        
                        punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));
                        
                    }
                    catch(Exception e){
                        
                        midnightroundup = false;
                                    
                        punchtime = punchtime.withMinute(0).plusHours(1);
                                                              
                        if(punchtime.equals(LocalTime.MIDNIGHT)){
                                            
                            punchdate = punchdate.plusDays(1);
                                        
                        }
                                    
                    }
                    
                    if(midnightroundup){
                        
                        if(punchtime.equals(LocalTime.MIDNIGHT)){
                                            
                            punchdate = punchdate.plusDays(1);
                                        
                        }
                        
                    }
                    
                    adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;

                }
                
            }
            
            case CLOCK_IN -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){
                    
                    if((punchtime != (s.getLunchStop())) && (!(punchtime.isAfter(s.getLunchStart())))){

                        if(punchtime.isAfter(s.getShiftStart())){

                            if(((ChronoUnit.MINUTES.between(s.getShiftStart(), punchtime)) > s.getGracePeriod()) && ((ChronoUnit.MINUTES.between(s.getShiftStart(), punchtime)) <= s.getRoundInterval())){

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStart().plusMinutes(s.getDockPenalty()));
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;
                                
                            }
                            
                            else if((((ChronoUnit.MINUTES.between(s.getShiftStart(), punchtime)) > s.getRoundInterval()))){
                                
                                try{
                        
                                    if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){

                                        punchtime = punchtime.plusMinutes(1);

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);

                                    if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){
                                        
                                        midnightroundup = true;

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));

                                }
                                catch(Exception e){

                                    midnightroundup = false;

                                    punchtime = punchtime.withMinute(0).plusHours(1);

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){

                                        punchdate = punchdate.plusDays(1);

                                    }

                                }

                                if(midnightroundup){

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){

                                        punchdate = punchdate.plusDays(1);

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
                                
                            }

                            else if((ChronoUnit.MINUTES.between(s.getShiftStart(), punchtime)) <= s.getGracePeriod()){

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStart());
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;

                            }

                        }

                        else if((punchtime.isBefore(s.getShiftStart()))){

                            if((ChronoUnit.MINUTES.between(punchtime, s.getShiftStart())) <= s.getRoundInterval()){

                                adjustedtimestamp = LocalDateTime.of(punchdate, s.getShiftStart());
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;
                                
                            }
                            
                        }

                    }

                    else if((punchtime == (s.getLunchStop())) || ((punchtime.isAfter(s.getLunchStart())))){

                        adjustedtimestamp = LocalDateTime.of(punchdate, s.getLunchStop());
                        adjustmenttype = PunchAdjustmentType.LUNCH_STOP;

                    }
                    
                    
                    else{

                        try{
                        
                            if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){

                                punchtime = punchtime.plusMinutes(1);

                            }

                            punchtime = punchtime.withSecond(0).withNano(0);

                            if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){                                
                                
                                midnightroundup = true;

                            }

                            punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));

                        }
                        catch(Exception e){

                            midnightroundup = false;

                            punchtime = punchtime.withMinute(0).plusHours(1);

                            if(punchtime.equals(LocalTime.MIDNIGHT)){

                                punchdate = punchdate.plusDays(1);

                            }

                        }

                        if(midnightroundup){

                            if(punchtime.equals(LocalTime.MIDNIGHT)){

                                punchdate = punchdate.plusDays(1);

                            }

                        }

                        adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
                    
                    }
                    
                }
                
                else{
                    
                    try{
                        
                        if(punchtime.getSecond() > (Duration.ofMinutes(1).getSeconds() / 2)){
                            
                            punchtime = punchtime.plusMinutes(1);
                            
                        }
                        
                        punchtime = punchtime.withSecond(0).withNano(0);
                        
                        if(!(Math.abs(ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) > s.getRoundInterval())){                            
                           
                            midnightroundup = true;
                            
                        }
                        
                        punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));
                        
                    }
                    catch(Exception e){
                        
                        midnightroundup = false;
                                    
                        punchtime = punchtime.withMinute(0).plusHours(1);
                                                              
                        if(punchtime.equals(LocalTime.MIDNIGHT)){
                                            
                            punchdate = punchdate.plusDays(1);
                                        
                        }
                                    
                    }
                    
                    if(midnightroundup){
                        
                        if(punchtime.equals(LocalTime.MIDNIGHT)){
                                            
                            punchdate = punchdate.plusDays(1);
                                        
                        }
                        
                    }
                    
                    adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;

                }
                
            }

                
            case TIME_OUT -> {
                
                adjustmenttype = PunchAdjustmentType.NONE;
                
            }
            
            default -> {
                
                throw new AssertionError(punchtype.name());

            }
            
        }
        
    }
    
    public int getId() {
        
        return id;
        
    }

    public int getTerminalid() {
        
        return terminalid;
        
    }

    public Badge getBadge() {
        
        return badge;
        
    }

    public EventType getPunchtype() {
        
        return punchtype;
        
    }

    public LocalDateTime getOriginaltimestamp() {
        
        return originaltimestamp;
        
    }
    
    public LocalDateTime getAdjustedtimestamp() {
        
        return adjustedtimestamp;
        
    }

    public PunchAdjustmentType getAdjustmenttype() {
        
        return adjustmenttype;
        
    }
    
    public String printAdjusted(){
        
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss"); /* set print pattern for originaltimestamp */
        
        return String.format("#%s %s: %s (%s)",                                              /* format and return String object */
                badge.getId(),                                                            /* " */
                punchtype.toString(),                                                     /* " */
                adjustedtimestamp.format(dTF).toUpperCase(),                      /* " */
                adjustmenttype.toString());                                               /* " */
         
    }
    
    public String printOriginal(){
        
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss"); /* set print pattern for originaltimestamp */
        
        return String.format("#%s %s: %s",                                              /* format and return String object */
                badge.getId(),                                                            /* " */
                punchtype.toString(),                                                     /* " */
                originaltimestamp.format(dTF).toUpperCase());                     /* " */
        
    }
    
    @Override
    
    public String toString(){
        
        return printOriginal();
        
    }
    
}
