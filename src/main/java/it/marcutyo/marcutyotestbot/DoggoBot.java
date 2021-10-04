package it.marcutyo.marcutyotestbot;

import org.springframework.beans.factory.annotation.Value;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.stickers.GetStickerSet;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Random;


public class DoggoBot extends AbstractTelegramLongPollingBot {
    private final String DOGGO_PIC_COMMAND = "boop_pic";
    private final String DOGGO_VID_COMMAND = "boop_vid";
    private final String DOGGO_STICKER_COMMAND = "boop_sticker";

    private final DoggoBotProcessor doggoBotProcessor;


    public DoggoBot(
            @Value("${my-bot.name}") String botUsername,
            @Value("${my-bot.api-key}") String apiKey,
            DoggoBotProcessor doggoBotProcessor) {
        this.botUsername = botUsername;
        this.apiKey = apiKey;
        this.doggoBotProcessor = doggoBotProcessor;
    }

    @Override
    public void onUpdateReceived(Update update) {
        UserInput userInput = new UserInput(update);

        switch (userInput.getCommand()) {
            default:
            case HELP_COMMAND:
                doggoBotProcessor.getECHO_COMMAND().processMessage(
                        this, update.getMessage(), userInput.getArguments());
                break;
            case DOGGO_PIC_COMMAND:
                sendNewMessage(userInput.getChat().getId().toString(), null, "Doggo pic");
                break;
            case DOGGO_VID_COMMAND:
                sendNewMessage(userInput.getChat().getId().toString(), null, "Doggo vid");
                break;
            case DOGGO_STICKER_COMMAND:
                try {
                    List<Sticker> doggoStickerSet = sendApiMethod(
                            GetStickerSet.builder()
                                    .name("BunJoe")
                                    .build()
                    ).getStickers();
                    Random r = new Random();
                    Sticker stickerResponse = doggoStickerSet.get(r.nextInt(doggoStickerSet.size()));
                    try {
                        execute(SendSticker.builder()
                                .chatId(userInput.getChat().getId().toString())
                                .sticker(new InputFile(stickerResponse.getFileId()))
                                .build());
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public void sendNewMessage(String chatId, String parseMode, String text) {
        try {
            execute(SendMessage.builder()
                    .chatId(chatId)
                    .parseMode(parseMode)
                    .text(text)
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "marcutyo_echo_bot";
    }

    @Override
    public String getBotToken() {
        return "1938694946:AAEI7yLNN6-qLon_e0ZISW-HCVdjpLskf28";
    }
}
