import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.stickers.GetStickerSet;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.api.objects.stickers.StickerSet;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.util.List;
import java.util.Random;

@Component
public class DoggoBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message userMessage = update.getMessage();
            String chatId = userMessage.getChatId().toString();

            if (userMessage.hasText()) {
                String userCommand = userMessage.getText();

                switch (userCommand) {
                    case "/start":
                        sendNewMessage(chatId, null, "Start");
                        break;
                    case "/i_wamt_doggo_pics":
                        sendNewMessage(chatId, null, "Doggo pic");
                        break;
                    case "/i_wamt_doggo_vids":
                        sendNewMessage(chatId, null, "Doggo vid");
                        break;
                    case "/i_wamt_doggo_stickers":
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
                                        .chatId(chatId)
                                        .sticker(new InputFile(stickerResponse.getFileId()))
                                        .build());
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            execute(SendMessage.builder()
                                    .chatId(chatId)
                                    .parseMode("HTML")
                                    .text("<b><u>Noooo this is not valid!</u></b> 🐶")
                                    .build()
                            );
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                }
            } else {
                sendNewMessage(chatId, null, "No plz just send me text. 🐶");
            }
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
