import java.util.Arrays;
import java.util.Random;


public class SearchingAlgorithms {
    private static int linearSearch(int[] array, int value, int arraySize){
        for(int i = 0; i < arraySize; i++){
            if (array[i] == value) return i;
        }
        return -1;
    }
    public static double linearSearch(int arraySize, int mode){
        double totalRunningTime = 0;
        int[] array = Arrays.copyOf(Main.array, arraySize);
        if(mode == 1){ // to obtain sorted input data timing results
            array = Arrays.copyOf(Main.sortedArray, arraySize);
        }
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int target = rand.nextInt(arraySize - 1);
            double startTime = System.nanoTime();
            SearchingAlgorithms.linearSearch(array, array[target], arraySize);
            double endTime = System.nanoTime();
            totalRunningTime += endTime - startTime;
        }
        return totalRunningTime / 1000;
    }
    private static int binarySearch(int[] array, int value){
        int lo = 0;
        int hi = array.length - 1;
        while(hi - lo > 1){
            int mid = (hi + lo) / 2;
            if(array[mid] < value){
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        if(array[lo] == value) return lo;
        else if (array[hi] == value) return hi;
        return -1;
    }

    public static double binarySearch(int arraySize){
        double totalRunningTime = 0;
        int[] array = Arrays.copyOf(Main.sortedArray, arraySize);
        Random rand = new Random();
        for (int i = 0; i < 1000; i++) {
            int target = rand.nextInt(arraySize - 1);
            double startTime = System.nanoTime();
            SearchingAlgorithms.binarySearch(array, array[target]);
            double endTime = System.nanoTime();
            totalRunningTime += endTime - startTime;
        }
        return totalRunningTime / 1000;
    }
}
