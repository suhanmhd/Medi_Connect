package com.example.mediconnect.UserService.controller;




import com.example.mediconnect.UserService.dto.*;
import com.example.mediconnect.UserService.dto.doctor.AvailableSlotResonseDTO;
import com.example.mediconnect.UserService.dto.doctor.SlotDTO;

import com.example.mediconnect.UserService.entity.Prescription;
import com.example.mediconnect.UserService.entity.user.UserDetails;
import com.example.mediconnect.UserService.service.DoctorService;
import com.example.mediconnect.UserService.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserService userService;
    @GetMapping("/doctors")
    public ResponseEntity<Void> getAllDoctors(){



        return  new ResponseEntity<>( HttpStatus.OK);
    }

    @GetMapping("/getDoctorProfile/{docId}")
    public ResponseEntity<Map<String, Doctor>>GetDoctorById(@PathVariable("docId") UUID id){

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @PostMapping("/addBookingSlot")
    public ResponseEntity<String> addSlots(@RequestBody List<SlotDTO> slotDTOs) {
        try {
            doctorService.addSlots(slotDTOs);
            return ResponseEntity.ok("Slots added successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to add slots: " + e.getMessage());
        }
    }


    @DeleteMapping("/deleteSlot/{slotId}")
    public ResponseEntity<String> deleteSlotById(@PathVariable UUID slotId) {
        System.out.println(slotId);
        try {
           doctorService.deleteSlotById(slotId);
            return ResponseEntity.ok("Slot deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request");
        }
    }




//    @PostMapping("/updateBookingSlot")
//    public ResponseEntity<String> updateBookingSlot(@RequestBody SlotResponseDTO slotResponseDTO) {
//        try {
//            doctorService.updateBookingSlot(slotResponseDTO);
//            return ResponseEntity.ok("Booking slot updated successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating booking slot: " + e.getMessage());
//        }
//    }





    @PostMapping("/updateDoctorProfile")
    public ResponseEntity<Map<String,Doctor>> updateDoctor(@RequestBody Doctor doctor) {
        System.out.println(doctor);

        Doctor updatedDoctor = doctorService.updateDoctor(doctor);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",updatedDoctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



//    @GetMapping("/getAppointmentRequests/{docId}")
//    public ResponseEntity<Map<String,List>>getAppointmentRequest(@PathVariable("docId") UUID id){
//        Map<String,List> response = new HashMap<>();
//        List<Appointment> appointmentList=doctorService.getAppointmentRequest(id);
//        System.out.println(appointmentList);
//        response.put("appointmentsDetails",appointmentList);
//        return  new ResponseEntity<>(response,HttpStatus.OK);
//    }
//
//
//    @GetMapping("/getTodaysAppointmentRequests/{docId}")
//    public ResponseEntity<Map<String,List>>getTodaysAppointments(@PathVariable("docId") UUID id){
//        Map<String,List> response = new HashMap<>();
//
//        List<Appointment> appointmentList=null;
//        while (appointmentList==null) {
////            appointmentList=doctorService.getTodaysAppointments(id);
//        }
//
//        response.put("appointmentsDetails",appointmentList);
//        return  new ResponseEntity<>(response,HttpStatus.OK);
//    }





    @GetMapping("/getDoctor/{docId}")
    public ResponseEntity<Map<String,Doctor>>getDoctorById(@PathVariable("docId") String docId){

        UUID id = UUID.fromString(docId);

        Doctor doctor = doctorService. GetDoctorById(id);


        Map<String,Doctor> response = new HashMap<>();
        response.put("doctorProfile",doctor);
        System.out.println(response);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }



    @GetMapping("/getAvailableSlots/{doctorId}")
    public ResponseEntity <Map<String,List>>getAvailableSlots(@PathVariable("doctorId") UUID doctorId) {
            List<AvailableSlotResonseDTO> availableSlots = doctorService.getAvailableSlots(doctorId);
        Map<String,List>response = new HashMap<>();
        response.put("slot",availableSlots);
            return  new ResponseEntity<>(response,HttpStatus.OK);


    }


    @GetMapping ("/getSingleUser/{userId}")
    public  ResponseEntity<Map<String, UserDetails>> getUserById(@PathVariable("userId") UUID id){
        UserDetails userDetails = doctorService.getUserById(id);
        Map<String, UserDetails> response = new HashMap<>();
        response.put("userDetails", userDetails);
        return  new ResponseEntity<>(response,HttpStatus.OK);
    }


    @PostMapping("/prescription")
    public ResponseEntity<String> createPrescription(@RequestBody PrescriptionDto prescription) {
        try {
           String createdPrescription = doctorService.createPrescription(prescription);
            return new ResponseEntity<>(createdPrescription, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getPatientPrecsription/{userId}")
    public ResponseEntity<Map<String, List>> getPatientPrecsription(@PathVariable("userId") UUID userId){
     List<PrescriptionResponse> prescription=  doctorService.getPatientPrescription(userId);
      Map<String,List>response = new HashMap<>();
      response.put("prescription",prescription);


        return  new ResponseEntity<>(response,HttpStatus.OK);
    }






}
