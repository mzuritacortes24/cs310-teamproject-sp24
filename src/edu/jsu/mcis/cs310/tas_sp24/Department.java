package edu.jsu.mcis.cs310.tas_sp24;
/**
 *
 * @author Denver
 */
public class Department {
    private int id;
    private String description;
    private int terminalid;


    public Department(int id, String description, int terminalid) {
        this.id = id;
        this.description = description;
        this.terminalid = terminalid;
    }
    
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getTermanalid() {
        return terminalid;
    }

    @Override
    public String toString() {
        return "#" + id + " (" + description + "), Terminal ID: " + terminalid;
    }
}