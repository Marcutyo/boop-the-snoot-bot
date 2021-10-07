package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultMpeg4Gif;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
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
        String inlineQueryText = inlineQuery.getQuery();

        log.info("Fetching results");
        log.info(inlineQueryId);
        log.info(inlineQuery.getQuery());


        List<InlineQueryResult> inlineQueryResults = new ArrayList<>();

        if (inlineQueryText.equalsIgnoreCase("pic")) {
            List<String> doggoUrls = Stream.generate(() -> clientComponent.getDoggoUrl("jpg,jpeg,png"))
                    .limit(3).collect(Collectors.toList());

            doggoUrls.forEach(doggoUrl -> inlineQueryResults.add(
                    InlineQueryResultPhoto.builder()
                            .id(UUID.randomUUID().toString())
                            .photoUrl(doggoUrl)
                            .thumbUrl(doggoUrl)
                            .build()
            ));
        } else if (inlineQueryText.equalsIgnoreCase("vid")) {
            List<String> doggoUrls = Stream.generate(() -> clientComponent.getDoggoUrl("mp4,gif"))
                    .distinct().limit(3).collect(Collectors.toList());

            doggoUrls.forEach(doggoUrl -> inlineQueryResults.add(
                    InlineQueryResultMpeg4Gif.builder()
                            .id(UUID.randomUUID().toString())
                            .mpeg4Url(doggoUrl)
                            .thumbUrl(doggoUrl)
                            .build()
            ));
        }
        try {
            absSender.execute(AnswerInlineQuery.builder()
                    .inlineQueryId(inlineQueryId)
                    .results(inlineQueryResults)
                    .cacheTime(15)
                    .build()
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
