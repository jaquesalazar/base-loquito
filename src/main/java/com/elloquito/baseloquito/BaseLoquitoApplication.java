package com.elloquito.baseloquito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BaseLoquitoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseLoquitoApplication.class, args);
    }

}
