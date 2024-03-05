package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
                                            
/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
        int totalminutes = 0;
        LocalDateTime clock_in = null;
        LocalDateTime clock_out = null;
        LocalDateTime lunch_in = null;
        LocalDateTime lunch_out = null;
        LocalDateTime time_out = null;
        Boolean weekend = false;
        
        for(Punch p : dailypunchlist){
            
            switch (p.getPunchtype()){
                
                case CLOCK_OUT -> {
                    
                    if(!(((p.getAdjustedtimestamp().getDayOfWeek()) == DayOfWeek.SUNDAY) || ((p.getAdjustedtimestamp().getDayOfWeek()) == DayOfWeek.SATURDAY))){
                    
                        if(((p.getAdjustedtimestamp().toLocalTime()) == (shift.getLunchStart())) || (p.getAdjustedtimestamp().toLocalTime().isBefore(shift.getLunchStop()))){

                            lunch_out = p.getAdjustedtimestamp();

                        }
                        
                        else{
                            
                            clock_out = p.getAdjustedtimestamp();
                            
                        }
                        
                    }
                    
                    else{
                        
                        clock_out = p.getAdjustedtimestamp();
                        weekend = true;
                        
                    }
                    
                }
                    
                case CLOCK_IN -> {
                    
                    if(!(((p.getAdjustedtimestamp().getDayOfWeek()) == DayOfWeek.SUNDAY) || ((p.getAdjustedtimestamp().getDayOfWeek()) == DayOfWeek.SATURDAY))){
                    
                        if(((p.getAdjustedtimestamp().toLocalTime()) == (shift.getLunchStop())) || (p.getAdjustedtimestamp().toLocalTime().isAfter(shift.getLunchStart()))){

                            lunch_in = p.getAdjustedtimestamp();

                        }
                        
                        else{
                            
                            clock_in = p.getAdjustedtimestamp();
                            
                        }
                    
                    }
                    
                    else{
                        
                        clock_in = p.getAdjustedtimestamp();
                        weekend = true;
                        
                    }
                    
                }
                    
                case TIME_OUT -> {
                    
                    time_out = p.getAdjustedtimestamp();
                    
                }
                
                default -> {
                    
                    throw new AssertionError(p.getPunchtype().name());
                    
                }
                    
            }
            
        }
        
        try{
            
            totalminutes = (int) ChronoUnit.MINUTES.between(clock_in, clock_out);
        
        }
        catch(Exception e){}
        
        if(!(weekend)){
            
            try{

                if((lunch_out.toLocalTime().equals(shift.getLunchStart())) && (lunch_in.toLocalTime().equals(shift.getLunchStop()))){

                    totalminutes = (int) (totalminutes - shift.getLunchDuration());

                }

            }
            catch(Exception e){
                
                if(totalminutes >= shift.getLunchThreshold()){
                    
                    totalminutes = (int) (totalminutes - shift.getLunchDuration());
                
                }

            }
        
        }
                
        return totalminutes;
        
    }
    
}