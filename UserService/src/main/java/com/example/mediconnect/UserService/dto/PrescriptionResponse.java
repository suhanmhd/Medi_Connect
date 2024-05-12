package com.example.mediconnect.UserService.dto;

import com.example.mediconnect.UserService.dto.doctor.DoctorResponse;
import com.example.mediconnect.UserService.dto.user.UserResponse;
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
public class PrescriptionResponse {
    private UUID id;
    private DoctorResponse doctor;
    private UserResponse user;
    private String days;
    private String medicineName;
    private String quantity;
    private List<String> time;
}
