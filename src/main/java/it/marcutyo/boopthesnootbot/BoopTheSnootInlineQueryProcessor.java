package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BoopTheSnootInlineQueryProcessor {
    private final ClientComponent clientComponent;

    public BoopTheSnootInlineQueryProcessor(
            ClientComponent clientComponent) {
        this.clientComponent = clientComponent;
    }

    public void processInlineQuery(AbsSender absSender, Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();
        String inlineQueryId = inlineQuery.getId();

        log.info("Fetching results");
        log.info(inlineQueryId);
        log.info(inlineQuery.getQuery());

        List<String> doggoUrls = Stream.generate(() -> clientComponent.getDoggoUrl("jpg,jpeg,png"))
                .limit(5).collect(Collectors.toList());

        List<InlineQueryResult> inlineQueryResults = new ArrayList<>();
        InlineQueryResultPhoto inlineQueryResultPhoto;
        if (inlineQuery.getQuery().equals("cane")) {
            doggoUrls.forEach(doggoUrl -> inlineQueryResults.add(
                    InlineQueryResultPhoto.builder()
                            .id(UUID.randomUUID().toString())
                            .photoUrl(doggoUrl)
                            .thumbUrl(doggoUrl)
                            .build()
            ));
        } else if (inlineQuery.getQuery().equals("catto")) {
            inlineQueryResultPhoto = new InlineQueryResultPhoto(
                    inlineQueryId, "https://purr.objects-us-east-1.dream.io/i/vKNYB.jpg"
            );
            inlineQueryResultPhoto.setThumbUrl("https://purr.objects-us-east-1.dream.io/i/vKNYB.jpg");
        } else return;
        try {
            absSender.execute(AnswerInlineQuery.builder()
                    .inlineQueryId(inlineQueryId)
                    .results(inlineQueryResults)
                    .cacheTime(10)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
