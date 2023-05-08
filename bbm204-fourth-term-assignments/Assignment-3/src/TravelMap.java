import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class TravelMap {

    public Map<Integer, Location> locationMap = new HashMap<>();

    public List<Location> locations = new ArrayList<>();

    public List<Trail> trails = new ArrayList<>();

    // TODO: You are free to add more variables if necessary.

    public void initializeMap(String filename) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(filename);
            doc.getDocumentElement().normalize();

            NodeList locationList = doc.getElementsByTagName("Location");
            for (int i = 0; i < locationList.getLength(); i++) {
                Node node = locationList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element location = (Element) locationList.item(i);
                    String name = location.getElementsByTagName("Name").item(0).getTextContent();
                    int id = Integer.parseInt(location.getElementsByTagName("Id").item(0).getTextContent());
                    Location newLocation = new Location(name, id);
                    this.locationMap.put(id, newLocation);
                    locations.add(newLocation);

                }
            }

            NodeList trailList = doc.getElementsByTagName("Trail");
            for (int i = 0; i < trailList.getLength(); i++) {
                Node node = trailList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE) {
                    Element trail = (Element) trailList.item(i);
                    Location source = locationMap.get(Integer.parseInt(trail.getElementsByTagName("Source").item(0).getTextContent()));
                    Location destination = locationMap.get(Integer.parseInt(trail.getElementsByTagName("Destination").item(0).getTextContent()));
                    int danger = Integer.parseInt(trail.getElementsByTagName("Danger").item(0).getTextContent());
                    Trail newTrail = new Trail(source, destination, danger);
                    source.connectedTrails.add(newTrail);
                    destination.connectedTrails.add(newTrail);
                    this.trails.add(newTrail);
                }
            }
        }
        catch(ParserConfigurationException | SAXException | IOException e){
            e.printStackTrace();
        }

    }
    public void addTrailsToPQ(HashMap<Trail, Boolean> takenEdges, Location targetVertice, PriorityQueue<Trail> trailsPQ){
        for(Trail t : targetVertice.connectedTrails){
            if(takenEdges.get(t) == null){
                trailsPQ.add(t);
            }
        }
    }

    public List<Trail> getSafestTrails() {
        List<Trail> safestTrails = new ArrayList<>();
        HashMap<Location, Boolean> visitedVertices = new HashMap<>();
        HashMap<Trail, Boolean> takenEdges = new HashMap<>();
        PriorityQueue<Trail> trailsPQ = new PriorityQueue<>();
        addTrailsToPQ(takenEdges, locationMap.get(0), trailsPQ);
        while (!trailsPQ.isEmpty()){
            if(safestTrails.size() == locationMap.size() - 1) break;;
            Trail trailToCheck = trailsPQ.poll();
            if(visitedVertices.get(trailToCheck.source) != null && visitedVertices.get(trailToCheck.destination) != null){
                continue;
            }
            safestTrails.add(trailToCheck);
            visitedVertices.put(trailToCheck.destination, true);
            visitedVertices.put(trailToCheck.source, true);
            addTrailsToPQ(takenEdges, trailToCheck.destination, trailsPQ);
            addTrailsToPQ(takenEdges, trailToCheck.source, trailsPQ);

        }
        return safestTrails;

    }

    public void printSafestTrails(List<Trail> safestTrails) {
        int totalDanger = 0;
        System.out.println("Safest trails are:");
        for(Trail t: safestTrails){
            totalDanger += t.danger;
            System.out.println("The trail from " + t.source +  " to " + t.destination +  "with danger " + t.danger);
        }
        System.out.println("Total danger: " + totalDanger);
    }
}
