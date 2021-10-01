package it.marcutyo.marcutyotestbot;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

public class UserInput {
    private String command = "help";
    private String[] arguments = new String[0];
    private String chatId;
    private User user;


    public UserInput(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            setChatId(message);
            setUser(message);

            if (message.hasText() && message.getText().startsWith("/")) {
                String[] messageText = message.getText().split("\\s+");
                setCommand(messageText);
                setArguments(messageText);
            }
        }
    }

    private void setChatId(Message message) {
        this.chatId = message.getChatId().toString();
    }

    private void setUser(Message message) {
        this.user = message.getFrom();
    }

    private void setCommand(String[] messageText) {
        this.command = messageText[0].replace("/", "");
    }

    private void setArguments(String[] messageText) {
        if (messageText.length>1) {
            arguments = new String[messageText.length - 1];
            for (short i = 1; i < messageText.length; i++) {
                arguments[i] = messageText[i];
            }
        }
    }
}
