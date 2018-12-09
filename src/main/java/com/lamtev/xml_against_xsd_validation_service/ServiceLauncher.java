package com.lamtev.xml_against_xsd_validation_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * XML-against-XSD-validation-service launcher.
 * <p>
 * Launches Spring Boot application.
 * <p>
 * Service API is described at {@link Api}
 *
 * @author Anton Lamtev
 */
@SpringBootApplication
public interface ServiceLauncher {
    static void main(String... args) {
        SpringApplication.run(ServiceLauncher.class, args);
    }
}
