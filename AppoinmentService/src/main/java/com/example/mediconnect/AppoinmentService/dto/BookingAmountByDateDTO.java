package com.example.mediconnect.AppoinmentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingAmountByDateDTO {
    private double totalAmount;
    private Date createdDate;




}
