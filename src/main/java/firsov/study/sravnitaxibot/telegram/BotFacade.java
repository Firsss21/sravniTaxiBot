package firsov.study.sravnitaxibot.telegram;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;

public interface BotFacade {
    void handleUpdate(Update update) throws IOException, InterruptedException;
}
