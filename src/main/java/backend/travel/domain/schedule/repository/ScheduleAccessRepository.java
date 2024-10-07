package backend.travel.domain.schedule.repository;

import backend.travel.domain.schedule.entity.ScheduleAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScheduleAccessRepository extends JpaRepository<ScheduleAccess, Long> {

    Optional<ScheduleAccess> findScheduleAccessByUserIdAndScheduleId(Long userId, Long scheduleId);

    Boolean existsScheduleAccessByUserIdAndScheduleId(Long userId, Long scheduleId);
}
