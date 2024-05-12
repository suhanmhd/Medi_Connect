package com.example.mediconnect.AppoinmentService.service;

import com.example.mediconnect.AppoinmentService.dto.*;
import com.example.mediconnect.AppoinmentService.entity.Appointment;
import com.example.mediconnect.AppoinmentService.kafka.Producer;
import com.example.mediconnect.AppoinmentService.repository.AppointmentRepository;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
     @Autowired
    private RazorpayClient razorpayClient;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private Producer producer;

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;


    @Value("${razorpay.secret.id}")
    private String razorpayId;



    public void appointmentBooking(AppointmentReq appointmentReq) {
        System.out.println(appointmentReq.getDocId());
        System.out.println(appointmentReq.getUserId());



         Appointment appointment = Appointment.builder()
                 .userId(appointmentReq.getUserId().toString())
                 .userEmail(appointmentReq.getUserEmail())
                 .userImage(appointmentReq.getUserImage())
                 .doctorId(appointmentReq.getDocId().toString())
                 .userInfo(appointmentReq.getUserInfo())
                 .doctorInfo(appointmentReq.getDoctorInfo())
                 .status("pending")
                 .amount(appointmentReq.getAmount())
                 .paymentStatus("pending")
                 .date(appointmentReq.getDate())
                 .time(appointmentReq.getTime())
                 .build();

//        appointmentRepository.save(appointment);

        System.out.println(appointment.getUserId()+"  ______"+appointment.getDoctorId());

        Appointment savedAppointment = appointmentRepository.save(appointment);
      double amount = appointment.getAmount();

        Map<String, Object> response = null;
           response = generateRazorpay(savedAppointment.getId(), amount);



        System.out.println(response);
       producer.sendBookingRes(response);

    }


    private Map<String, Object> generateRazorpay(UUID id, double amount) {
        System.out.println("in generate rpay");
        Map<String, Object> response = new HashMap<>();

        try {
            System.out.println(id);
            System.out.println(amount);
            Order order = razorpayClient.orders.create(new JSONObject()
                    .put("amount", amount * 100)
                    .put("currency", "INR")
                    .put("receipt", id)
                    .put("notes", new JSONObject()
                            .put("key1", "value3")
                            .put("key2", "value2"))
            );

            response.put("status", true);
            response.put("order", order);

        } catch (RazorpayException e) {
            response.put("status", "Failed");
            response.put("message", e.getMessage());
        }
        System.out.println(response+"_+_+_+_+_+_");
        return response;
    }





    public String verifyPayment(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> res = (Map<String, Object>) requestBody.get("res");
        String paymentId = (String) res.get("razorpay_payment_id");
        String orderId = (String) res.get("razorpay_order_id");
        String signature = (String) res.get("razorpay_signature");

        Map<String, Object> order = (Map<String, Object>) requestBody.get("order");
       int amount = (int) order.get("amount");
       int amountPaid = (int) order.get("amount_paid");
        int amountDue = (int) order.get("amount_due");
        String currency = (String) order.get("currency");
        String receipt = (String) order.get("receipt");
        String orderId2 = (String) order.get("id");
        String entity = (String) order.get("entity");
        String offerId = (String) order.get("offer_id");
        String status = (String) order.get("status");
        int attempts = (int) order.get("attempts");


        try {

            String data = orderId + "|" + paymentId;


            String generatedSignature = generateHmacSHA256Signature(data, razorpayApiKey);
            System.out.println(generatedSignature);
            System.out.println(signature);

            if (generatedSignature.equals(signature)) {
                System.out.println("helloddd");
                // Signature is valid, process the payment and change status

                 boolean paymentstatus=changePaymentStatus(receipt);
                if (paymentstatus) {
                    System.out.println("sucess of the payment");
                    return "success";
                }else{
                    System.out.println("simply failed");
                    return "failed";

                }
            } else {
                // Signature is invalid
                return "Invalid payment signature";
            }
        } catch (Exception e) {
            return "Error verifying payment";
        }
    }

    private boolean changePaymentStatus( String id) {
        System.out.println("which id"+id);



         Appointment payment =appointmentRepository.getById(UUID.fromString(id));
        System.out.println(payment);
         payment.setPaymentStatus("Completed");
             appointmentRepository.save(payment);
        System.out.println(payment.getPaymentStatus());
       if(payment.getPaymentStatus()=="Completed"){
           return true;
       }

        return false;
        // ...
    }

    private String generateHmacSHA256Signature(String data, String secretKey) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac hmacSha256 = Mac.getInstance("HmacSHA256");
        hmacSha256.init(secretKeySpec);
        byte[] signatureBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(signatureBytes);
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder hexStringBuilder = new StringBuilder();
        for (byte b : bytes) {
            String hexString = Integer.toHexString(0xFF & b);
            if (hexString.length() == 1) {
                hexStringBuilder.append('0');
            }
            hexStringBuilder.append(hexString);
        }
        return hexStringBuilder.toString();
    }


    public void viewAppointments(String userId) {



//
//        System.out.println(userId);
        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);

        producer.getAppointments(appointments);
        System.out.println("-----");
        System.out.println(appointments);
        System.out.println("------");
    }

    public void cancelAppointment(AppointmentCanceldto appointmentCanceldto) {
         Appointment appointmentdetails = appointmentRepository.findById(appointmentCanceldto.getAppointmentId()).orElse(null);
        System.out.println(appointmentdetails.getPaymentStatus()+""+appointmentdetails.getStatus()+"+++++"+appointmentdetails.getId());
        if (appointmentdetails != null && "pending".equals(appointmentdetails.getStatus()) && "Completed".equals(appointmentdetails.getPaymentStatus())){


         appointmentRepository.delete(appointmentdetails);

             producer.cancelAppointmentRes();

        }

    }

    public List<Appointment> getAppointmentRequest(String doctorId) {
        List<Appointment> appointmentRequests = appointmentRepository.findAllByDoctorId(doctorId);

//        producer.getAppointmentRequests(appointmentRequests);
        System.out.println("-----");
        System.out.println(appointmentRequests);

        return appointmentRequests;
    }

    public String updateAppointmentStatus(AppointmentStatus appointmentStatus) {
        System.out.println("hello");

        UUID id = appointmentStatus.getAppointmentId();
        Appointment appointmentdata=appointmentRepository.findById(appointmentStatus.getAppointmentId()).orElse(null);
         RefundDTO refundDTO = new RefundDTO();
         refundDTO.setAmount(appointmentdata.getAmount());
         refundDTO.setUserId(UUID.fromString(appointmentdata.getUserId()));
         producer.sendRefundAmountToUserWallet(refundDTO);
        appointmentdata.setStatus(appointmentStatus.getStatus());

        appointmentRepository.save(appointmentdata);
        List<Appointment> appointmentRequests = appointmentRepository.findAll();
        System.out.println("+_+_+_+"+appointmentRequests);
        return "Appointment status updated";

    }
    public List<Appointment> getTodaysAppointments(String doctorId) {
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Today's Date: " + today);
        List<Appointment> appointmentRequest = appointmentRepository.findAllByDoctorId(doctorId);

        List<Appointment> todaysAppointments = appointmentRequest.stream()
                .filter(appointment -> {
                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate(), formatter);
                    System.out.println("Appointment Date: " + appointmentDate);
                    return appointmentDate.isEqual(today);
                })
                .collect(Collectors.toList());

        // producer.getTodaysAppointments(todaysAppointments);
        return todaysAppointments;
    }


//    public List<Appointment> getTodaysAppointments(String doctorId) {
//        LocalDate today = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//
//        System.out.println(today);
//        List<Appointment> appointmentRequest = appointmentRepository.findAllByDoctorId(doctorId);
//
//
//        List<Appointment> todaysAppointments =appointmentRequest.stream()
//                .filter(appointment -> {
//                    LocalDate appointmentDate = LocalDate.parse(appointment.getDate(), formatter);
//                    System.out.println(appointmentDate);
//                    return  appointmentDate.equals(today);
//                })
//                .collect(Collectors.toList());
////        producer.getTodaysAppointments(todaysAppointments);
//        return todaysAppointments;
//    }

    public void getAppointmentTimes(String doctorId) {
        List<Appointment> appointmentTimes = appointmentRepository.findAllByDoctorId(doctorId);

        producer.getAppointmentTime(appointmentTimes);
    }

    public List<DoctorInfo> getBookedDoctors(UUID id) {
        List<Appointment> appointments = appointmentRepository.findAllByUserId(id.toString());

        Set<String> uniqueDoctorIds = new HashSet<>();
        List<DoctorInfo> distinctDoctorInfos = new ArrayList<>();

        for (Appointment appointment : appointments) {
            String doctorId = appointment.getDoctorId();
            if (!uniqueDoctorIds.contains(doctorId)) {
                uniqueDoctorIds.add(doctorId);
                DoctorInfo doctorInfo = DoctorInfo.builder()
                        .doctorId(appointment.getDoctorId())
                        .doctorInfo(appointment.getDoctorInfo())
                        .build();
                distinctDoctorInfos.add(doctorInfo);
            }
        }

        return distinctDoctorInfos;
    }


//public List<DoctorInfo> getBookedDoctors(UUID id) {
//    List<Appointment> appointments = appointmentRepository.findAllByUserId(id.toString());
//    List<DoctorInfo> doctorInfos = appointments.stream()
//            .map(appointment -> DoctorInfo.builder()
//                    .doctorId(appointment.getDoctorId())
//                    .doctorInfo(appointment.getDoctorInfo())
//                    .build())
//            .collect(Collectors.toList());
//    return doctorInfos;
//}

    public DoctorInfo getDoctor(UUID id) {
        Appointment appointments = appointmentRepository.findDoctorById(id.toString());
        DoctorInfo doctorInfo =new DoctorInfo();
        doctorInfo.setDoctorId(appointments.getDoctorId());
        doctorInfo.setDoctorInfo(appointments.getDoctorInfo());

        return doctorInfo;
    }

    public PaymentReport getDoctorDashDetails(UUID docId) {
        List<Appointment> doctorAppointments =appointmentRepository.findAllByDoctorId(docId.toString());

       double totalRevenue = 0;

        for (Appointment appointment : doctorAppointments) {
            totalRevenue = totalRevenue+appointment.getAmount();
        }
        PaymentReport paymentReport = new PaymentReport();
        paymentReport.setTotalRevenue(totalRevenue);

        return paymentReport;


    }



    public   List<Appointment> getUserAppointmentDetailsToDoctor(String userId) {




        List<Appointment> appointments = appointmentRepository.findAllByUserId(userId);

       return  appointments;
    }

    public List<Appointment> getAlllAppointmentDetailsToAdmin() {
        // Retrieve the list of appointments
        List<Appointment> appointments = appointmentRepository.findAll();

        // Define a DateTimeFormatter for parsing the date strings
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Sort the appointments based on the parsed LocalDate
        appointments.sort((a1, a2) -> {
            LocalDate date1 = LocalDate.parse(a1.getDate(), dateFormatter);
            LocalDate date2 = LocalDate.parse(a2.getDate(), dateFormatter);
            return date1.compareTo(date2);
        });

        return appointments;
    }


    public List<Appointment> getPaidAppointmentsToAdmin() {
        try {
            return appointmentRepository.findByStatusAndPaymentStatusOrderByCreatedDateDesc("approved","Completed");
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching paid appointments: " + e.getMessage(), e);
        }


    }


    public AdminDetailsDTO getAllDetails() {
        try {
            long numUsers = appointmentRepository.count();
            long numDoctors = appointmentRepository.count();
            long numBookings = appointmentRepository.count();

            List<Appointment> bookingDetails = appointmentRepository.findByStatusAndPaymentStatus("approved", "Completed");
            double bookingTotal = appointmentRepository.calculateTotalBookingAmount();

           List<BookingAmountByDateDTO> totalAmountsByDate = appointmentRepository.getTotalAmountsByDate();

            return new AdminDetailsDTO(numUsers, numDoctors, numBookings, bookingTotal, totalAmountsByDate, bookingDetails);
        } catch (Exception e) {
            throw new RuntimeException("Error while fetching admin details: " + e.getMessage(), e);
        }
    }
}
