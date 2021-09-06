package firsov.study.sravnitaxibot.common.bean;

import firsov.study.sravnitaxibot.common.BotState;
import firsov.study.sravnitaxibot.common.entity.ChatEntity;
import firsov.study.sravnitaxibot.common.entity.Location;
import firsov.study.sravnitaxibot.common.repository.ChatConfigRepo;
import firsov.study.sravnitaxibot.common.repository.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatConfigService {
    @Autowired
    ChatConfigRepo chatConfigRepo;
    @Autowired
    LocationRepo locationRepo;

    /**
     * Если такой чат уже существует, то true, иначе false
     *
     * @param chatId
     * @return
     */
    public boolean isChatInit(Long chatId) {
        return chatConfigRepo.findAllByChatId(chatId) != null;
    }

    /**
     * Сохраняем в бд новую запись о чате
     *
     * @param chatId
     */
    public void initChat(Long chatId) {
        chatConfigRepo.save(new ChatEntity(chatId, BotState.DEFAULT));
    }

    /**
     * Удаляем из бд запись по chatId
     *
     * @param chatId
     */
    public void deleteChat(Long chatId) {
        chatConfigRepo.deleteByChatId(chatId);
    }

    /**
     * Устанавливает новый стейт для чата
     *
     * @param chatId
     * @param botState
     */
    public void setBotState(Long chatId, BotState botState) {
        ChatEntity chat = chatConfigRepo.findAllByChatId(chatId);
        chat.setBotState(botState);
        chatConfigRepo.save(chat);
    }

    /**
     * Получает стейт чата
     *
     * @param chatId
     */
    public BotState getBotState(Long chatId) {
        return chatConfigRepo.findAllByChatId(chatId).getBotState();
    }


    /**
     * Устанавливает чату город
     *
     * @param chatId
     * @param city
     */
    public void setCity(Long chatId, String city) {
        ChatEntity chatConfig = chatConfigRepo.findAllByChatId(chatId);
        chatConfig.setCity(city);
        chatConfigRepo.save(chatConfig);
    }

    /**
     * Получает город чата
     *
     * @param chatId
     * @return
     */
    public String getCity(Long chatId) {
        ChatEntity allByChatId = chatConfigRepo.findAllByChatId(chatId);
        return allByChatId.getCity();
    }

    public Location getLocation(Long chatId) {
        return chatConfigRepo.findAllByChatId(chatId).getLocation();
    }

    public void setLocation(Long chatId, Location location) {
        locationRepo.save(location);
        ChatEntity chatConfig = chatConfigRepo.findAllByChatId(chatId);
        chatConfig.setLocation(location);
        chatConfigRepo.save(chatConfig);
    }
}
