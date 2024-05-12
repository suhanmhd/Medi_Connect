package com.example.mediconnect.UserService.repository;

import com.example.mediconnect.UserService.entity.DoctorReview;
import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.entity.user.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface DoctorReviewRepository extends JpaRepository<DoctorReview, UUID> {
    List<DoctorReview> findByDoctorAndUser(DoctorCredentials doctorCredentials, UserDetails userDetails);



    List<DoctorReview> findByDoctor(DoctorCredentials doctor);


    List<DoctorReview> findByDoctorId(UUID id);
}
