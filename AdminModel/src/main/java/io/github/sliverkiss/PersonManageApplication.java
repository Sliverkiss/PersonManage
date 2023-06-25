package io.github.sliverkiss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@SpringBootApplication
public class PersonManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(PersonManageApplication.class, args);
    }
}


