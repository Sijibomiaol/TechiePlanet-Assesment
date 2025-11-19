package com.techieplanet.assessment.controller;

import com.techieplanet.assessment.dto.StudentReportResponse;
import com.techieplanet.assessment.dto.StudentRequest;
import com.techieplanet.assessment.dto.StudentResponse;
import com.techieplanet.assessment.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Tag(name = "Student Management", description = "APIs for managing student scores")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @Operation(summary = "Create a new student", description = "Add a new student with scores in 5 subjects")
    public ResponseEntity<StudentResponse> createStudent(@Valid @RequestBody StudentRequest request) {
        StudentResponse response = studentService.createStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/reports")
    @Operation(summary = "Get student reports", description = "Get paginated student reports with statistics")
    public ResponseEntity<Page<StudentReportResponse>> getStudentReports(
            @Parameter(description = "Filter by student name") @RequestParam(required = false) String name,
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Sort by field") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Sort direction") @RequestParam(defaultValue = "ASC") String sortDir
    ) {
        Sort.Direction direction = sortDir.equalsIgnoreCase("DESC") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        
        Page<StudentReportResponse> reports = studentService.getStudentReports(name, pageable);
        return ResponseEntity.ok(reports);
    }

    @GetMapping("/reports/{id}")
    @Operation(summary = "Get student report by ID", description = "Get detailed report for a specific student")
    public ResponseEntity<StudentReportResponse> getStudentReport(
            @Parameter(description = "Student ID") @PathVariable Long id
    ) {
        StudentReportResponse report = studentService.getStudentReport(id);
        return ResponseEntity.ok(report);
    }
}

