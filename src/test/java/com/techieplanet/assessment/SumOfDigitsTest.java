package com.techieplanet.assessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SumOfDigitsTest {

    @Test
    @DisplayName("Sum: Sample input 1234445123444512344451234445123444512344451234445")
    public void testSumSampleInput() {
        String input = "1234445123444512344451234445123444512344451234445";
        assertEquals(161, SumOfDigits.sumOfDigits(input));
    }

    @Test
    @DisplayName("Sum: Test single digit")
    public void testSumSingleDigit() {
        assertEquals(5, SumOfDigits.sumOfDigits("5"));
    }

    @Test
    @DisplayName("Sum: Test two digits")
    public void testSumTwoDigits() {
        assertEquals(12, SumOfDigits.sumOfDigits("57"));
    }

    @Test
    @DisplayName("Sum: Test all zeros")
    public void testSumAllZeros() {
        assertEquals(0, SumOfDigits.sumOfDigits("0000"));
    }

    @Test
    @DisplayName("Sum: Test with zero")
    public void testSumWithZero() {
        assertEquals(10, SumOfDigits.sumOfDigits("1234"));
    }

    @Test
    @DisplayName("Sum: Test empty string")
    public void testSumEmptyString() {
        assertEquals(0, SumOfDigits.sumOfDigits(""));
    }

    @Test
    @DisplayName("Sum: Test null string")
    public void testSumNullString() {
        assertEquals(0, SumOfDigits.sumOfDigits(null));
    }

    @Test
    @DisplayName("Sum: Test large number")
    public void testSumLargeNumber() {
        assertEquals(45, SumOfDigits.sumOfDigits("123456789"));
    }

    @Test
    @DisplayName("Sum: Test repeated digits")
    public void testSumRepeatedDigits() {
        assertEquals(45, SumOfDigits.sumOfDigits("999999999"));
    }

    @Test
    @DisplayName("Sum: Test all nines")
    public void testSumAllNines() {
        assertEquals(27, SumOfDigits.sumOfDigits("999"));
    }

    @Test
    @DisplayName("Sum: Test starting with zero")
    public void testSumStartingWithZero() {
        assertEquals(6, SumOfDigits.sumOfDigits("0123"));
    }

    @Test
    @DisplayName("Sum: Test very long string")
    public void testSumVeryLongString() {
        String longString = "1".repeat(100);
        assertEquals(100, SumOfDigits.sumOfDigits(longString));
    }

    @Test
    @DisplayName("Sum: Test alternating pattern")
    public void testSumAlternatingPattern() {
        assertEquals(50, SumOfDigits.sumOfDigits("1010101010"));
    }

    @Test
    @DisplayName("Digital Root: Sample input 1234445123444512344451234445123444512344451234445")
    public void testDigitalRootSampleInput() {
        String input = "1234445123444512344451234445123444512344451234445";
        assertEquals(8, SumOfDigits.digitalRoot(input));
    }

    @Test
    @DisplayName("Digital Root: Test single digit already")
    public void testDigitalRootSingleDigit() {
        assertEquals(5, SumOfDigits.digitalRoot("5"));
    }

    @Test
    @DisplayName("Digital Root: Test two digits sum to single")
    public void testDigitalRootTwoDigits() {
        assertEquals(3, SumOfDigits.digitalRoot("12"));
    }

    @Test
    @DisplayName("Digital Root: Test requires multiple iterations")
    public void testDigitalRootMultipleIterations() {
        assertEquals(9, SumOfDigits.digitalRoot("9999"));
    }

    @Test
    @DisplayName("Digital Root: Test all zeros")
    public void testDigitalRootAllZeros() {
        assertEquals(0, SumOfDigits.digitalRoot("0000"));
    }

    @Test
    @DisplayName("Digital Root: Test 1234")
    public void testDigitalRootBasicNumber() {
        assertEquals(1, SumOfDigits.digitalRoot("1234"));
    }

    @Test
    @DisplayName("Digital Root: Test empty string")
    public void testDigitalRootEmptyString() {
        assertEquals(0, SumOfDigits.digitalRoot(""));
    }

    @Test
    @DisplayName("Digital Root: Test null string")
    public void testDigitalRootNullString() {
        assertEquals(0, SumOfDigits.digitalRoot(null));
    }

    @Test
    @DisplayName("Digital Root: Test 123456789")
    public void testDigitalRootLargeNumber() {
        assertEquals(9, SumOfDigits.digitalRoot("123456789"));
    }

    @Test
    @DisplayName("Digital Root: Test 38")
    public void testDigitalRootThirtyEight() {
        assertEquals(2, SumOfDigits.digitalRoot("38"));
    }

    @Test
    @DisplayName("Digital Root: Test 999")
    public void testDigitalRootThreeNines() {
        assertEquals(9, SumOfDigits.digitalRoot("999"));
    }

    @Test
    @DisplayName("Digital Root: Test 16")
    public void testDigitalRootSixteen() {
        assertEquals(7, SumOfDigits.digitalRoot("16"));
    }

    @Test
    @DisplayName("Digital Root: Test 942")
    public void testDigitalRootNineFortyTwo() {
        assertEquals(6, SumOfDigits.digitalRoot("942"));
    }

    @Test
    @DisplayName("Digital Root: Test very long string of ones")
    public void testDigitalRootVeryLongString() {
        String longString = "1".repeat(100);
        assertEquals(1, SumOfDigits.digitalRoot(longString));
    }

    @Test
    @DisplayName("Digital Root: Test 10")
    public void testDigitalRootTen() {
        assertEquals(1, SumOfDigits.digitalRoot("10"));
    }

    @Test
    @DisplayName("Digital Root: Test 99")
    public void testDigitalRootNinetyNine() {
        assertEquals(9, SumOfDigits.digitalRoot("99"));
    }

    @Test
    @DisplayName("Digital Root: Test 100")
    public void testDigitalRootHundred() {
        assertEquals(1, SumOfDigits.digitalRoot("100"));
    }

    @Test
    @DisplayName("Digital Root: Test 65536")
    public void testDigitalRootSixtyFiveThousand() {
        assertEquals(7, SumOfDigits.digitalRoot("65536"));
    }
}

