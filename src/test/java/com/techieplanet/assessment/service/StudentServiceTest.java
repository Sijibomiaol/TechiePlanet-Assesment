package com.techieplanet.assessment.service;

import com.techieplanet.assessment.dto.StudentReportResponse;
import com.techieplanet.assessment.dto.StudentRequest;
import com.techieplanet.assessment.dto.StudentResponse;
import com.techieplanet.assessment.entity.Student;
import com.techieplanet.assessment.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private StudentService studentService;

    private Student student;
    private StudentRequest studentRequest;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setId(1L);
        student.setName("John Doe");
        student.setMathScore(85.0);
        student.setEnglishScore(90.0);
        student.setScienceScore(78.0);
        student.setHistoryScore(88.0);
        student.setGeographyScore(92.0);

        studentRequest = new StudentRequest();
        studentRequest.setName("John Doe");
        studentRequest.setMathScore(85.0);
        studentRequest.setEnglishScore(90.0);
        studentRequest.setScienceScore(78.0);
        studentRequest.setHistoryScore(88.0);
        studentRequest.setGeographyScore(92.0);
    }

    @Test
    @DisplayName("Create student successfully")
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentResponse response = studentService.createStudent(studentRequest);

        assertNotNull(response);
        assertEquals("John Doe", response.getName());
        assertEquals(85.0, response.getMathScore());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    @DisplayName("Get student reports with pagination")
    void testGetStudentReports() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Student> studentPage = new PageImpl<>(Arrays.asList(student));
        
        when(studentRepository.findAll(pageable)).thenReturn(studentPage);
        when(statisticsService.calculateMean(any())).thenReturn(86.6);
        when(statisticsService.calculateMedian(any())).thenReturn(88.0);
        when(statisticsService.calculateMode(any())).thenReturn(85.0);

        Page<StudentReportResponse> reports = studentService.getStudentReports(null, pageable);

        assertNotNull(reports);
        assertEquals(1, reports.getTotalElements());
        verify(studentRepository, times(1)).findAll(pageable);
    }

    @Test
    @DisplayName("Get student report by ID")
    void testGetStudentReport() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(statisticsService.calculateMean(any())).thenReturn(86.6);
        when(statisticsService.calculateMedian(any())).thenReturn(88.0);
        when(statisticsService.calculateMode(any())).thenReturn(85.0);

        StudentReportResponse report = studentService.getStudentReport(1L);

        assertNotNull(report);
        assertEquals("John Doe", report.getName());
        assertEquals(5, report.getScores().size());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get student report throws exception when not found")
    void testGetStudentReportNotFound() {
        when(studentRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> studentService.getStudentReport(999L));
        verify(studentRepository, times(1)).findById(999L);
    }
}

