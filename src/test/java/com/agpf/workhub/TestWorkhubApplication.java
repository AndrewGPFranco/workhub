package com.agpf.workhub;

import org.springframework.boot.SpringApplication;

public class TestWorkhubApplication {

	public static void main(String[] args) {
		SpringApplication.from(WorkhubApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
