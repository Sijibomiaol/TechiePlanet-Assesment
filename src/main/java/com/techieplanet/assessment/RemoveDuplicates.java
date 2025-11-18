package com.techieplanet.assessment;

public class RemoveDuplicates {
    public static int[][] removeDuplicates(int[][] arr) {
        if (arr == null || arr.length == 0) {
            return arr;
        }

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null && arr[i].length > 0) {
                removeDuplicateFromRow(arr[i]);
            }
        }
        return arr;
    }

    private static void removeDuplicateFromRow(int[] row) {
        int [] seen = new int [row.length];
        int seenCount = 0;
        for (int i = 0; i < row.length; i++) {
            int currentNumber = row[i];
            if (hasSeenNumber(seen, seenCount, currentNumber)) {
                row[i] = 0;
            }
            else  {
                seen[seenCount] = currentNumber;
                seenCount++;
            }
        }

    }


    private  static boolean hasSeenNumber(int [] seen,int count, int target) {
        for (int i = 0; i < count; i++) {
            if (seen[i] == target) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] arr = {
                {1, 3, 1, 2, 3, 4, 4, 3, 5},
                {1, 1, 1, 1, 1, 1, 1}
        };

        System.out.println("Original Array:");
        printArray(arr);

        int[][] result = removeDuplicates(arr);

        System.out.println("\nArray after removing duplicates:");
        printArray(result);
    }

    private static void printArray(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            System.out.print("[");
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j]);
                if (j < arr[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
}

