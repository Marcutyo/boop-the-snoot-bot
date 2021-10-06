package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultPhoto;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class BoopTheSnootBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final String apiKey;

    private final BoopTheSnootBotProcessor boopTheSnootBotProcessor;
    private final BoopTheSnootInlineQueryProcessor boopTheSnootInlineQueryProcessor;

    public BoopTheSnootBot(
            @Value("${test-bot.name}") String botUsername,
            @Value("${test-bot.api-key}") String apiKey,
            BoopTheSnootBotProcessor boopTheSnootBotProcessor,
            BoopTheSnootInlineQueryProcessor boopTheSnootInlineQueryProcessor) {
        super(new DefaultBotOptions(), true);
        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.boopTheSnootBotProcessor = boopTheSnootBotProcessor;
        this.boopTheSnootInlineQueryProcessor = boopTheSnootInlineQueryProcessor;
    }

    @PostConstruct
    private void initCommandRegistry() {
        registerDefaultAction(this::processDefaultCommand);
        registerAll(
                boopTheSnootBotProcessor.getHELP_COMMAND(),
                boopTheSnootBotProcessor.getBOOP_PIC_COMMAND(),
                boopTheSnootBotProcessor.getBOOP_VID_COMMAND(),
                boopTheSnootBotProcessor.getBOOP_STICKER_COMMAND(),
                boopTheSnootBotProcessor.getBOOP_THE_SNOOT()
        );
    }

    private void processDefaultCommand(AbsSender absSender, Message message) {
        getRegisteredCommand("help").processMessage(absSender, message, new String[]{});
        log.info("Processed default command.");
    }

    private void processInlineQuery(Update update) {
        InlineQuery inlineQuery = update.getInlineQuery();
        String inlineQueryId = inlineQuery.getId();
        InlineQueryResultPhoto inlineQueryResultPhoto;
        if (inlineQuery.getQuery().equals("cane")) {
            inlineQueryResultPhoto = new InlineQueryResultPhoto(
                    inlineQueryId, "https://random.dog/2b77b03c-3073-454e-957b-867580b3d005.jpg"
            );
            inlineQueryResultPhoto.setThumbUrl("https://random.dog/2b77b03c-3073-454e-957b-867580b3d005.jpg");
        } else if (inlineQuery.getQuery().equals("catto")) {
            inlineQueryResultPhoto = new InlineQueryResultPhoto(
                    inlineQueryId, "https://purr.objects-us-east-1.dream.io/i/vKNYB.jpg"
            );
            inlineQueryResultPhoto.setThumbUrl("https://purr.objects-us-east-1.dream.io/i/vKNYB.jpg");
        } else return;
        List<InlineQueryResult> inlineQueryResults = new ArrayList<>();
        inlineQueryResults.add(inlineQueryResultPhoto);
        try {
            execute(new AnswerInlineQuery(
                    inlineQueryId, inlineQueryResults)
            );
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        if (update.hasInlineQuery()) {
            processInlineQuery(update);
            return;
        }
        log.info("User typed not valid command.");
        processDefaultCommand(this, update.getMessage());
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.apiKey;
    }
}
