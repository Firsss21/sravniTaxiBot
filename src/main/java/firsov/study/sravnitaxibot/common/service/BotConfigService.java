package firsov.study.sravnitaxibot.common.service;

import firsov.study.sravnitaxibot.telegram.BotConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BotConfigService implements BotConfig {

    @Value("#{${bot.name}}")
    private String username;
    @Value("#{${bot.callback}}")
    private String telegramCallbackAnswer;
    @Value("#{${bot.accessToken}}")
    private String botAccessToken;

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getTelegramCallbackAnswerTemp() {
        return telegramCallbackAnswer;
    }

    @Override
    public String getBotAccessToken() {
        return botAccessToken;
    }
}
