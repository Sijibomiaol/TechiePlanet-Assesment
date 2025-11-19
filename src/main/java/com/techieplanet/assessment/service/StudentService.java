package com.techieplanet.assessment.service;

import com.techieplanet.assessment.dto.StudentReportResponse;
import com.techieplanet.assessment.dto.StudentRequest;
import com.techieplanet.assessment.dto.StudentResponse;
import com.techieplanet.assessment.entity.Student;
import com.techieplanet.assessment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final StatisticsService statisticsService;

    @Transactional
    public StudentResponse createStudent(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setMathScore(request.getMathScore());
        student.setEnglishScore(request.getEnglishScore());
        student.setScienceScore(request.getScienceScore());
        student.setHistoryScore(request.getHistoryScore());
        student.setGeographyScore(request.getGeographyScore());
        
        Student savedStudent = studentRepository.save(student);
        return mapToResponse(savedStudent);
    }

    @Transactional(readOnly = true)
    public Page<StudentReportResponse> getStudentReports(String name, Pageable pageable) {
        Page<Student> students;
        
        if (name != null && !name.isBlank()) {
            students = studentRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            students = studentRepository.findAll(pageable);
        }
        
        return students.map(this::mapToReportResponse);
    }

    @Transactional(readOnly = true)
    public StudentReportResponse getStudentReport(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        return mapToReportResponse(student);
    }

    private StudentResponse mapToResponse(Student student) {
        return StudentResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .mathScore(student.getMathScore())
                .englishScore(student.getEnglishScore())
                .scienceScore(student.getScienceScore())
                .historyScore(student.getHistoryScore())
                .geographyScore(student.getGeographyScore())
                .build();
    }

    private StudentReportResponse mapToReportResponse(Student student) {
        List<Double> scores = Arrays.asList(
                student.getMathScore(),
                student.getEnglishScore(),
                student.getScienceScore(),
                student.getHistoryScore(),
                student.getGeographyScore()
        );
        
        Map<String, Double> scoresMap = new LinkedHashMap<>();
        scoresMap.put("Math", student.getMathScore());
        scoresMap.put("English", student.getEnglishScore());
        scoresMap.put("Science", student.getScienceScore());
        scoresMap.put("History", student.getHistoryScore());
        scoresMap.put("Geography", student.getGeographyScore());
        
        return StudentReportResponse.builder()
                .id(student.getId())
                .name(student.getName())
                .scores(scoresMap)
                .meanScore(statisticsService.calculateMean(scores))
                .medianScore(statisticsService.calculateMedian(scores))
                .modeScore(statisticsService.calculateMode(scores))
                .build();
    }
}

