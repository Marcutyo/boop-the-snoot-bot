package it.marcutyo.boopthesnootbot;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Getter
@Component
public class ClientComponent {
    private final String RANDOM_DOG_URL = "https://random.dog/";

    private final WebClient webClient;

    public ClientComponent() {
        this.webClient = WebClient.create(RANDOM_DOG_URL);
    }

    public String getResponse(String fileExt) {
        return this.webClient
                .get()
                .uri("woof?include={fileExt}", fileExt)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public String getDoggoUrl(String fileExt) {
        return RANDOM_DOG_URL + getResponse(fileExt);
    }
}
