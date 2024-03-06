package edu.jsu.mcis.cs310.tas_sp24.dao;

import java.time.*;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import com.github.cliftonlabs.json_simple.*;
import edu.jsu.mcis.cs310.tas_sp24.Punch;
import edu.jsu.mcis.cs310.tas_sp24.Shift;
import com.github.cliftonlabs.json_simple.*;
                                            
/**
 * 
 * Utility class for DAOs.  This is a final, non-constructable class containing
 * common DAO logic and other repeated and/or standardized code, refactored into
 * individual static methods.
 * 
 */
public final class DAOUtility {

    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift){
        
        return 1;
        
    }
    
/**
 * @author blake
 */
    
public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist) {
    ArrayList<HashMap<String, String>> jsonData = new ArrayList<>();

    for (Punch punch : dailypunchlist) {
        HashMap<String, String> punchData = new HashMap<>();

        punchData.put("id", String.valueOf(punch.getId()));
        punchData.put("badgeid", punch.getBadge().getId());
        punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
        punchData.put("punchtype", punch.getPunchtype().toString());
        punchData.put("punchadjustmenttype", punch.getAdjustmenttype().toString());
        punchData.put("priginaltimestamp", punch.printOriginal());
        punchData.put("adjustedtimestamp", punch.printAdjusted());

        jsonData.add(punchData);
    }

    String json = Jsoner.serialize(jsonData);

    return json;
}

    
}