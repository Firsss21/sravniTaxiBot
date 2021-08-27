package firsov.study.sravnitaxibot.common.repository;

import firsov.study.sravnitaxibot.common.entity.ChatEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatConfigRepo extends JpaRepository<ChatEntity, Long> {
    ChatEntity findAllByChatId(Long id);
    void deleteByChatId(Long id);
}
