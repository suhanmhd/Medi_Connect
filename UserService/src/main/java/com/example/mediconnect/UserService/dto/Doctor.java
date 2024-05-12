package com.example.mediconnect.UserService.dto;

import com.example.mediconnect.UserService.entity.DoctorReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

//    @Id
//    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;
    private String firstname;


    private String lastname;

    private String email;


    private String name;



    private int averageRating;

    private boolean enabled;

    private  String isApproved;

    private String place;

    private String about;


    private String specialization;

    private String experience;

    private String image;

    private double feesPerConsultation;

    private  String license;
    private List<DoctorReviewResponseDTO> reviews;

    private List<JobHistoryDTO> jobHistoryList;
    private List<EducationDTO> educationList;
    private ClinicInfoDTO clinicInfo;
}
