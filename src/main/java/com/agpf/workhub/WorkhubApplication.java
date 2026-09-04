package com.agpf.workhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class WorkhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkhubApplication.class, args);
	}

}
