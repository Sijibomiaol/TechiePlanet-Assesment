package com.techieplanet.assessment.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Math score is required")
    @Min(value = 0, message = "Math score must be between 0 and 100")
    @Max(value = 100, message = "Math score must be between 0 and 100")
    private Double mathScore;

    @NotNull(message = "English score is required")
    @Min(value = 0, message = "English score must be between 0 and 100")
    @Max(value = 100, message = "English score must be between 0 and 100")
    private Double englishScore;

    @NotNull(message = "Science score is required")
    @Min(value = 0, message = "Science score must be between 0 and 100")
    @Max(value = 100, message = "Science score must be between 0 and 100")
    private Double scienceScore;

    @NotNull(message = "History score is required")
    @Min(value = 0, message = "History score must be between 0 and 100")
    @Max(value = 100, message = "History score must be between 0 and 100")
    private Double historyScore;

    @NotNull(message = "Geography score is required")
    @Min(value = 0, message = "Geography score must be between 0 and 100")
    @Max(value = 100, message = "Geography score must be between 0 and 100")
    private Double geographyScore;
}

