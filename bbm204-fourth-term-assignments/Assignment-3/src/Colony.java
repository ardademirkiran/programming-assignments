import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Colony {
    int colonyNum;
    int maxCityIndex = 0;
    public List<Integer> cities = new ArrayList<>();


    public HashMap<Integer, List<Integer>> roadNetwork = new HashMap<>();

    public Colony(int colonyNum){
        this.colonyNum = colonyNum;
    }
}
