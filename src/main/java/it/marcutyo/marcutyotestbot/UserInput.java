package it.marcutyo.marcutyotestbot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UserInput {
    private boolean command;
    private String verb;
    private String chatId;
    private String userId;

    public UserInput(Update update) {
        String data = getData(update);
        if (data.startsWith("/")) this.command = true;
        this.verb = data;
    }

    private String getData(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            setIds(message);
            return message.getText();
        } else {
            Message message = update.getCallbackQuery().getMessage();
            setIds(message);
            return update.getCallbackQuery().getData();
        }
    }

    private void setIds(Message message) {
        this.chatId = message.getChatId().toString();
        this.userId = message.getFrom().getId().toString();
    }
}
