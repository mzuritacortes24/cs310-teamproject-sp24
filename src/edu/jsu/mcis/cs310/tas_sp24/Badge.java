package edu.jsu.mcis.cs310.tas_sp24;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

/**
 * Badge datatype which holds the data for a single badge instance
 * @author samkb
 */
public class Badge {

    private final String id, description;

    /**
     *
     * @param id The badge id of the employee
     * @param description The badge owners name
     */
    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    /**
     *
     * @param description The badge owners name
     */
    public Badge(String description) {
        this.description = description;
        this.id = generateIdFromDescription(description);
    }

    /**
     * Creates a badge id based on the value of the badge description
     * @param description The badge owners name
     * @return id
     */
    private String generateIdFromDescription(String description) {   
        Checksum crc = new CRC32();
        crc.update(description.getBytes(), 0, description.length());
        long checksumValue = crc.getValue();
        
        // Convert to 8-digit hexadecimal string
        return String.format("%08X", checksumValue);
    }
    
    /**
     * Getter for the id class variable
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the description class variable
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * toString override for the class
     * @return
     */
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
