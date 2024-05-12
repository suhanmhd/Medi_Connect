package com.example.mediconnect.UserService.repository;


import com.example.mediconnect.UserService.entity.doctor.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface EducationRepository extends JpaRepository<Education, UUID> {
    // You can define custom queries or methods here if needed
}
