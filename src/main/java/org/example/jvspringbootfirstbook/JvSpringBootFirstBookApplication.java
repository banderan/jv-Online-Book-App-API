package org.example.jvspringbootfirstbook;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JvSpringBootFirstBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(JvSpringBootFirstBookApplication.class, args);
    }
    @Bean
    public CommandLineRunner commandLineRunner() {
        return new CommandLineRunner() {
            public void run(String... args) throws Exception {

            }
        };
    }
}
