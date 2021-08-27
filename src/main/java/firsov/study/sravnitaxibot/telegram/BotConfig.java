package firsov.study.sravnitaxibot.telegram;

import org.springframework.stereotype.Component;

public interface BotConfig {
     String getBotUsername();
     String getBotAccessToken();
     String getTelegramCallbackAnswerTemp();
}
