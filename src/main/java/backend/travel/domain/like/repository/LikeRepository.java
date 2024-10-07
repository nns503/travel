package backend.travel.domain.like.repository;

import backend.travel.domain.like.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndScheduleId(Long userId, Long scheduleId);
}
