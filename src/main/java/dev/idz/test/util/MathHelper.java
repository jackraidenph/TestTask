package dev.idz.test.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Static utilities class for math
 */
public class MathHelper {

    /**
     * Find the Nth largest integer in a collection of Integers
     *
     * @param values A collection of integers
     * @param n      Offset of the largest integer to find
     * @return Nth largest integer
     */
    public static int findNthLargestInt(Collection<Integer> values, int n) {
        //We need to find a unique largest integer, so we convert the collection to a set
        Set<Integer> set = values instanceof Set<Integer>
                ? (Set<Integer>) values
                : new HashSet<>(values);

        int[] arr = new int[set.size()];

        int idx = 0;
        for (Integer i : set) {
            arr[idx] = i;
            idx++;
        }

        return nthSmallestQuickSelect(arr, values.size() - n);
    }

    /**
     * Find the Nth largest integer in an array of Integers
     *
     * @param values An array of integers
     * @param n      Offset of the largest integer to find
     * @return Nth largest integer
     */
    public static int findNthLargestInt(Integer[] values, int n) {
        return findNthLargestInt(Set.of(values), n);
    }

    /**
     * Find the Nth largest integer in a primitive int array.
     * Firstly, we make a set from the array to remove duplicates.
     * If no duplicates were removed(The size is the same) - we proceed without redundant re-copying.
     *
     * @param values An array of integers
     * @param n      Offset of the largest integer to find
     * @return Nth largest integer
     */
    public static int findNthLargestInt(int[] values, int n) {
        Set<Integer> set = new HashSet<>();
        for (int i : values) {
            set.add(i);
        }

        if (set.size() == values.length) {
            return nthSmallestQuickSelect(values, values.length - n);
        }

        return findNthLargestInt(set, n);
    }


    //A helper method to partition a primitive int array
    private static int partition(int[] arr, int start, int end) {
        int pivot = arr[end], pivotloc = start;
        for (int i = start; i <= end; i++) {
            if (arr[i] < pivot) {
                int temp = arr[i];
                arr[i] = arr[pivotloc];
                arr[pivotloc] = temp;
                pivotloc++;
            }
        }

        int temp = arr[end];
        arr[end] = arr[pivotloc];
        arr[pivotloc] = temp;

        return pivotloc;
    }

    //QuickSelect algorith for finding the Nth smallest integer
    private static int nthSmallestQuickSelect(int[] arr, int start, int end, int k) {
        int partition = partition(arr, start, end);

        if (partition == k - 1) {
            return arr[partition];
        } else if (partition < k - 1) {
            return nthSmallestQuickSelect(arr, partition + 1, end, k);
        } else {
            return nthSmallestQuickSelect(arr, start, partition - 1, k);
        }
    }

    //QuickSelect algorith for finding the Nth smallest integer, start and end are 0 and array's end respectively
    private static int nthSmallestQuickSelect(int[] arr, int n) {
        return nthSmallestQuickSelect(arr, 0, arr.length - 1, n);
    }
}
