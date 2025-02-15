package dev.idz.test.util;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class MathHelperTest {

    @Test
    void findNthLargestIntUniqueSorted() {

        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        assertEquals(10, MathHelper.findNthLargestInt(values, 0));
        assertEquals(9, MathHelper.findNthLargestInt(values, 1));
        assertEquals(8, MathHelper.findNthLargestInt(values, 2));
    }

    @Test
    void findNthLargestIntNonUniqueSorted() {

        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 8, 9, 9, 10, 10};

        assertEquals(10, MathHelper.findNthLargestInt(values, 0));
        assertEquals(9, MathHelper.findNthLargestInt(values, 1));
        assertEquals(8, MathHelper.findNthLargestInt(values, 2));
    }

    @Test
    void findNthLargestIntNonUniqueNonSorted() {

        int[] values = {9, 9, 1, 2, 3, 8, 8, 4, 5, 6, 10, 10, 7};

        assertEquals(10, MathHelper.findNthLargestInt(values, 0));
        assertEquals(9, MathHelper.findNthLargestInt(values, 1));
        assertEquals(8, MathHelper.findNthLargestInt(values, 2));
    }

    private static final int BIG_TEST_SIZE = 1_000_000;

    @Test
    void findNthLargestIntNonUniqueNonSortedBig() {

        int[] values = new int[BIG_TEST_SIZE];

        Random random = new Random();
        for (int i = 0; i < BIG_TEST_SIZE; i++) {
            values[i] = random.nextInt();
        }

        int[] sorted = Arrays.copyOf(values, BIG_TEST_SIZE);
        Arrays.parallelSort(sorted);

        assertEquals(sorted[BIG_TEST_SIZE - 1], MathHelper.findNthLargestInt(values, 0));
        assertEquals(sorted[BIG_TEST_SIZE - 2], MathHelper.findNthLargestInt(values, 1));
        assertEquals(sorted[BIG_TEST_SIZE - 3], MathHelper.findNthLargestInt(values, 2));
    }
}