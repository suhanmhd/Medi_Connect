package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalDoctorAndUser {

    private long totalUsers;
    private long totalDoctors;
    private double totalRevenue;
}
