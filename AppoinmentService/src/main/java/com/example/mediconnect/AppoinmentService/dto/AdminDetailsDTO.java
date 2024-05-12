package com.example.mediconnect.AppoinmentService.dto;



import com.example.mediconnect.AppoinmentService.entity.Appointment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminDetailsDTO {
    private long numUsers;
    private long numDoctors;
    private long numBookings;
    private double bookingTotal;
    private List<BookingAmountByDateDTO> totalAmountsByDate;
    private List<Appointment> bookingDetails;

    // Constructors, getters, and setters
}