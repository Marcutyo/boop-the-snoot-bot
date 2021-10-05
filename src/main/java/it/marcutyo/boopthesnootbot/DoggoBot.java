package it.marcutyo.boopthesnootbot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.bots.AbsSender;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class DoggoBot extends TelegramLongPollingCommandBot {
    private final String botUsername;
    private final String apiKey;

    private final DoggoBotProcessor doggoBotProcessor;

    public DoggoBot(
            @Value("${my-bot.name}") String botUsername,
            @Value("${my-bot.api-key}") String apiKey,
            DoggoBotProcessor doggoBotProcessor) {
        super(new DefaultBotOptions(), true);
        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.doggoBotProcessor = doggoBotProcessor;
    }

    @PostConstruct
    private void initCommandRegistry() {
        registerDefaultAction(this::processDefaultCommand);
        registerAll(
                doggoBotProcessor.getHELP_COMMAND(),
                doggoBotProcessor.getBOOP_PIC_COMMAND(),
                doggoBotProcessor.getBOOP_VID_COMMAND(),
                doggoBotProcessor.getBOOP_STICKER_COMMAND(),
                doggoBotProcessor.getBOOP_THE_SNOOT()
        );
    }

    private void processDefaultCommand(AbsSender absSender, Message message) {
        getRegisteredCommand("help").processMessage(absSender, message, new String[]{});
        log.info("Processed default command.");
    }

    @Override
    public void processNonCommandUpdate(Update update) {
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
