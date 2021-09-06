package firsov.study.sravnitaxibot.common.bean;

import com.vdurmont.emoji.EmojiParser;
import firsov.study.sravnitaxibot.common.Command;
import org.springframework.stereotype.Service;

@Service
public class MessageGenerator {

    public String generateStartMessage(String name) {
        return "Привет " + name + "!";
    }

    public String generateFindKeyboard() {
        return EmojiParser.parseToUnicode("Сравнить :full_moon_with_face:");
    }

    public String generateCancelKey() {
        return "Отменить";
    }

    public String generateHelpMessage() {
        String message =  ":sunny: Вот мои доступные команды :sunny:\n\n";

        for (Command value : Command.values()) {
            message = message + value.getDescription() + "\n";
        }
        return EmojiParser.parseToUnicode(message);
    }

    public String generateSuccessCancel() {
        return EmojiParser.parseToUnicode(":white_check_mark: Активная команда успешно отклонена");
    }

}
