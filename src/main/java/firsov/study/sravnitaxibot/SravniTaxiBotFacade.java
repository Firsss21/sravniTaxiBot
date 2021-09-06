package firsov.study.sravnitaxibot;

import firsov.study.sravnitaxibot.common.BotState;
import firsov.study.sravnitaxibot.common.Command;
import firsov.study.sravnitaxibot.common.KeyboardType;
import firsov.study.sravnitaxibot.common.bean.ChatConfigService;
import firsov.study.sravnitaxibot.common.bean.Locationer;
import firsov.study.sravnitaxibot.common.bean.MessageGenerator;
import firsov.study.sravnitaxibot.common.bean.TaxiInterface;
import firsov.study.sravnitaxibot.common.entity.Location;
import firsov.study.sravnitaxibot.common.model.Coords;
import firsov.study.sravnitaxibot.common.service.KeyboardService;
import firsov.study.sravnitaxibot.common.service.TaxiManagerService;
import firsov.study.sravnitaxibot.telegram.BotFacade;
import firsov.study.sravnitaxibot.telegram.CallbackAnswer;
import firsov.study.sravnitaxibot.telegram.TelegramBot;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.Locale;

@Service
public class SravniTaxiBotFacade implements BotFacade {

    @Autowired
    private MessageGenerator messageGenerator;
    @Autowired
    private ChatConfigService chatConfigService;
    @Autowired
    private CallbackAnswer callbackAnswer;
    @Autowired
    private TelegramBot telegramBot;
    @Autowired
    private KeyboardService keyboardService;
    @Autowired
    private Locationer locationer;
    @Autowired
    private TaxiManagerService taxiManagerService;

    private String getMessage(Update update) {
        if (update.hasMessage() && update.getMessage().getText() != null) {
            return update.getMessage().getText();
        } else if (update.hasChannelPost() && update.getChannelPost().getText() != null) {
            return update.getChannelPost().getText();
        } else if (update.hasCallbackQuery() && update.getCallbackQuery().getData() != null) {
            return update.getCallbackQuery().getData();
        }
        return "";
    }

    @Override
    public void handleUpdate(Update update) throws IOException, InterruptedException {
        String messageText;
        Long chatId;
        String userFirstName = "";

        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            messageText = update.getMessage().getText() == null ? "" : update.getMessage().getText().toUpperCase(Locale.ROOT).replace("/", "");
            userFirstName = update.getMessage().getChat().getFirstName();
        } else if (update.hasChannelPost()) {
            chatId = update.getChannelPost().getChatId();
            messageText = update.getChannelPost().getText() == null ? "" : update.getChannelPost().getText().toUpperCase(Locale.ROOT).replace("/", "");

            userFirstName = update.getChannelPost().getChat().getFirstName();
        } else if (update.hasCallbackQuery()) {
            callbackAnswer.callbackAnswer(update.getCallbackQuery().getId());

            chatId = update.getCallbackQuery().getMessage().getChatId();
            messageText = update.getCallbackQuery().getData() == null ? "" : update.getCallbackQuery().getData().toUpperCase(Locale.ROOT);
            sendMessage(update, update.getCallbackQuery().getData());

        } else if (update.hasMyChatMember()) {
            if (update.getMyChatMember().getNewChatMember().getStatus().equals("kicked")) {
                chatConfigService.deleteChat(update.getMyChatMember().getChat().getId());
            }

            return;
        } else {

            return;
        }

        if (!chatConfigService.isChatInit(chatId)) {
            chatConfigService.initChat(chatId);
            sendMessage(update, messageGenerator.generateStartMessage(userFirstName));
        } else {
            handleBotState(update, chatId, messageText, userFirstName);
        }
    }


    private Long setChatIdToMessageBuilder(Update update, SendMessage.SendMessageBuilder messageBuilder) {
        Long chatId = null;
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            messageBuilder.chatId(update.getMessage().getChatId().toString());
        } else if (update.hasChannelPost()) {
            chatId = update.getChannelPost().getChatId();
            messageBuilder.chatId(update.getChannelPost().getChatId().toString());
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            messageBuilder.chatId(update.getCallbackQuery().getMessage().getChatId().toString());
        }
        return chatId;
    }

    private void sendMessage(Update update, String messageText) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();
        Long chatId = setChatIdToMessageBuilder(update, messageBuilder);
        messageBuilder.replyMarkup(keyboardService.setKeyBoard(chatId));
        messageBuilder.text(messageText);

        try {
            telegramBot.execute(messageBuilder.build());
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    private void sendMessage(Update update, String messageText, KeyboardType keyboardType) {
        SendMessage.SendMessageBuilder messageBuilder = SendMessage.builder();

        Long chatId = setChatIdToMessageBuilder(update, messageBuilder);

        messageBuilder.text(messageText);

//        switch (keyboardType) {
//            case AUTO: {
//                messageBuilder.replyMarkup(keyboardService.setKeyBoard(chatId));
//            }
//            case FIND: {
//                messageBuilder.replyMarkup(keyboardService.setMainMenuKeyboard(chatId));
//            }
//        }
        messageBuilder.replyMarkup(keyboardService.setKeyBoard(chatId));

        try {
            telegramBot.execute(messageBuilder.build());
        } catch (TelegramApiException telegramApiException) {
            telegramApiException.printStackTrace();
        }
    }

    @SneakyThrows
    private void handleBotState(Update update, Long chatId, String messageText, String userFirstName) {

        BotState botState = chatConfigService.getBotState(chatId);

        if (messageText.equals(Command.START.name())) {
            sendMessage(update, messageGenerator.generateStartMessage(userFirstName), KeyboardType.FIND);
            chatConfigService.setBotState(chatId, BotState.DEFAULT);
            return;
        }
        if (messageText.equals(Command.CANCEL.name()) || messageText.equals("ОТМЕНИТЬ")) {
            if (botState == BotState.DEFAULT) {
                sendMessage(update, "Нет активной команды для отклонения");
            } else {
                chatConfigService.setBotState(chatId, BotState.DEFAULT);
                sendMessage(update, messageGenerator.generateSuccessCancel());
                return;
            }
        }

        if (messageText.equals(Command.HELP.name())) {
            sendMessage(update, messageGenerator.generateHelpMessage());
            return;
        }
        if (messageText.equals(Command.CITY.name())) {
            if (chatConfigService.getCity(chatId) == null) {
                sendMessage(update, "Город не выбран");
            } else {
                sendMessage(update, chatConfigService.getCity(chatId));
            }
            return;
        }

        switch (botState) {
            case DEFAULT: {
                if (messageText.equals(Command.FIND.name()) || messageText.equals(messageGenerator.generateFindKeyboard().toUpperCase(Locale.ROOT))) {
                    if (chatConfigService.getCity(chatId) == null) {
                        chatConfigService.setBotState(chatId, BotState.INS_CITY);
                        sendMessage(update, "Введите название своего города");
                    } else {
                        chatConfigService.setBotState(chatId, BotState.INS_DEPART_ADDRESS);
                        sendMessage(update, "Введите откуда вы хотите направиться");
//                        keyboardService.setGetCurLocation(chatId);
                    }
                }
                if (messageText.equals(Command.SETCITY.name()) || messageText.equals("Выбрать город".toUpperCase(Locale.ROOT))) {
                    chatConfigService.setBotState(chatId, BotState.INS_CITY);
                    if (chatConfigService.getCity(chatId) == null) {
                        sendMessage(update, "Введите название своего города");
                    } else {
                        sendMessage(update, "Введите название своего нового города");
                    }
                }
                break;
            }
            case INS_CITY: {
                if (!messageText.equals("") && locationer.isCity(messageText)) {
                    chatConfigService.setCity(chatId, messageText);
                    chatConfigService.setBotState(chatId, BotState.DEFAULT);
                    sendMessage(update, "Город успешно выбран!");
                    break;
                }
                sendMessage(update, "Введите корректное название своего города");
                break;
            }
            case INS_DEPART_ADDRESS: {
                boolean success = false;
                if (update.getMessage().getLocation() != null) {
                    chatConfigService.setLocation(chatId, new Location(update.getMessage().getLocation().getLatitude(), update.getMessage().getLocation().getLongitude()));
                    success = true;

                } else {
                    String address = chatConfigService.getCity(chatId) + ", " + getMessage(update);
                    if (!messageText.equals("") && locationer.isRightAddress(address)) {
                        chatConfigService.setLocation(chatId, new Location(locationer.getCoords(address)));
                        success = true;
                    } else {
                        sendMessage(update, "Введите корректный адресс");
                        return;
                    }
                }

                if (success) {
                    sendMessage(update, "Введите адресс прибытия");
                    chatConfigService.setBotState(chatId, BotState.INS_DEST_ADDRESS);
                } else {
                    chatConfigService.setBotState(chatId, BotState.DEFAULT);
                    sendMessage(update, "error");
                }


                break;
            }
            case INS_DEST_ADDRESS: {
                String address = chatConfigService.getCity(chatId) + ", " + getMessage(update);
                if (!messageText.equals("") && locationer.isRightAddress(address)) {
                    Coords dest = locationer.getCoords(address);
                    Coords start = new Coords(chatConfigService.getLocation(chatId));
                    String msg = taxiManagerService.getPrices(start, dest);
                    chatConfigService.setBotState(chatId, BotState.DEFAULT);
                    sendMessage(update, msg);
                    break;
                }
                sendMessage(update, "Введите корректный адресс прибытия");
                break;
            }
        }


    }
}
