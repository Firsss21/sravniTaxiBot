package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.common.BotState;
import firsov.study.sravnitaxibot.common.bean.ChatConfigService;
import firsov.study.sravnitaxibot.common.bean.MessageGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    @Autowired
    MessageGenerator messageGenerator;
    @Autowired
    ChatConfigService chatConfigService;

    public ReplyKeyboardMarkup setKeyBoard(Long chatId) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> menu = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        BotState botState = chatConfigService.getBotState(chatId);
        switch (botState) {
            case DEFAULT: {
                if (chatConfigService.getCity(chatId) == null) {
                    row.add("Выбрать город");
                } else {
                    row.add(messageGenerator.generateFindKeyboard());
                }
                break;
            }
            case INS_CITY:
                row.add(messageGenerator.generateCancelKey());
                break;
            case INS_DEST_ADDRESS:
                row.add(messageGenerator.generateCancelKey());
                break;
            case INS_DEPART_ADDRESS:
                row.add(KeyboardButton.builder().text("Моя локация").requestLocation(true).build());
                row.add(messageGenerator.generateCancelKey());
                break;
            default:
                break;
        }
        menu.add(row);
        replyKeyboardMarkup.setKeyboard(menu);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }


}
