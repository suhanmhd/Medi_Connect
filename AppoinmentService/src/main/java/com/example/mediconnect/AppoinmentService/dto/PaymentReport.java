package com.example.mediconnect.AppoinmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentReport {

    private double totalRevenue;

    private long totalAppointments;
    private long pendingAppointments;
}
