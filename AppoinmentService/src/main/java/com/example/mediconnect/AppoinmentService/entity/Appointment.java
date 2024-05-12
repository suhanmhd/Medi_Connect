package com.example.mediconnect.AppoinmentService.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Appointment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;



     private String userId;


     private String doctorId;

   private String userInfo;

    private String userImage;
    private  String userEmail;

     private String doctorInfo;
//    @Builder.Default
     private  String status="pending";

     private double amount;
//    @Builder.Default
     private  String paymentStatus = "pending";

     private String date;
     private String time;

}
