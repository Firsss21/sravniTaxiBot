package firsov.study.sravnitaxibot.common.repository;

import firsov.study.sravnitaxibot.common.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepo extends JpaRepository<Location, Long> {
}
