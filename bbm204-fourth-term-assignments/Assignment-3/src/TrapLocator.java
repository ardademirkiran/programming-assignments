import java.util.*;

public class TrapLocator {
    public List<Colony> colonies;

    public TrapLocator(List<Colony> colonies) {
        this.colonies = colonies;
    }

    ArrayList<Integer> detectCyclesDFS(int startCityIndex, int colonyIndex){
            int[] visited = new int[colonies.get(colonyIndex).maxCityIndex + 1];
            int[] edgeTo = new int[colonies.get(colonyIndex).maxCityIndex + 1];
            Stack<Integer> dfsStack = new Stack<>();
            dfsStack.push(startCityIndex);
            ArrayList<Integer> cyclePath = new ArrayList<>();
            while(!dfsStack.isEmpty()){
                int targetIndex = dfsStack.pop();
                visited[targetIndex] = 1;
                for(int i : colonies.get(colonyIndex).roadNetwork.get(targetIndex)){
                    if(i == startCityIndex){
                        for(int j = targetIndex; j != startCityIndex; j = edgeTo[j]){
                            cyclePath.add(j + 1);
                        }
                        cyclePath.add(startCityIndex + 1);
                        return cyclePath;
                    }
                    if(visited[i] == 0){
                        edgeTo[i] = targetIndex;
                        dfsStack.push(i);
                    }
                }
            }
            return cyclePath;
    }

    public List<List<Integer>> revealTraps() {
        List<List<Integer>> traps = new ArrayList<>();
        for(int colonyIndex = 0; colonyIndex < colonies.size(); colonyIndex++){
            Colony colony = colonies.get(colonyIndex);
            ArrayList<Integer> cyclePath = new ArrayList<>();
            for(int i = 0; i < colony.cities.size(); i++){
                cyclePath = detectCyclesDFS(colony.cities.get(i) - 1, colonyIndex);
                if(cyclePath.size() != 0) break;
            }
            traps.add(cyclePath);
        }
        return traps;
    }

    public void printTraps(List<List<Integer>> traps) {
        System.out.println("Danger exploration conclusions:");
        for(int i = 0; i < traps.size(); i++){
            System.out.print("Colony " + (i + 1) + ": ");
            if(traps.get(i).size() == 0){
                System.out.println("Safe");
            } else {
                Collections.sort(traps.get(i));
                System.out.println("Dangerous. Cities on the dangerous path: " + traps.get(i));
            }

        }
    }

}
