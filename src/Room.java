import java.util.HashMap;
public class Room {
    private String description;
    private HashMap<String,Room>exits;
    public Room(String Description) {
        this.description = description;
        exits = new HashMap<String, Room>();
    }
    public void setExit(String direction, Room neighbor) {
        exit.put(direction, neighbor);
    }
    public Room getExit(String direction) {
        return exits.get(direction);
    }
    public String getsShortDescription() {
        return description;
    }
}

