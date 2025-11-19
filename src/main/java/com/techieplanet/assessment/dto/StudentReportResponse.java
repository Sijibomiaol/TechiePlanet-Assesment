package com.techieplanet.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentReportResponse {

    private Long id;
    private String name;
    private Map<String, Double> scores;
    private Double meanScore;
    private Double medianScore;
    private Double modeScore;
}

