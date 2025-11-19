package com.techieplanet.assessment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatisticsServiceTest {

    private StatisticsService statisticsService;

    @BeforeEach
    void setUp() {
        statisticsService = new StatisticsService();
    }

    @Test
    @DisplayName("Calculate mean of scores")
    void testCalculateMean() {
        List<Double> scores = Arrays.asList(80.0, 90.0, 70.0, 85.0, 95.0);
        Double mean = statisticsService.calculateMean(scores);
        assertEquals(84.0, mean);
    }

    @Test
    @DisplayName("Calculate mean with empty list")
    void testCalculateMeanEmpty() {
        Double mean = statisticsService.calculateMean(Collections.emptyList());
        assertEquals(0.0, mean);
    }

    @Test
    @DisplayName("Calculate median with odd number of scores")
    void testCalculateMedianOdd() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 90.0);
        Double median = statisticsService.calculateMedian(scores);
        assertEquals(80.0, median);
    }

    @Test
    @DisplayName("Calculate median with even number of scores")
    void testCalculateMedianEven() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 90.0, 100.0);
        Double median = statisticsService.calculateMedian(scores);
        assertEquals(85.0, median);
    }

    @Test
    @DisplayName("Calculate mode with single most frequent score")
    void testCalculateMode() {
        List<Double> scores = Arrays.asList(80.0, 90.0, 80.0, 70.0, 80.0);
        Double mode = statisticsService.calculateMode(scores);
        assertEquals(80.0, mode);
    }

    @Test
    @DisplayName("Calculate mode with no repeating scores")
    void testCalculateModeNoRepeat() {
        List<Double> scores = Arrays.asList(70.0, 80.0, 90.0, 100.0);
        Double mode = statisticsService.calculateMode(scores);
        assertEquals(70.0, mode);
    }

    @Test
    @DisplayName("Calculate mode with empty list")
    void testCalculateModeEmpty() {
        Double mode = statisticsService.calculateMode(Collections.emptyList());
        assertEquals(0.0, mode);
    }
}

