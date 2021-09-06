package firsov.study.sravnitaxibot.common.entity;

import firsov.study.sravnitaxibot.common.BotState;
import firsov.study.sravnitaxibot.common.model.Coords;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;

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

    @OneToOne
    private Location location;

    public ChatEntity(Long chatId, BotState aDefault) {
        this.chatId = chatId;
        this.botState = aDefault;
    }

    public ChatEntity() {

    }
}
