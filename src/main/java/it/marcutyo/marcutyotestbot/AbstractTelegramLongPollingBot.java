package it.marcutyo.marcutyotestbot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class AbstractTelegramLongPollingBot extends TelegramLongPollingBot {
    protected String botUsername;
    protected String apiKey;

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.apiKey;
    }

    public Message sendMessage(SendMessage sendMessage) {
        Message message = new Message();
        try {
            message = this.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message sendSticker(SendSticker sendSticker) {
        Message message = new Message();
        try {
            message = this.execute(sendSticker);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message sendPhoto(SendPhoto sendPhoto) {
        Message message = new Message();
        try {
            message = this.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }

    public Message sendAnimation(SendAnimation sendAnimation) {
        Message message = new Message();
        try {
            message = this.execute(sendAnimation);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        return message;
    }
}
