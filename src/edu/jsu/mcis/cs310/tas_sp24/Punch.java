
package edu.jsu.mcis.cs310.tas_sp24;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Punch datatype which contains information for a single punch instance
 * @author samkb
 * 
 */

public class Punch {
    
    int id;
    int terminalid;                                                             /* instantiate instance fields */
    private final Badge badge;                                                  /* " */
    private final EventType punchtype;                                          /* " */
    private final LocalDateTime originaltimestamp;                              /* " */
    private LocalDateTime adjustedtimestamp;                                    /* " */
    private PunchAdjustmentType adjustmenttype;                                 /* " */
    
    /**
     *
     * @param terminalid The id of the terminal used for the punch
     * @param badge The badge object for the punch instance employee
     * @param punchtype The type of the punch instance
     */
    public Punch(int terminalid, Badge badge, EventType punchtype){
        
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchtype = punchtype;                                             /* " */
        this.originaltimestamp = LocalDateTime.now();                           /* set originaltimestamp equal to current time */
        this.adjustedtimestamp = LocalDateTime.now().withSecond(0).withNano(0);       /* " */
        this.adjustmenttype = PunchAdjustmentType.valueOf("NONE");          /* " */
        
    }
    
    /**
     *
     * @param id The database id of the punch instance
     * @param terminalid The id of the terminal used for the punch
     * @param badge The badge object for the punch instance employee
     * @param originaltimestamp The timestamp of the punch instance
     * @param punchtype The type of the punch instance
     */
    public Punch(int id, int terminalid, Badge badge, LocalDateTime originaltimestamp, EventType punchtype){
        
        this.id = id;
        this.terminalid = terminalid;                                           /* set instance field equal to parameter */
        this.badge = badge;                                                     /* " */
        this.punchtype = punchtype;                                             /* " */
        this.originaltimestamp = originaltimestamp;                             /* " */
        this.adjustedtimestamp = originaltimestamp.withSecond(0);         /* " */
        this.adjustmenttype = PunchAdjustmentType.valueOf("NONE");          /* " */
        
    }
    
    /**
     * Adjusts originaltimestamp to generate adjustedtimestamp
     * @param s The shift object that contains the adjustment rules
     */
    public void adjust(Shift s){
        
        LocalTime punchtime = originaltimestamp.toLocalTime();
        LocalDate punchdate = originaltimestamp.toLocalDate();
        DailySchedule schedule = s.getDailySchedule(punchdate.getDayOfWeek());
        
        switch(punchtype){
            
            case CLOCK_OUT -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){                                                                                                                                                  /* check for weekend */
                        
                    if((punchtime != (schedule.getLunchstart())) && (punchtime.isAfter(schedule.getLunchstop()))){                                                                                                                                                               /* check for lunch break */

                        if(punchtime.isBefore(schedule.getShiftstop())){                                                                                                                                                                                                      /* check if punch is prior to the shift end */

                            if(((ChronoUnit.SECONDS.between( punchtime, schedule.getShiftstop())) > (schedule.getGraceperiod()*60)) && ((ChronoUnit.SECONDS.between( punchtime, schedule.getShiftstop())) <= (schedule.getRoundinterval()*60))){      /* check to see if punch is outside the grace period but within the interval period */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             //War Room F

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop().minusMinutes(schedule.getDockpenalty()));                                                                                                                   /* place values into variables */
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if((((ChronoUnit.SECONDS.between( punchtime, schedule.getShiftstop())) > (schedule.getRoundinterval()*60)))){                                                                                                                   /* check if punch is outside the interval period */
                                                              
                                roundTimestamp(s);                                                                                                                                                                                        /*  */

                            }

                            else if((ChronoUnit.SECONDS.between(punchtime, schedule.getShiftstop())) <= (schedule.getGraceperiod()*60)){                                                                                                                         /* check if punch is within grace period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop());                                                                                                                                                                  /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;                                                                                                                                                                                            /*  */

                            }

                        }

                        else if((punchtime.isAfter(schedule.getShiftstop()))){                                                                                                                                                                                                /* check if punch is after shift stop */

                            if((ChronoUnit.SECONDS.between( schedule.getShiftstop(), punchtime)) <= (schedule.getRoundinterval()*60)){                                                                                                                           /* check if punch is within the interval period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop());                                                                                                                                                                  /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if(((ChronoUnit.SECONDS.between( schedule.getShiftstop(), punchtime)) > (schedule.getRoundinterval()*60)) && ((punchtime.getMinute()%15) != 0)){                                                                                                                      /* check if punch is within the interval period */
                                
                                roundTimestamp(s); 
                                
                            }
 
                            
                        }

                    }
                    
                    
                    else if((punchtime == (schedule.getLunchstart())) || (((punchtime.isBefore(schedule.getLunchstop()))) && (punchtime.isAfter(schedule.getLunchstart())))){                                                                                                                                                           /* check if punch is lunch clock out */

                        adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getLunchstart());                                                                                                                                                                         /* set values of adjusted time stamp */
                        adjustmenttype = PunchAdjustmentType.LUNCH_START;                                                                                                                                                                                                   /*  */

                    }

                    else if ((punchtime.getMinute()%15) != 0){
                                            
                        roundTimestamp(s);                                                                                                                                                                                                /*  */

                    }
                    
                }
                
                else{

                    roundTimestamp(s);                                                                                                                                                                                                    /*  */

                }
                
            }
            
            case CLOCK_IN -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){                                                                                                                                                  /* check for weekend */
                    
                    if((punchtime != (schedule.getLunchstop())) && (punchtime.isBefore(schedule.getLunchstart()))){                                                                                                                                                                /* check for lunch break */

                        if(punchtime.isAfter(schedule.getShiftstart())){                                                                                                                                                                                                      /* check if punch is after shift start */

                            if(((ChronoUnit.SECONDS.between(schedule.getShiftstart(), punchtime)) > (schedule.getGraceperiod()*60)) && ((ChronoUnit.SECONDS.between(schedule.getShiftstart(), punchtime)) <= (schedule.getRoundinterval()*60))){      /* check to see if punch is outside the grace period but within the interval period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart().plusMinutes(schedule.getDockpenalty()));                                                                                                                       /* place values into variables */
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if((((ChronoUnit.SECONDS.between(schedule.getShiftstart(), punchtime)) > (schedule.getRoundinterval()*60)))){                                                                                                                   /* check if punch is outside interval period */
                                
                                roundTimestamp(s);                                                                                                                                                                                        /*  */
                                
                            }

                            else if((ChronoUnit.SECONDS.between(schedule.getShiftstart(), punchtime)) <= (schedule.getGraceperiod()*60)){                                                                                                                        /* check to see if punch is within grace period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart());                                                                                                                                                                 /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;                                                                                                                                                                                           /*  */

                            }

                        }

                        else if((punchtime.isBefore(schedule.getShiftstart()))){                                                                                                                                                                                              /* check if punch is before shift start */

                            if((ChronoUnit.SECONDS.between(punchtime, schedule.getShiftstart())) <= (schedule.getRoundinterval()*60)){                                                                                                                           /* check if punch is within the interval period */
                                
                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart());                                                                                                                                                                 /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;                                                                                                                                                                                           /*  */
                                
                            }
                            
                            else if(((ChronoUnit.SECONDS.between(punchtime, schedule.getShiftstart())) > (schedule.getRoundinterval()*60)) && ((punchtime.getMinute()%15) != 0)){                                                                                                                      /* check if punch is within the interval period */
                                
                                roundTimestamp(s);  
                                
                            }
 
                        }

                    }

                    else if((punchtime == (schedule.getLunchstop())) || (((punchtime.isBefore(schedule.getLunchstop()))) && (punchtime.isAfter(schedule.getLunchstart())))){                                                                                                                                                            /* check if punch is lunch clock in */

                        adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getLunchstop());                                                                                                                                                                          /* set values of adjusted time stamp */
                        adjustmenttype = PunchAdjustmentType.LUNCH_STOP;                                                                                                                                                                                                    /*  */

                    }
                    
                    
                    else if ((punchtime.getMinute()%15) != 0){

                        roundTimestamp(s);                                                                                                                                                                                                /*  */
                    
                    }
                    
                }
                
                else{
                    
                    roundTimestamp(s);                                                                                                                                                                                                   /*  */

                }
                
            }
 
            case TIME_OUT -> {
                
                adjustmenttype = PunchAdjustmentType.NONE;                                                                                                                                                                                                                  /* set adjustment type to none */
                
            }
            
            default -> {
                
                throw new AssertionError(punchtype.name());

            }
            
        }
        
    }
    
    /**
     * Rounds the timestamp if it falls with the proper times
     * @param s The shift object that contains the adjustment rules
     */
    public void roundTimestamp(Shift s){
        
        LocalTime punchtime = originaltimestamp.toLocalTime();
        LocalDate punchdate = originaltimestamp.toLocalDate();
        Boolean weekend = false;
        Boolean midnightroundup = false;
        
        if(((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){
            
            weekend = true;
            
        }
        
        if(!weekend){
            
            DailySchedule schedule = s.getDailySchedule(punchdate.getDayOfWeek());
            
            try{
                        
                if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                                     /*  */

                    punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                             /*  */

                }

                punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                                /*  */

                if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                                    /*  */      
                             
                    midnightroundup = true;                                                                                                                                                                                                                     /*  */

                }

                punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / schedule.getRoundinterval()))*schedule.getRoundinterval()));                                                                                                            /*  */

            }
            catch(Exception e){

                midnightroundup = false;                                                                                                                                                                                                                        /*  */

                punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                                /*  */

                if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                                    /*  */

                    punchdate = punchdate.plusDays(1);                                                                                                                                                                                                  /*  */

                }

            }

            if(midnightroundup){                                                                                                                                                                                                                                /*  */

                if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                                    /*  */

                    punchdate = punchdate.plusDays(1);                                                                                                                                                                                                  /*  */

                }

            }

            adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                                 /*  */
            adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
            
        }
        
        else{
            
            try{
                        
                if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                                         /*  */
                            
                    punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                                 /*  */
                            
                }
                        
                punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                                    /*  */
                        
                if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= s.getRoundInterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                        /*  */
                           
                    midnightroundup = true;                                                                                                                                                                                                                         /*  */
                            
                }
                        
                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / s.getRoundInterval()))*s.getRoundInterval()));                                                                                                                /*  */
                        
            }
            catch(Exception e){
                        
                midnightroundup = false;                                                                                                                                                                                                                            /*  */
                                    
                punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                                    /*  */
                                                              
                if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                                        /*  */
                                            
                    punchdate = punchdate.plusDays(1);                                                                                                                                                                                                      /*  */
                                        
                }
                                    
            }
                    
            if(midnightroundup){                                                                                                                                                                                                                                    /*  */
                        
                if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                                        /*  */
                                            
                    punchdate = punchdate.plusDays(1);                                                                                                                                                                                                      /*  */
                                        
                }
                        
            }
                    
            adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                                     /*  */
            adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;
            
        }
        
    }
    
    /**
     * Getter for the id class variable
     * @return id
     */
    public int getId() {
        
        return id;
        
    }

    /**
     * Getter for the terminalid class variable
     * @return terminalid
     */
    public int getTerminalid() {
        
        return terminalid;
        
    }

    /**
     * Getter for the badge class variable
     * @return badge
     */
    public Badge getBadge() {
        
        return badge;
        
    }

    /**
     * Getter for the punchtype class variable
     * @return punchtype
     */
    public EventType getPunchtype() {
        
        return punchtype;
        
    }

    /**
     * Getter for the originalGetter for the id class variable class variable
     * @return originaltimestamp
     */
    public LocalDateTime getOriginaltimestamp() {
        
        return originaltimestamp;
        
    }
    
    /**
     * Getter for the adjustedtimestamp class variable
     * @return adjustedtimestamp
     */
    public LocalDateTime getAdjustedtimestamp() {
        
        return adjustedtimestamp;
        
    }

    /**
     * Getter for the adjustmenttype class variable
     * @return adjustmenttype
     */
    public PunchAdjustmentType getAdjustmenttype() {
        
        return adjustmenttype;
        
    }
    
    /**
     * Print method for the adjustedtimestamp class variable
     * @return Formatted adjusted timestamp
     */
    public String printAdjusted(){
        
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss"); /* set print pattern for originaltimestamp */
        
        return String.format("#%s %s: %s (%s)",                                              /* format and return String object */
                badge.getId(),                                                            /* " */
                punchtype.toString(),                                                     /* " */
                adjustedtimestamp.format(dTF).toUpperCase(),                      /* " */
                adjustmenttype.toString());                                               /* " */
         
    }
    
    /**
     *Print method for the originaltimestamp class variable
     * @return Formatted original timestamp
     */
    public String printOriginal(){
        
        DateTimeFormatter dTF = DateTimeFormatter.ofPattern("EEE MM/dd/uuuu HH:mm:ss"); /* set print pattern for originaltimestamp */
        
        return String.format("#%s %s: %s",                                              /* format and return String object */
                badge.getId(),                                                            /* " */
                punchtype.toString(),                                                     /* " */
                originaltimestamp.format(dTF).toUpperCase());                     /* " */
        
    }
    
    /**
     * toString override that calls printOriginal method
     * @return 
     */
    @Override
    
    public String toString(){
        
        return printOriginal();
        
    }
    
}
