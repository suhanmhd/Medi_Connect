package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorReviewDTO {

    private UUID userId;
    private  UUID docId;
    private  int rating;
    private String review;
}
