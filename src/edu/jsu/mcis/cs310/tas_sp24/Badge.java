package edu.jsu.mcis.cs310.tas_sp24;

import java.util.zip.CRC32;
import java.util.zip.Checksum;

public class Badge {

    private final String id, description;

    public Badge(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Badge(String description) {
        this.description = description;
        this.id = generateIdFromDescription(description);
    }

    private String generateIdFromDescription(String description) {   
        Checksum crc = new CRC32();
        crc.update(description.getBytes(), 0, description.length());
        long checksumValue = crc.getValue();
        
        // Convert to 8-digit hexadecimal string
        return String.format("%08X", checksumValue);
    }
    
    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();

        s.append('#').append(id).append(' ');
        s.append('(').append(description).append(')');

        return s.toString();

    }

}
