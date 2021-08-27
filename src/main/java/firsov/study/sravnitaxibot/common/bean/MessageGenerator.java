package firsov.study.sravnitaxibot.common.bean;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;

@Service
public class MessageGenerator {

    public String generateStartMessage(String name) {
        return "Hello " + name + "!";
    }

    public String generateFindKeyboard() {
        return EmojiParser.parseToUnicode("Сравнить :full_moon_with_face:");
    }
}
