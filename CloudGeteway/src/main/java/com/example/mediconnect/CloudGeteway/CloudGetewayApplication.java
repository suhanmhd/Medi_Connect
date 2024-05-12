package com.example.mediconnect.CloudGeteway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
//@ComponentScan(basePackages = "com.example.mediconnect.CloudGateway")
public class CloudGetewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGetewayApplication.class, args);
	}

}
