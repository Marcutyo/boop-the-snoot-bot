package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class BoopTheSnootBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoopTheSnootBotApplication.class, args);
        log.info("TestBot successfully started!");
    }
}
