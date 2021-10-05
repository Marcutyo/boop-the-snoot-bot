package it.marcutyo.marcutyotestbot;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Random;

@Getter
@Component
public class DoggoBotProcessor {
    private final HelpCommand HELP_COMMAND;

    private final BotCommand BOOP_PIC_COMMAND;
    private final BotCommand BOOP_VID_COMMAND;
    private final BotCommand BOOP_STICKER_COMMAND;
    private final BotCommand BOOP_THE_SNOOT;

    private final ClientComponent clientComponent;

    public DoggoBotProcessor(ClientComponent clientComponent) {
        this.clientComponent = clientComponent;

        this.HELP_COMMAND = new HelpCommand(
                "help",
                "🇮🇹 per avere un riepilogo dettagliato dei comandi ❓\n" +
                        "🇬🇧🇺🇸 to get a detailed summary of commands",
                "HelpCommand extended description placeholder"
        );

        this.BOOP_PIC_COMMAND = new BotCommand(
                "boop_pic",
                "🇮🇹 per ricevere una immagine casuale di un 𝒹𝑜𝑔𝑔𝑜 🐕‍🦺\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 pic"
        ) {
            @SneakyThrows
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                absSender.execute(
                        new SendPhoto(
                                chat.getId().toString(),
                                new InputFile(clientComponent.getDoggoUrl("jpg,jpeg,png"))
                        )
                );
            }
        };

        this.BOOP_VID_COMMAND = new BotCommand(
                "boop_vid",
                "🇮🇹 per ricevere una GIF casuale di un 𝒹𝑜𝑔𝑔𝑜 🐕\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 gif"
        ) {
            @SneakyThrows
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                absSender.execute(
                        new SendAnimation(
                                chat.getId().toString(),
                                new InputFile(clientComponent.getDoggoUrl("mp4,gif,webm"))
                        )
                );
            }
        };

        this.BOOP_STICKER_COMMAND = new BotCommand(
                "boop_sticker",
                "🇮🇹 per ricevere uno sticker casuale di un 𝒹𝑜𝑔𝑔𝑜 🦮\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 sticker"
        ) {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

            }
        };

        this.BOOP_THE_SNOOT = new BotCommand(
                "boop_the_snoot",
                "🇮🇹 per ricevere un 𝒹𝑜𝑔𝑔𝑜 a caso (immagine, GIF o sticker)🐾\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 (pic, GIF, or sticker)"
        ) {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                int n = new Random().nextInt(3);
                switch (n) {
                    case 0:
                        getBOOP_VID_COMMAND().execute(absSender, user, chat, strings);
                        break;
                    case 1:
                        getBOOP_PIC_COMMAND().execute(absSender, user, chat, strings);
                        break;
                    case 2:
                        getBOOP_STICKER_COMMAND().execute(absSender, user, chat, strings);
                }
            }
        };
    }


}
