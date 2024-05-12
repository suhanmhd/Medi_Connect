package com.example.mediconnect.UserService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentReq {

    private UUID docId;
    private UUID userId;
    private  UUID  slotId;
    private String userInfo;

    private String userEmail;
    private String userImage;


    private String doctorInfo;
    private double amount;
    private String date;
    private String time;



}
