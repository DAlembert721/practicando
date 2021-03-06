package com.test_blogging;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TestBloggingApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestBloggingApplication.class, args);
    }

}
