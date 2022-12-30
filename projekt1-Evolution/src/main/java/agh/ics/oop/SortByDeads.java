package agh.ics.oop;

import java.util.Comparator;
import java.util.Random;

public class SortByDeads implements Comparator<int[]> {
    public int compare(int[] a, int[] b) {
        Random generator = new Random();
        return a[2] - b[2] != 0 ? a[2] - b[2] : generator.nextInt(3) - 1;
    }
}
