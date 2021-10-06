package it.marcutyo.boopthesnootbot;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;

@Component
public class BoopTheSnootInlineQueryProcessor {
    private final ClientComponent clientComponent;

    public BoopTheSnootInlineQueryProcessor(
            ClientComponent clientComponent) {
        this.clientComponent = clientComponent;
    }


}
