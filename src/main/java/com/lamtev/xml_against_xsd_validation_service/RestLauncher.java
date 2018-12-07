package com.lamtev.xml_against_xsd_validation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public interface RestLauncher {
    static void main(String... args) {
        SpringApplication.run(RestLauncher.class, args);
    }
}
