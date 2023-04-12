import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class SortingAlgorithms {
    public static void exchange(int[] array, int index1, int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static int findMax(int[] array){
        int max = array[0];
        for(int num: array){ if (num > max) max = num;}
        return max;
    }

    private static void selectionSort(int[] array, int arraySize){
        for(int i = 0; i < arraySize; i++){
            int min = i;
            for(int j = i + 1; j < arraySize; j++){
                if(array[j] < array[min]) min = j;
            }
            if(min != i) exchange(array, min, i);
        }
        Main.sortedArray = array;
    }

    public static double selectionSort(int arraySize, int mode){
        double totalRunningTime = 0;
        int[] array = Arrays.copyOf(Main.array, arraySize);
        if(mode == 1){ // to obtain sorted input data timing results
            array = Arrays.copyOf(Main.sortedArray, arraySize);
        } else if (mode == 2){ // to obtain reversely sorted input data timing results
            array = Arrays.copyOf(Main.sortedArray, arraySize);
            for(int i = 0, j = arraySize - 1; i < j; i++, j--){
                exchange(array, i , j);
            }
            Main.reverseSortedArray = Arrays.copyOf(array, arraySize); //assign this reverse sorted copy to static Main.reverseSortedArray
        }
        for (int iterationNum = 0; iterationNum < 10; iterationNum++){
            int[] tempArray = Arrays.copyOf(array, arraySize);
            double startTime = System.currentTimeMillis();
            selectionSort(tempArray, arraySize);
            double endTime   = System.currentTimeMillis();
            totalRunningTime += endTime - startTime;
        }
        return totalRunningTime / 10;
    }

    private static int partition(int[] array, int lo, int hi){
        int pivot = array[hi];
        int i = lo - 1;
        for(int j = lo; j <= hi; j++){
            if (array[j] < pivot){
                i++;
                exchange(array, i, j);
            }
        }
        exchange(array, i + 1, hi);
        return i + 1;
    }

    private static void quickSort(int[] array, int lo, int hi){
        int stackSize = hi - lo + 1;
        int[] stack = new int[stackSize];
        int top = -1;
        stack[++top] = lo;
        stack[++top] = hi;
        while(top > 0){
            hi = stack[top--];
            lo = stack[top--];
            int pivot = partition(array, lo, hi);
            if(pivot - 1 > lo){
                stack[++top] = lo;
                stack[++top] = pivot - 1;
            }
            if (pivot + 1 < hi) {
                stack[++top] = pivot + 1;
                stack[++top] = hi;
            }
        }
    }
    public static double quickSort(int arraySize, int mode){
        double totalRunningTime = 0;
        int[] array = Arrays.copyOf(Main.array, arraySize);
        if(mode == 1){ // to obtain sorted input data timing results
            array = Arrays.copyOf(Main.sortedArray, arraySize);
        } else if (mode == 2){ // to obtain reversely sorted input data timing results
            array = Arrays.copyOf(Main.reverseSortedArray, arraySize);
        }
        for (int iterationNum = 0; iterationNum < 10; iterationNum++){
            int[] tempArray = Arrays.copyOf(array, arraySize);
            double startTime = System.currentTimeMillis();
            quickSort(tempArray, 0, arraySize - 1);
            double endTime = System.currentTimeMillis();
            totalRunningTime += endTime - startTime;
        }
        return totalRunningTime / 10;
    }

    private static int hash(int i, int max, int numOfBuckets){
        return i / max * (numOfBuckets - 1);
    }

    private static int[] bucketSort(int[] array){
        int numOfBuckets = (int) Math.sqrt(array.length);
        ArrayList<Integer>[] buckets = new ArrayList[numOfBuckets];
        for(int k = 0; k < numOfBuckets; k++) buckets[k] = new ArrayList<>();
        int max = findMax(array);
        for(int i = 0; i < array.length; i++){
            buckets[hash(i, max, numOfBuckets)].add(array[i]);
        }
        int[] sortedArray = new int[array.length];
        int sortedArrayIndex = 0;
        for(ArrayList<Integer> aList: buckets){
            Collections.sort(aList);
            for(int num: aList){
                sortedArray[sortedArrayIndex] = num;
                sortedArrayIndex++;
            }
        }
        return sortedArray;
    }

    public static double bucketSort(int arraySize, int mode){
        double totalRunningTime = 0;
        int[] array = Arrays.copyOf(Main.array, arraySize);
        if(mode == 1){ // to obtain sorted input data timing results
            array = Arrays.copyOf(Main.sortedArray, arraySize);
        } else if (mode == 2){ // to obtain reversely sorted input data timing results
            array = Arrays.copyOf(Main.reverseSortedArray, arraySize);
        }
        for (int iterationNum = 0; iterationNum < 10; iterationNum++){
            int[] tempArray = Arrays.copyOf(array, arraySize);
            double startTime = System.currentTimeMillis();
            tempArray = bucketSort(tempArray);
            double endTime   = System.currentTimeMillis();
            totalRunningTime += endTime - startTime;
        }
        return totalRunningTime / 10;
    }
}
