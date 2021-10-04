package it.marcutyo.marcutyotestbot;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Getter
@Component
public class DoggoBotProcessor {
    private final BotCommand ECHO_COMMAND;
    private final HelpCommand HELP2_COMMAND;
    private final BotCommand DEFAULT_COMMAND;


    public DoggoBotProcessor() {
        this.ECHO_COMMAND = new BotCommand("echo", "I'll repeat everything you write") {
            @SneakyThrows
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                String str = String.join(" ", strings);
                absSender.execute(new SendMessage(chat.getId().toString(), str));
            }
        };

        this.DEFAULT_COMMAND = new BotCommand("default", "È il metodo default") {
            @SneakyThrows
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                absSender.execute(new SendMessage(chat.getId().toString(), "Messaggio default se il metodo non esiste o non è un metodo"));
            }
        };

        this.HELP2_COMMAND = new HelpCommand("help", "mostra tutti i comandi", "è un comando fantastico per farti avere tutto maremma");
    }


}
