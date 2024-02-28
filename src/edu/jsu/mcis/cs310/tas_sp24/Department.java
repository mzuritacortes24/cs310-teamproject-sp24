package edu.jsu.mcis.cs310.tas_sp24;
/**
 *
 * @author Denver @author William
 */
public class Department {
    // Declare Variables
    private int id;
    private String description;
    private int terminalid;


    public Department(int id, String description, int terminalid) { //department constuctor containing id, desciption, and terminalid
        this.id = id; 
        this.description = description;
        this.terminalid = terminalid;
    }
    
    //Getters for id, description, and terminalid
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getTerminalid() {
        return terminalid;
    }

    @Override // Use toString to receive the id, description, and terminalid
    public String toString() {
        return String.format("#%d (%s), Terminal ID: %d", id, description, terminalid);
    }
}