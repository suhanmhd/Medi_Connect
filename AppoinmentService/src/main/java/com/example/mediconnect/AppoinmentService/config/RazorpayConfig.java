package com.example.mediconnect.AppoinmentService.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorpayConfig {

    @Value("${razorpay.api.key}")
    private String razorpayApiKey;


    @Value("${razorpay.secret.id}")
    private String razorpayId;




    @Bean
    public RazorpayClient razorpayClient() throws RazorpayException {
        return new RazorpayClient(razorpayId,razorpayApiKey);

//        return  new RazorpayClient(razorpayId,);

    }
}
