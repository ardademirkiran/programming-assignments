import java.util.ArrayList;

public class Location {
    public String name;
    public int id;
    public ArrayList<Trail> connectedTrails;

    public Location(String name, int id) {
        this.name = name;
        this.id = id;
        connectedTrails = new ArrayList<>();
    }

    @Override
    public String toString() {
        return name;
    }
}
