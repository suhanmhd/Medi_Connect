package com.example.mediconnect.UserService.dto;

import com.example.mediconnect.UserService.entity.user.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorReviewResponseDTO {
    private UUID id;
    private int rating;
    private String review;

    private UserDetails user; // Include if needed
    private Doctor doctor; // Include if needed
}
