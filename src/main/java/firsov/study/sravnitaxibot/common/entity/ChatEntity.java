package firsov.study.sravnitaxibot.common.entity;

import firsov.study.sravnitaxibot.common.BotState;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class ChatEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    private Long chatId;

    @NonNull
    private BotState botState;

    private String city;

    public ChatEntity(Long chatId, BotState aDefault) {
        this.chatId = chatId;
        this.botState = aDefault;
    }

    public ChatEntity() {

    }
}
