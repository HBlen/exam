package com.blen.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.blen.exam"})
public class ExamApplicationTests {
  public static void main(String[] args) {
		SpringApplication.run(ExamApplicationTests.class,args);
  }
}
