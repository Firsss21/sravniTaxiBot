package firsov.study.sravnitaxibot.common.service;

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

    public ReplyKeyboardMarkup setMainMenuKeyboard(Long chatId) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> menu = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
//        row.add(KeyboardButton.builder().requestLocation(true).build());
        row.add(messageGenerator.generateFindKeyboard());
        menu.add(row);
        replyKeyboardMarkup.setKeyboard(menu);
        replyKeyboardMarkup.setResizeKeyboard(true);
        return replyKeyboardMarkup;
    }
}
