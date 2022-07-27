package com.cathay.assignment.currency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.cathay.assignment.currency"})
public class CurrencyApplication {

  public static void main(String[] args) {

    SpringApplication.run(CurrencyApplication.class, args);
  }
}
