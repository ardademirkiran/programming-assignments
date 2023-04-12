import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static int[] array;
    public static int[] sortedArray;
    public static int[] reverseSortedArray;
    public static void main(String[] args) throws IOException {
        double[][] yAxisRandom = new double[3][10];
        double[][] yAxisSorted = new double[3][10];
        double[][] yAxisReverse = new double[3][10];
        double[][] yAxisSearch = new double[3][10];
        int[] inputAxis = {512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 251282};
        Scanner scanner = new Scanner(new File(args[0]));
        scanner.useDelimiter("\r\n");
        scanner.next();
        int[] allData = new int[250000];
        int[] sizes = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        for(int i = 0; i < 250000; i++){
            allData[i] = Integer.parseInt(scanner.next().split(",")[6]);
        }
        for(int j = 0 ; j < 10; j++ ){
            array = Arrays.copyOfRange(allData, 0, sizes[j]);
            yAxisRandom[0][j] = SortingAlgorithms.selectionSort(sizes[j], 0);
            yAxisRandom[1][j] = SortingAlgorithms.quickSort(sizes[j], 0);
            yAxisRandom[2][j] = SortingAlgorithms.bucketSort(sizes[j], 0);
            yAxisSorted[0][j] = SortingAlgorithms.selectionSort(sizes[j], 1);
            yAxisSorted[1][j] = SortingAlgorithms.quickSort(sizes[j], 1);
            yAxisSorted[2][j] = SortingAlgorithms.bucketSort(sizes[j], 1);
            yAxisReverse[0][j] = SortingAlgorithms.selectionSort(sizes[j], 2);
            yAxisReverse[1][j] = SortingAlgorithms.quickSort(sizes[j], 2);
            yAxisReverse[2][j] = SortingAlgorithms.bucketSort(sizes[j], 2);
            yAxisSearch[0][j] =  SearchingAlgorithms.linearSearch(sizes[j], 0);
            yAxisSearch[1][j] =  SearchingAlgorithms.linearSearch(sizes[j], 1);
            yAxisSearch[2][j] =  SearchingAlgorithms.binarySearch(sizes[j]);
            System.out.println("Algorithm:\tSelection Sort\t Input Size:\t" + sizes[j] + "\tInput Type:\tRandom Data" + "\t Running Time(ms):\t" + yAxisRandom[0][j]);
            System.out.println("Algorithm:\tSelection Sort\t Input Size:\t" + sizes[j] + "\tInput Type:\tSorted Data" + "\tRunning Time(ms):\t" + yAxisSorted[0][j]);
            System.out.println("Algorithm:\tSelection Sort\t Input Size:\t" + sizes[j] + "\tInput Type:\tReverse Sorted Data" + "\tRunning Time(ms):\t" + yAxisReverse[0][j]);
            System.out.println("Algorithm:\tQuickSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tRandom Data" + "\tRunning Time(ms):\t" + yAxisRandom[1][j]);
            System.out.println("Algorithm:\tQuickSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tSorted Data" + "\tRunning Time(ms):\t" + yAxisSorted[1][j]);
            System.out.println("Algorithm:\tQuickSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tReverse Sorted" + "\tRunning Time(ms):\t" + yAxisReverse[1][j]);
            System.out.println("Algorithm:\tBucketSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tRandom Data" + "\tRunning Time(ms):\t" + yAxisRandom[2][j]);
            System.out.println("Algorithm:\tBucketSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tSorted Data" + "\tRunning Time(ms):\t" + yAxisSorted[2][j]);
            System.out.println("Algorithm:\tBucketSort\tInput Size:\t" + sizes[j] + "\tInput Type:\tReverse Sorted Data" + "\tRunning Time(ms):\t" + yAxisReverse[2][j]);
            System.out.println("Algorithm:\tLinearSearch\tInput Size:\t" + sizes[j] + "\tInput Type:\tRandom Data" + "\tRunning time(ns):\t" + yAxisSearch[0][j]);
            System.out.println("Algorithm:\tLinearSearch\tInput Size:\t" + sizes[j] + "\tInput Type:\tSorted Data" + "\tRunning time(ns):\t" + yAxisSearch[1][j]);
            System.out.println("Algorithm:\tBinary Search\tInput Size:\t" + sizes[j] + "\tInput Type:\tSorted Data" + "\tRunning time(ns):\t" + yAxisSearch[2][j]);
            System.out.println("\n");
        }
        showAndSaveChart("Random_Data", inputAxis, yAxisRandom, false);
        showAndSaveChart("Sorted_Data", inputAxis, yAxisSorted, false);
        showAndSaveChart("Reverse_Sorted_Data", inputAxis, yAxisReverse, false);
        showAndSaveChart("Search_Algorithms", inputAxis, yAxisSearch, true);
    }

    public static void showAndSaveChart(String title, int[] xAxis, double[][] yAxis, boolean isSearch) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle("Time in Milliseconds").xAxisTitle("Input Size").build();

        // Convert x axis to double[]
        double[] doubleX = Arrays.stream(xAxis).asDoubleStream().toArray();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        if(isSearch){
            chart.addSeries("Random Data Linear Search", doubleX, yAxis[0]);
            chart.addSeries("Sorted Data Linear Search", doubleX, yAxis[1]);
            chart.addSeries("Sorted Data Binary Search", doubleX, yAxis[2]);
        } else {
            chart.addSeries("SelectionSort", doubleX, yAxis[0]);
            chart.addSeries("QuickSort", doubleX, yAxis[1]);
            chart.addSeries("BucketSort", doubleX, yAxis[2]);
        }

        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
