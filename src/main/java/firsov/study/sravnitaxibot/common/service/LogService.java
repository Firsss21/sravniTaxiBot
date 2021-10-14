package firsov.study.sravnitaxibot.common.service;

import com.google.api.client.util.DateTime;
import firsov.study.sravnitaxibot.common.entity.ChatEntity;
import firsov.study.sravnitaxibot.common.entity.LogEntity;
import firsov.study.sravnitaxibot.common.repository.ChatConfigRepo;
import firsov.study.sravnitaxibot.common.repository.LogRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LogService {

   private LogRepo repo;
   private ChatConfigRepo chatRepo;

   public void saveLog(long chatId, String dest){
       LogEntity logEntity = new LogEntity();
       logEntity.setDate(new DateTime(System.currentTimeMillis()));
       logEntity.setDestination(dest);
       logEntity.setChatId(chatId);
       repo.save(logEntity);
   }

   public String getStatistic() {
       StringBuilder builder = new StringBuilder();
       builder.append("Количество запросов: ");
       List<LogEntity> logs = repo.findAll();
       builder.append(logs.size());
       LogEntity top1ByOrderByDate = repo.findTop1ByOrderByDate();
       if (top1ByOrderByDate != null) {
           builder.append(", последний запрос был: ");
            builder.append(top1ByOrderByDate.getDate());
       }
       List<ChatEntity> all = chatRepo.findAll();
       builder.append(", количество пользователей: " + all.size());
       return builder.toString();
   }
}
