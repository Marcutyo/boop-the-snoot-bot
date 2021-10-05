package it.marcutyo.marcutyotestbot;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@Getter
public class UserInput {
    private String command = "help";
    private String[] arguments = new String[0];
    private Chat chat;
    private User user;


    public UserInput(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            setChat(message);
            setUser(message);

            if (message.hasText() && message.getText().startsWith("/")) {
                String[] messageText = message.getText().split("\\s+");
                setCommand(messageText);
                setArguments(messageText);
            }
        }
    }

    private void setChat(Message message) {
        this.chat = message.getChat();
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
