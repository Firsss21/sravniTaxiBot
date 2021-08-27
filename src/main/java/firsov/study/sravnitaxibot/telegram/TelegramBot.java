package firsov.study.sravnitaxibot.telegram;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private BotConfig botConfig;
    @Autowired
    private BotFacade botFacade;

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotAccessToken();
    }


    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        botFacade.handleUpdate(update);
    }
}
