import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with an Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int lo = 0;
        int hi = index;
        int maxCompatibleIndex = -1;
        while(hi  - lo > 1){
            int mid = (hi + lo) / 2;
            if(taskArray[index].isCompatible(taskArray[mid])){
                maxCompatibleIndex = mid;
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        if(taskArray[index].isCompatible(taskArray[lo])) return lo;
        else if(taskArray[index].isCompatible(taskArray[hi])) return hi;
        return maxCompatibleIndex;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for(int i = 0; i < taskArray.length; i++){
            compatibility[i] = binarySearch(i);
        }
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        calculateCompatibility();
        System.out.println("Calculating max array\n---------------------");
        calculateMaxWeight(taskArray.length - 1);
        System.out.println("\nCalculating the dynamic solution \n--------------------------------");
        solveDynamic(taskArray.length - 1);
        System.out.println("\nDynamic Schedule\n----------------");
        for(Task task: planDynamic) System.out.println(task);
        return planDynamic;
    }

    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if(i < 0) return;
        System.out.println("Called solveDynamic(" + i + ")");
        if(i == 0){
            planDynamic.add(taskArray[0]);
            return;
        }
        if(maxWeight[i] > maxWeight[i - 1]){
            solveDynamic(compatibility[i]);
            planDynamic.add(taskArray[i]);
        } else {
            solveDynamic(i - 1);
        }
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight(" + i + ")");
        if(i < 0) return 0.0;
        if(maxWeight[i] != null) return maxWeight[i];
        Double intervalIncluded = taskArray[i].getWeight() + calculateMaxWeight(compatibility[i]);
        Double intervalExcluded = calculateMaxWeight(i - 1);
        if(intervalIncluded > intervalExcluded){
            maxWeight[i] = intervalIncluded;
        } else {
            maxWeight[i] = intervalExcluded;
        }
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        Task lastAddedTask = taskArray[0];
        System.out.println("Greedy Schedule\n---------------\n" + taskArray[0]);
        planGreedy.add(0, taskArray[0]);
        for(int i = 0; i < taskArray.length; i++){
            if(taskArray[i].isCompatible(lastAddedTask)){
                planGreedy.add(taskArray[i]);
                System.out.println(taskArray[i]);
                lastAddedTask = taskArray[i];
            }
        }
        return planGreedy;
    }
}
