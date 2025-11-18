package com.techieplanet.assessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RemoveDuplicatesTest {

    @Test
    @DisplayName("Test with duplicates in each row")
    public void testRemoveDuplicatesBasic() {
        int[][] arr = {
            {1, 2, 3, 2, 4},
            {5, 5, 6, 7, 8},
            {9, 10, 9, 11, 12}
        };
        int[][] expected = {
            {1, 2, 3, 0, 4},
            {5, 0, 6, 7, 8},
            {9, 10, 0, 11, 12}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with no duplicates")
    public void testNoDuplicates() {
        int[][] arr = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10}
        };
        int[][] expected = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with all duplicates in a row")
    public void testAllDuplicates() {
        int[][] arr = {
            {5, 5, 5, 5, 5},
            {3, 3, 3}
        };
        int[][] expected = {
            {5, 0, 0, 0, 0},
            {3, 0, 0}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with empty array")
    public void testEmptyArray() {
        int[][] arr = {};
        int[][] expected = {};
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with null array")
    public void testNullArray() {
        int[][] arr = null;
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertNull(result);
    }

    @Test
    @DisplayName("Test with single element rows")
    public void testSingleElementRows() {
        int[][] arr = {
            {1},
            {2},
            {3}
        };
        int[][] expected = {
            {1},
            {2},
            {3}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with empty rows")
    public void testEmptyRows() {
        int[][] arr = {
            {},
            {1, 2, 3},
            {}
        };
        int[][] expected = {
            {},
            {1, 2, 3},
            {}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with multiple duplicates of same number")
    public void testMultipleDuplicates() {
        int[][] arr = {
            {1, 1, 1, 2, 2, 2, 3}
        };
        int[][] expected = {
            {1, 0, 0, 2, 0, 0, 3}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with negative numbers")
    public void testNegativeNumbers() {
        int[][] arr = {
            {-1, -2, -1, -3},
            {-5, -5, -6}
        };
        int[][] expected = {
            {-1, -2, 0, -3},
            {-5, 0, -6}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with zeros in original array")
    public void testWithZeros() {
        int[][] arr = {
            {0, 1, 2, 0, 3},
            {0, 0, 4}
        };
        int[][] expected = {
            {0, 1, 2, 0, 3},
            {0, 0, 4}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with large numbers")
    public void testLargeNumbers() {
        int[][] arr = {
            {1000, 2000, 1000, 3000},
            {5000, 6000, 5000}
        };
        int[][] expected = {
            {1000, 2000, 0, 3000},
            {5000, 6000, 0}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test with two elements where second is duplicate")
    public void testTwoElementsDuplicate() {
        int[][] arr = {
            {7, 7}
        };
        int[][] expected = {
            {7, 0}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }

    @Test
    @DisplayName("Test preserves first occurrence")
    public void testPreservesFirstOccurrence() {
        int[][] arr = {
            {10, 20, 30, 20, 10}
        };
        int[][] expected = {
            {10, 20, 30, 0, 0}
        };
        int[][] result = RemoveDuplicates.removeDuplicates(arr);
        assertArrayEquals(expected, result);
    }
}

