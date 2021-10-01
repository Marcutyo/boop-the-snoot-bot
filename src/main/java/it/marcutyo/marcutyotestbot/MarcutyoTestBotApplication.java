package it.marcutyo.marcutyotestbot;

import it.marcutyo.marcutyotestbot.DoggoBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
@SpringBootApplication
public class MarcutyoTestBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(MarcutyoTestBotApplication.class, args);
        log.info("TestBot successfully started!");
    }
}
