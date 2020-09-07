package com.acme.probando;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.acme.probando.model"})
public class ProbandoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProbandoApplication.class, args);
    }

}
