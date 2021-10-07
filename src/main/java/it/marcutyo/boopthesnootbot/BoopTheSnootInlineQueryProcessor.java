package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.stickers.GetStickerSet;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultMpeg4Gif;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.cached.InlineQueryResultCachedSticker;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
public class BoopTheSnootInlineQueryProcessor {
    private final ClientComponent clientComponent;
    private final List<String> stickerSetNames;

    public BoopTheSnootInlineQueryProcessor(
            ClientComponent clientComponent,
            List<String> stickerSetNames) {
        this.clientComponent = clientComponent;
        this.stickerSetNames = stickerSetNames;
    }

    public void processInlineQuery(AbsSender absSender, InlineQuery inlineQuery) {
        String inlineQueryId = inlineQuery.getId();
        String inlineQueryText = inlineQuery.getQuery();

        User user = inlineQuery.getFrom();

        List<InlineQueryResult> inlineQueryResults = new ArrayList<>();

        log.info("Received inline query with id {} with text '{}' from user {} with ID {}.",
                inlineQueryId, inlineQueryText, user.getFirstName() + user.getLastName(), user.getId());

        if (inlineQueryText.equalsIgnoreCase("pic")) {
            log.info("Fetching pics...");
            List<String> doggoUrls = Stream.generate(() -> clientComponent.getDoggoUrl("jpg,jpeg,png"))
                    .limit(3).collect(Collectors.toList());

            doggoUrls.forEach(doggoUrl -> inlineQueryResults.add(
                    InlineQueryResultPhoto.builder()
                            .id(UUID.randomUUID().toString())
                            .photoUrl(doggoUrl)
                            .thumbUrl(doggoUrl)
                            .build()
            ));
            log.info("Successfully fetched pics.");
        } else if (inlineQueryText.equalsIgnoreCase("vid")) {
            log.info("Fetching vids...");
            List<String> doggoUrls = Stream.generate(() -> clientComponent.getDoggoUrl("mp4,gif"))
                    .distinct().limit(3).collect(Collectors.toList());

            doggoUrls.forEach(doggoUrl -> inlineQueryResults.add(
                    InlineQueryResultMpeg4Gif.builder()
                            .id(UUID.randomUUID().toString())
                            .mpeg4Url(doggoUrl)
                            .thumbUrl(doggoUrl)
                            .build()
            ));
            log.info("Successfully fetched vids.");
        } else if (inlineQueryText.equalsIgnoreCase("sticker")) {
            log.info("Fetching stickers...");
            List<String> doggoStickerIds = Stream
                    .generate(() -> {
                        String randStickerSetName = stickerSetNames
                                .get(new Random().nextInt(stickerSetNames.size()));
                        try {
                            List<Sticker> stickerSet = absSender
                                    .execute(new GetStickerSet(randStickerSetName)).getStickers();
                            return stickerSet.get(new Random().nextInt(stickerSet.size())).getFileId();
                        } catch (TelegramApiException e) {
                            log.error("Unable to get sticker set with name {}", randStickerSetName);
                            e.printStackTrace();
                        }
                        return null;
                    }).distinct().limit(3).collect(Collectors.toList());

            doggoStickerIds.forEach(doggoStickerId -> inlineQueryResults.add(
                    InlineQueryResultCachedSticker.builder()
                            .id(UUID.randomUUID().toString())
                            .stickerFileId(doggoStickerId)
                            .build()
            ));
            log.info("Successfully fetched stickers.");
        }
        try {
            log.info("Answering query...");
            absSender.execute(AnswerInlineQuery.builder()
                    .inlineQueryId(inlineQueryId)
                    .results(inlineQueryResults)
                    .cacheTime(15)
                    .build()
            );
            log.info(
                    "Successfully answered query with ID {} to the user {} with ID {}",
                    inlineQueryId,
                    user.getFirstName() + " " + user.getLastName(),
                    user.getId()
            );
        } catch (TelegramApiException e) {
            log.error(
                    "Unable to answer inline query to the user {} with ID {}",
                    user.getFirstName() + " " + user.getLastName(),
                    user.getId()
            );
            e.printStackTrace();
        }
    }
}
