package com.ht.airline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AirlineServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(AirlineServiceApplication.class, args);
  }
}
