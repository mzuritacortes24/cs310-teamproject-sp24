package edu.jsu.mcis.cs310.tas_sp24;
/**
 * Department datatype which holds the data for a single department instance
 * @author Denver
 * @author William
 */
public class Department {
    // Declare Variables
    private int id;
    private String description;
    private int terminalid;

    /**
     *
     * @param id The department id
     * @param description The description of the department
     * @param terminalid The terminal id of the associated with the department
     */
    public Department(int id, String description, int terminalid) { //department constuctor containing id, desciption, and terminalid
        this.id = id; 
        this.description = description;
        this.terminalid = terminalid;
    }
    
    //Getters for id, description, and terminalid

    /**
     * Getter for the id class variable
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for the description class variable
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     * Getter for the terminalid class variable
     * @return
     */
    public int getTerminalid() {
        return terminalid;
    }

    /**
     * toString override for the class
     * @return
     */
    @Override // Use toString to receive the id, description, and terminalid
    public String toString() {
        return String.format("#%d (%s), Terminal ID: %d", id, description, terminalid);
    }
}