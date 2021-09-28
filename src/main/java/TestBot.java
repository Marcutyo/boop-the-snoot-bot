import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class TestBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message userMessage = update.getMessage();
            String chatId = userMessage.getChatId().toString();

            if (userMessage.hasText()) {
                String userCommand = userMessage.getText();

                switch (userCommand) {
                    case "/start":
                        sendNewMessage(chatId, "Start");
                        break;
                    case "/i_wamt_doggo_pics":
                        sendNewMessage(chatId, "Doggo pic");
                        break;
                    case "/i_wamt_doggo_vids":
                        sendNewMessage(chatId, "Doggo vid");
                        break;
                    default:
                        sendNewMessage(chatId, "Noooo this is not valid! 🐶");
                }
            } else {
                sendNewMessage(chatId, "No plz just send me text. 🐶");
            }
        }
    }

    public void sendNewMessage(String chatId, String text) {
        try {
            execute(new SendMessage(chatId, text));
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
