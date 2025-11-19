package com.techieplanet.assessment.service;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    public Double calculateMean(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        return scores.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public Double calculateMedian(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        
        List<Double> sortedScores = scores.stream()
                .sorted()
                .collect(Collectors.toList());
        
        int size = sortedScores.size();
        if (size % 2 == 0) {
            return (sortedScores.get(size / 2 - 1) + sortedScores.get(size / 2)) / 2.0;
        } else {
            return sortedScores.get(size / 2);
        }
    }

    public Double calculateMode(List<Double> scores) {
        if (scores == null || scores.isEmpty()) {
            return 0.0;
        }
        
        Map<Double, Long> frequencyMap = scores.stream()
                .collect(Collectors.groupingBy(score -> score, Collectors.counting()));
        
        long maxFrequency = frequencyMap.values().stream()
                .max(Long::compare)
                .orElse(0L);
        
        if (maxFrequency == 1) {
            return scores.get(0);
        }
        
        return frequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() == maxFrequency)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(0.0);
    }
}

