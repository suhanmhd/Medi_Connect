package com.example.mediconnect.AppoinmentService.repository;


import com.example.mediconnect.AppoinmentService.dto.BookingAmountByDateDTO;
import com.example.mediconnect.AppoinmentService.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    Optional<Appointment> findByUserId(UUID userId);

    Optional<Object> getByUserId(String userId);

    List<Appointment> findAllByUserId(String userId);

    List<Appointment> findAllByDoctorId(String doctorId);

    Appointment findDoctorById(String doctorId);

//    List<Appointment> findByStatusAndPaymentStatusOrderByCreatedAtDesc(String approved, String completed);
    List<Appointment> findByStatusAndPaymentStatusOrderByCreatedDateDesc(String status, String paymentStatus);

    List<Appointment> findByStatusAndPaymentStatus(String status, String paymentStatus);

    @Query("SELECT SUM(a.amount) FROM Appointment a WHERE a.status = 'approved' AND a.paymentStatus = 'Completed'")
    double calculateTotalBookingAmount();


//    @Query("SELECT NEW com.example.mediconnect.AppoinmentService.dto.BookingAmountByDateDTO(SUM(a.amount), DATE(a.createdDate)) " +
//            "FROM Appointment a WHERE a.status = 'approved' AND a.paymentStatus = 'Completed' " +
//            "GROUP BY DATE(a.createdDate) " +
//            "ORDER BY DATE(a.createdDate)")
//    List<BookingAmountByDateDTO> getTotalAmountsByDate();

    @Query("SELECT new com.example.mediconnect.AppoinmentService.dto.BookingAmountByDateDTO(SUM(a.amount), a.createdDate) " +
            "FROM Appointment a GROUP BY a.createdDate")
    List<BookingAmountByDateDTO> getTotalAmountsByDate();

}


