
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
        this.adjustedtimestamp = LocalDateTime.now().withSecond(0).withNano(0);       /* " */
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
        LocalTime punchtimenoseconds = adjustedtimestamp.toLocalTime();
        LocalDate punchdate = originaltimestamp.toLocalDate();
        DailySchedule schedule = s.getDailySchedule(punchdate.getDayOfWeek());
        boolean midnightroundup = false;
        
        switch(punchtype){
            
            case CLOCK_OUT -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){                                                                                                                                                  /* check for weekend */
                        
                    if((punchtime != (schedule.getLunchstart())) && (punchtime.isAfter(schedule.getLunchstop()))){                                                                                                                                                               /* check for lunch break */

                        if(punchtime.isBefore(schedule.getShiftstop())){                                                                                                                                                                                                      /* check if punch is prior to the shift end */

                            if(((ChronoUnit.MINUTES.between( punchtimenoseconds, schedule.getShiftstop())) > schedule.getGraceperiod()) && ((ChronoUnit.MINUTES.between( punchtimenoseconds, schedule.getShiftstop())) <= schedule.getRoundinterval())){      /* check to see if punch is outside the grace period but within the interval period */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             //War Room F

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop().minusMinutes(schedule.getDockpenalty()));                                                                                                                   /* place values into variables */
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if((((ChronoUnit.MINUTES.between( punchtimenoseconds, schedule.getShiftstop())) > schedule.getRoundinterval()))){                                                                                                                   /* check if punch is outside the interval period */
                                                              
                                try{
                                    
                                    if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                             /* check if seconds are greater than 30 */

                                        punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                     /* add minute if seconds are greater */

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                        /* clear seconds and nanoseconds */

                                    if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                            /* check if day will be changed due to midnight round up */                                                                                 /* check to see if  */
                                        
                                        midnightroundup = true;                                                                                                                                                                                                             /* set flag if round up is needed */

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / schedule.getRoundinterval()))*schedule.getRoundinterval()));                                                                                                    /* round minutes to nearest interval */

                                }
                                catch(Exception e){

                                    midnightroundup = false;                                                                                                                                                                                                                /* set midnight round up flag to false */

                                    punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                        /* add hour due to minute overflow */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /* check to see if time is equal to midnight */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /* add day if time is midnight */

                                    }

                                }

                                if(midnightroundup){                                                                                                                                                                                                                        /* check for round up flag */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /* double check punch is equal to midnight */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /* add day to punch */                

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                         /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                        /*  */

                            }

                            else if((ChronoUnit.MINUTES.between(punchtimenoseconds, schedule.getShiftstop())) <= schedule.getGraceperiod()){                                                                                                                         /* check if punch is within grace period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop());                                                                                                                                                                  /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;                                                                                                                                                                                            /*  */

                            }

                        }

                        else if((punchtime.isAfter(schedule.getShiftstop()))){                                                                                                                                                                                                /* check if punch is after shift stop */

                            if((ChronoUnit.MINUTES.between( schedule.getShiftstop(), punchtimenoseconds)) <= schedule.getRoundinterval()){                                                                                                                           /* check if punch is within the interval period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstop());                                                                                                                                                                  /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_STOP;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if(((ChronoUnit.MINUTES.between( schedule.getShiftstop(), punchtimenoseconds)) > schedule.getRoundinterval()) && ((ChronoUnit.MINUTES.between( schedule.getShiftstop(), punchtimenoseconds)%15) != 0)){                                                                                                                      /* check if punch is within the interval period */
                                
                                try{
                                    
                                    if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                             /* check if seconds are greater than 30 */

                                        punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                     /* add minute if seconds are greater */

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                        /* clear seconds and nanoseconds */

                                    if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                            /* check if day will be changed due to midnight round up */                                                                                 /* check to see if  */
                                        
                                        midnightroundup = true;                                                                                                                                                                                                             /* set flag if round up is needed */

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / schedule.getRoundinterval()))*schedule.getRoundinterval()));                                                                                                    /* round minutes to nearest interval */

                                }
                                catch(Exception e){

                                    midnightroundup = false;                                                                                                                                                                                                                /* set midnight round up flag to false */

                                    punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                        /* add hour due to minute overflow */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /* check to see if time is equal to midnight */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /* add day if time is midnight */

                                    }

                                }

                                if(midnightroundup){                                                                                                                                                                                                                        /* check for round up flag */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /* double check punch is equal to midnight */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /* add day to punch */                

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                         /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND; 
                                
                            }
 
                            
                        }

                    }
                    
                    
                    else if((punchtime == (schedule.getLunchstart())) || (((punchtime.isBefore(schedule.getLunchstop()))) && (punchtime.isAfter(schedule.getLunchstart())))){                                                                                                                                                           /* check if punch is lunch clock out */

                        adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getLunchstart());                                                                                                                                                                         /* set values of adjusted time stamp */
                        adjustmenttype = PunchAdjustmentType.LUNCH_START;                                                                                                                                                                                                   /*  */

                    }

                    else{
                                            
                        try{

                            if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                                     /*  */

                                punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                             /*  */

                            }

                            punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                                /*  */

                            if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                                    /*  */
                                
                               midnightroundup = true;                                                                                                                                                                                                                      /*  */                         

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
                        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                                /*  */

                    }
                    
                }
                
                else{

                    try{
                        
                        if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                                         /*  */
                            
                            punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                                 /*  */
                            
                        }
                        
                        punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                                    /*  */
                        
                        if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= s.getRoundInterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                                        /*  */
                            
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
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                                    /*  */

                }
                
            }
            
            case CLOCK_IN -> {
                
                if(!((punchdate.getDayOfWeek() == DayOfWeek.SUNDAY) || (punchdate.getDayOfWeek() == DayOfWeek.SATURDAY))){                                                                                                                                                  /* check for weekend */
                    
                    if((punchtime != (schedule.getLunchstop())) && (punchtime.isBefore(schedule.getLunchstart()))){                                                                                                                                                                /* check for lunch break */

                        if(punchtime.isAfter(schedule.getShiftstart())){                                                                                                                                                                                                      /* check if punch is after shift start */

                            if(((ChronoUnit.MINUTES.between(schedule.getShiftstart(), punchtimenoseconds)) > schedule.getGraceperiod()) && ((ChronoUnit.MINUTES.between(schedule.getShiftstart(), punchtimenoseconds)) <= schedule.getRoundinterval())){      /* check to see if punch is outside the grace period but within the interval period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart().plusMinutes(schedule.getDockpenalty()));                                                                                                                       /* place values into variables */
                                adjustmenttype = PunchAdjustmentType.SHIFT_DOCK;                                                                                                                                                                                            /*  */
                                
                            }
                            
                            else if((((ChronoUnit.MINUTES.between(schedule.getShiftstart(), punchtimenoseconds)) > schedule.getRoundinterval()))){                                                                                                                   /* check if punch is outside interval period */
                                
                                try{
                        
                                    if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                             /*  */

                                        punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                     /*  */

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                        /*  */

                                    if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                            /*  */
                                        
                                        midnightroundup = true;                                                                                                                                                                                                             /*  */

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / schedule.getRoundinterval()))*schedule.getRoundinterval()));                                                                                                    /*  */

                                }
                                catch(Exception e){

                                    midnightroundup = false;                                                                                                                                                                                                                /*  */

                                    punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                        /*  */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /*  */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /*  */

                                    }

                                }

                                if(midnightroundup){                                                                                                                                                                                                                        /*  */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /*  */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /*  */

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                         /*  */
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                        /*  */
                                
                            }

                            else if((ChronoUnit.MINUTES.between(schedule.getShiftstart(), punchtimenoseconds)) <= schedule.getGraceperiod()){                                                                                                                        /* check to see if punch is within grace period */

                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart());                                                                                                                                                                 /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;                                                                                                                                                                                           /*  */

                            }

                        }

                        else if((punchtime.isBefore(schedule.getShiftstart()))){                                                                                                                                                                                              /* check if punch is before shift start */

                            if((ChronoUnit.MINUTES.between(punchtimenoseconds, schedule.getShiftstart())) <= schedule.getRoundinterval()){                                                                                                                           /* check if punch is within the interval period */
                                
                                adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getShiftstart());                                                                                                                                                                 /* set values of adjusted time stamp */
                                adjustmenttype = PunchAdjustmentType.SHIFT_START;                                                                                                                                                                                           /*  */
                                
                            }
                            
                            else if(((ChronoUnit.MINUTES.between(punchtimenoseconds, schedule.getShiftstart())) > schedule.getRoundinterval()) && ((ChronoUnit.MINUTES.between(punchtimenoseconds, schedule.getShiftstart())%15) != 0)){                                                                                                                      /* check if punch is within the interval period */
                                
                                try{
                        
                                    if(punchtime.getSecond() >= (Duration.ofMinutes(1).getSeconds() / 2)){                                                                                                                                                             /*  */

                                        punchtime = punchtime.plusMinutes(1);                                                                                                                                                                                     /*  */

                                    }

                                    punchtime = punchtime.withSecond(0).withNano(0);                                                                                                                                                                        /*  */

                                    if((ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) <= schedule.getRoundinterval() && (ChronoUnit.MINUTES.between(punchtime, LocalTime.MIDNIGHT)) >= 0){                                                                            /*  */
                                        
                                        midnightroundup = true;                                                                                                                                                                                                             /*  */

                                    }

                                    punchtime = punchtime.withMinute((Math.round((float)(((float)punchtime.getMinute()) / schedule.getRoundinterval()))*schedule.getRoundinterval()));                                                                                                    /*  */

                                }
                                catch(Exception e){

                                    midnightroundup = false;                                                                                                                                                                                                                /*  */

                                    punchtime = punchtime.withMinute(0).plusHours(1);                                                                                                                                                                        /*  */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /*  */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /*  */

                                    }

                                }

                                if(midnightroundup){                                                                                                                                                                                                                        /*  */

                                    if(punchtime.equals(LocalTime.MIDNIGHT)){                                                                                                                                                                                            /*  */

                                        punchdate = punchdate.plusDays(1);                                                                                                                                                                                          /*  */

                                    }

                                }

                                adjustedtimestamp = LocalDateTime.of(punchdate, punchtime);                                                                                                                                                                         /*  */
                                adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;  
                                
                            }
 
                        }

                    }

                    else if((punchtime == (schedule.getLunchstop())) || ((punchtime.isAfter(schedule.getLunchstart())))){                                                                                                                                                            /* check if punch is lunch clock in */

                        adjustedtimestamp = LocalDateTime.of(punchdate, schedule.getLunchstop());                                                                                                                                                                          /* set values of adjusted time stamp */
                        adjustmenttype = PunchAdjustmentType.LUNCH_STOP;                                                                                                                                                                                                    /*  */

                    }
                    
                    
                    else{

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
                        adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                                /*  */
                    
                    }
                    
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
                    adjustmenttype = PunchAdjustmentType.INTERVAL_ROUND;                                                                                                                                                                                                    /*  */

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
