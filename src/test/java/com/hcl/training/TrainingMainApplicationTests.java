package com.hcl.training;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan(basePackages = { "com.hcl.training","com.hcl.training.controller" })
class TrainingMainApplicationTests {

	private static final Logger logger = LogManager.getLogger(TrainingMainApplicationTests.class);

	@Test
	void contextLoads() {

	}
}
