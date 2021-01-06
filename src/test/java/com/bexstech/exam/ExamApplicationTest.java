package com.bexstech.exam;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, args = "--file=/home/felipe/Documents/routeModels.csv")
public class ExamApplicationTest {

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() {

	}
}
