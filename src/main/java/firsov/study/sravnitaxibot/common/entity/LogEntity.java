package firsov.study.sravnitaxibot.common.entity;

import com.google.api.client.util.DateTime;
import firsov.study.sravnitaxibot.common.BotState;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class LogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long chatId;

    private String destination;

    private DateTime date;
}
