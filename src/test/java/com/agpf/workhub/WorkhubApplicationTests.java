package com.agpf.workhub;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class WorkhubApplicationTests {

	@Test
	void contextLoads() {
	}

}
