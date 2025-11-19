package com.techieplanet.assessment.repository;

import com.techieplanet.assessment.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);
}

