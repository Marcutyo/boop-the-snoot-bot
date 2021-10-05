package it.marcutyo.boopthesnootbot;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.helpCommand.HelpCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.methods.stickers.GetStickerSet;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.stickers.Sticker;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Getter
@Component
public class BoopTheSnootBotProcessor {
    private final HelpCommand HELP_COMMAND;

    private final BotCommand BOOP_PIC_COMMAND;
    private final BotCommand BOOP_VID_COMMAND;
    private final BotCommand BOOP_STICKER_COMMAND;
    private final BotCommand BOOP_THE_SNOOT;

    private final List<String> STICKER_SET_NAMES = new ArrayList<>();

    private final ClientComponent clientComponent;

    public BoopTheSnootBotProcessor(ClientComponent clientComponent) {
        this.clientComponent = clientComponent;

        this.STICKER_SET_NAMES.add("BunJoe");
        this.STICKER_SET_NAMES.add("Cheemsburbger");
        this.STICKER_SET_NAMES.add("bellycorgi");
        this.STICKER_SET_NAMES.add("DonutTheDog");
        this.STICKER_SET_NAMES.add("akio_vk");
        this.STICKER_SET_NAMES.add("Vasya_Piton");
        this.STICKER_SET_NAMES.add("stickerdogs");

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
                try {
                    String doggoUrl = clientComponent.getDoggoUrl("jpg,jpeg,png");
                    absSender.execute(
                            new SendPhoto(
                                    chat.getId().toString(),
                                    new InputFile(doggoUrl)
                            )
                    );
                    log.info(
                            "Successfully sent pic with URL {} to the user {} with ID {}",
                            doggoUrl,
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                } catch (TelegramApiException e) {
                    log.error(
                            "Unable to send pic to the user {} with ID {}",
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                    e.printStackTrace();
                    sendErrorMessage(absSender, user, chat);
                }
            }
        };

        this.BOOP_VID_COMMAND = new BotCommand(
                "boop_vid",
                "🇮🇹 per ricevere una GIF casuale di un 𝒹𝑜𝑔𝑔𝑜 🐕\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 gif"
        ) {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                try {
                    String doggoUrl = clientComponent.getDoggoUrl("mp4,gif,webm");
                    absSender.execute(
                            new SendAnimation(
                                    chat.getId().toString(),
                                    new InputFile(doggoUrl)
                            )
                    );
                    log.info(
                            "Successfully sent vid with URL {} to the user {} with ID {}",
                            doggoUrl,
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                } catch (TelegramApiException e) {
                    log.error(
                            "Unable to send vid to the user {} with ID {}",
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                    e.printStackTrace();
                    sendErrorMessage(absSender, user, chat);
                }
            }
        };

        this.BOOP_STICKER_COMMAND = new BotCommand(
                "boop_sticker",
                "🇮🇹 per ricevere uno sticker casuale di un 𝒹𝑜𝑔𝑔𝑜 🦮\n" +
                        "🇬🇧🇺🇸 to get a random 𝒹𝑜𝑔𝑔𝑜 sticker"
        ) {
            @Override
            public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
                try {
                    String randStickerSetName = STICKER_SET_NAMES
                            .get(new Random().nextInt(STICKER_SET_NAMES.size()));
                    List<Sticker> stickerSet = absSender
                            .execute(new GetStickerSet(randStickerSetName)).getStickers();
                    String doggoStickerId = stickerSet.get(new Random().nextInt(stickerSet.size())).getFileId();
                    absSender.execute(
                            new SendSticker(
                                    chat.getId().toString(),
                                    new InputFile(doggoStickerId)
                            )
                    );
                    log.info(
                            "Successfully sent sticker with ID {} from sticker set {} to the user {} with ID {}",
                            doggoStickerId,
                            randStickerSetName,
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                } catch (TelegramApiException e) {
                    log.error(
                            "Unable to send sticker to the user {} with ID {}",
                            user.getFirstName() + " " + user.getLastName(),
                            user.getId()
                    );
                    e.printStackTrace();
                    sendErrorMessage(absSender, user, chat);
                }
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
                        break;
                }
                log.info("Successfully booped the snoot");
            }
        };
    }

    private void sendErrorMessage(AbsSender absSender, User user, Chat chat) {
        try {
            absSender.execute(
                    new SendMessage(
                            chat.getId().toString(),
                            "Oops! Something went wrong, I could not boop the snoot! Try again! 🐶"
                    )
            );
        } catch (TelegramApiException e) {
            log.error(
                    "Unable to send error message to the user {} with ID {}",
                    user.getFirstName() + " " + user.getLastName(),
                    user.getId()
            );
            e.printStackTrace();
        }
    }
}
