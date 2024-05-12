package com.example.mediconnect.UserService.entity;

import com.example.mediconnect.UserService.entity.doctor.DoctorCredentials;
import com.example.mediconnect.UserService.entity.user.UserDetails;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Prescription extends BaseEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid2")
    @GenericGenerator(name = "id", strategy = "uuid2")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;


    @ManyToOne
        @JoinColumn(name = "doc_id")
        private DoctorCredentials doctor; // Associate with DoctorCredentials entity

        @ManyToOne
        @JoinColumn(name = "user_id")
        private UserDetails user; // Associate with UserDetails entity

        private String days;
        private String medicineName;
        private String quantity;

        @ElementCollection
        private List<String> time; // Store the list of times

        // Getters and setters for all fields





}
