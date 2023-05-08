import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Kingdom {

    // TODO: You should add appropriate instance variables.
    ArrayList<ArrayList<Integer>> adjMatrix = new ArrayList<>();
    int[][] undirectedAdjMatrix;
    public void initializeKingdom(String filename) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String line;
            line = reader.readLine();
            String[] spLine = line.split(" ");
            undirectedAdjMatrix = new int[spLine.length][spLine.length];
            ArrayList<Integer> lineInput = new ArrayList<>();
            int rowCounter = 0;
            rowCounter = fillAdjacentMatrixes(spLine, lineInput, rowCounter);
            while((line = reader.readLine()) != null){
                spLine = line.split(" ");
                lineInput = new ArrayList<>();
                rowCounter = fillAdjacentMatrixes(spLine, lineInput, rowCounter);
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private int fillAdjacentMatrixes(String[] spLine, ArrayList<Integer> lineInput, int rowCounter) {
        for(int i = 0; i < spLine.length; i++){
            int newEntry = Integer.parseInt(spLine[i]);
            lineInput.add(newEntry);
            if(undirectedAdjMatrix[rowCounter][i] == 0) {
                undirectedAdjMatrix[rowCounter][i] = newEntry;
            }
            if(undirectedAdjMatrix[i][rowCounter] == 0 && newEntry == 1) {
                undirectedAdjMatrix[i][rowCounter] = 1;
            }
        }
        rowCounter++;
        adjMatrix.add(lineInput);
        return rowCounter;
    }

    public void depthFirstSearch(int cityIndex, int[] visited, Colony colony){
        visited[cityIndex] = 1;
        colony.cities.add(cityIndex + 1);
        if(cityIndex > colony.maxCityIndex) colony.maxCityIndex = cityIndex;
        colony.roadNetwork.put(cityIndex, new ArrayList<>());
        for(int i = 0; i < adjMatrix.get(cityIndex).size(); i++){
            if(adjMatrix.get(cityIndex).get(i) == 1){
                colony.roadNetwork.get(cityIndex).add(i);
            }
            if(undirectedAdjMatrix[cityIndex][i] == 1 && visited[i] == 0){
                depthFirstSearch(i, visited, colony);
            }
        }
    }

    public List<Colony> getColonies() {
        List<Colony> colonies = new ArrayList<>();
        int[] visited = new int[adjMatrix.size()];
        int colonyNum = 1;
        for(int vIndex = 0; vIndex < adjMatrix.size(); vIndex++){
            if(visited[vIndex] == 0) {
                Colony newColony = new Colony(colonyNum);
                depthFirstSearch(vIndex, visited, newColony);
                colonyNum++;
                colonies.add(newColony);
            }
        }
        return colonies;
    }

    public void printColonies(List<Colony> discoveredColonies) {
        for(Colony colony: discoveredColonies){
            Collections.sort(colony.cities);
            System.out.print("Colony " + colony.colonyNum + ": ");
            System.out.println(colony.cities);
        }
        // TODO: Your code here
    }
}
