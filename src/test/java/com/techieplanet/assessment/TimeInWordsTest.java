package com.techieplanet.assessment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeInWordsTest {

    @Test
    @DisplayName("Edge Case: 0 minutes (o' clock) ")
    public void timeTestZeroMinutes() {
        assertEquals("five o' clock", TimeInWords.timmeWords(5, 0));
        assertEquals("twelve o' clock", TimeInWords.timmeWords(12, 0));
        assertEquals("one o' clock", TimeInWords.timmeWords(1, 0));

    }
    @Test
    @DisplayName("Edge Case: 1 minute (singular) ")
    public  void timeTestOneMinutePast() {
        assertEquals("one minute past five", TimeInWords.timmeWords(5, 1));
        assertEquals("one minute past four", TimeInWords.timmeWords(4, 1));
    }
    @Test
    @DisplayName("Edge Case: 59 minutes (one minute to)")
    public void  timeTestOneMinuteTo() {
        assertEquals("one minute to six", TimeInWords.timmeWords(5, 59));
        assertEquals("one minute to five", TimeInWords.timmeWords(4, 59));
    }

    @Test
    @DisplayName("Edge Case: 15 minutes (quarter past)")
    public void timeQuarterPast() {
        assertEquals("quarter past five", TimeInWords.timmeWords(5, 15));
        assertEquals("quarter past four", TimeInWords.timmeWords(4, 15));
    }


    @Test
    @DisplayName("Edge Case: 30 minutes (half past)")
    public   void timeHalfPast() {
        assertEquals("half past five", TimeInWords.timmeWords(5, 30));
        assertEquals("half past two", TimeInWords.timmeWords(2, 30));
    }

    @Test
    @DisplayName("Edge Case : 45 minutes (quarter to)")
    public void timeQuarterTo() {
        assertEquals("quarter to eight", TimeInWords.timmeWords(7, 45));
        assertEquals("quarter to four", TimeInWords.timmeWords(3, 45));

    }

    @Test
    @DisplayName("Sample: 5:47 => thirteen minutes to six")
    public void timeThirteenMinutesToSix() {
        assertEquals("thirteen minutes to six", TimeInWords.timmeWords(5, 47));

    }

    @Test
    @DisplayName("Sample: 5:28 => twenty eight minutes past five")
    public void testSamplePast() {
        assertEquals("twenty eight minutes past five", TimeInWords.timmeWords(5, 28));
    }

    @Test
    @DisplayName("Sample: 5:10 => ten minutes past five")
    public void testTenMinutesPast() {
        assertEquals("ten minutes past five", TimeInWords.timmeWords(5, 10));
    }

    @Test
    @DisplayName("Sample: 5:40 => twenty minutes to six")
    public void testTwentyMinutesTo() {
        assertEquals("twenty minutes to six", TimeInWords.timmeWords(5, 40));
    }

    @Test
    @DisplayName("Boundary: Hour = 1 (minimum valid hour)")
    public void testMinimumHour() {
        assertEquals("one o' clock", TimeInWords.timmeWords(1, 0));
        assertEquals("quarter past one", TimeInWords.timmeWords(1, 15));
    }

    @Test
    @DisplayName("Boundary: Hour = 12 (maximum valid hour)")
    public void testMaximumHour() {
        assertEquals("twelve o' clock", TimeInWords.timmeWords(12, 0));
        assertEquals("half past twelve", TimeInWords.timmeWords(12, 30));
    }



}
