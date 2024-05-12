package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PrescriptionDto {
    private String docId;
    private String userId;
    private String days;
    private String medicineName;
    private String quantity;
    private List<String> time;

    // Getters and setters
}
