package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@SpringBootApplication
public class BoopTheSnootBotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoopTheSnootBotApplication.class, args);
        log.info("TestBot successfully started!");
    }

    @Bean
    public List<String> stickerSetNames() {
        List<String> stickerSetNames = new ArrayList<>();
        stickerSetNames.add("BunJoe");
        stickerSetNames.add("Cheemsburbger");
        stickerSetNames.add("bellycorgi");
        stickerSetNames.add("DonutTheDog");
        stickerSetNames.add("akio_vk");
        stickerSetNames.add("Vasya_Piton");
        stickerSetNames.add("stickerdogs");
        return stickerSetNames;
    }
}
