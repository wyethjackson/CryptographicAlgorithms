package com.module7;

import java.util.ArrayList;
import java.util.Comparator;

public class Sorter implements Comparator<double[]> {
    // Used for sorting in ascending order of
    // roll number
    public int compare(double[] a, double[] b)
    {
        return (int) (a[1] - b[1]);
    }
}
