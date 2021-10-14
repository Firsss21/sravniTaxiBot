package firsov.study.sravnitaxibot.common.repository;

import firsov.study.sravnitaxibot.common.entity.LogEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepo extends CrudRepository<LogEntity, Long> {
    List<LogEntity> findAll();
    LogEntity findTop1ByOrderByDate();
}
