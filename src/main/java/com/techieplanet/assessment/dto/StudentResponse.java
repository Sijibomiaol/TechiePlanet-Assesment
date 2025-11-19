package com.techieplanet.assessment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentResponse {

    private Long id;
    private String name;
    private Double mathScore;
    private Double englishScore;
    private Double scienceScore;
    private Double historyScore;
    private Double geographyScore;
}

