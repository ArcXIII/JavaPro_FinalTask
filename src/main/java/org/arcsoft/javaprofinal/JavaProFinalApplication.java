package org.arcsoft.javaprofinal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableJpaAuditing(dateTimeProviderRef = "offsetDateTimeProvider")
@SpringBootApplication
@EnableJpaRepositories("org.arcsoft.javaprofinal.application.port.out.persistence.repository")
public class JavaProFinalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaProFinalApplication.class, args);
    }

}
