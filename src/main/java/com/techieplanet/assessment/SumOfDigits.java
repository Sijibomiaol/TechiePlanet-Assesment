package com.techieplanet.assessment;

import java.util.Scanner;

public class SumOfDigits {

    public static int sumOfDigits(String digits) {
        if (digits == null || digits.isEmpty()) {
            return 0;
        }
        return sumOfDigitsHelper(digits, 0);
    }

    private static int sumOfDigitsHelper(String digits, int index) {
        if (index >= digits.length()) {
            return 0;
        }
        int currentDigit = Character.getNumericValue(digits.charAt(index));
        return currentDigit + sumOfDigitsHelper(digits, index + 1);
    }

    public static int digitalRoot(String digits) {
        if (digits == null || digits.isEmpty()) {
            return 0;
        }
        int sum = sumOfDigits(digits);
        if (sum < 10) {
            return sum;
        }
        return digitalRoot(String.valueOf(sum));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            String input = scanner.nextLine().trim();
            int sum = sumOfDigits(input);
            int root = digitalRoot(input);
            System.out.println("Sum of digits: " + sum);
            System.out.println("Digital root: " + root);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        } finally {
            scanner.close();
        }
    }
}

