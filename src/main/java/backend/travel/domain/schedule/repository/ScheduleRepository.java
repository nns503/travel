package backend.travel.domain.schedule.repository;

import backend.travel.domain.schedule.dto.ScheduleListDTO;
import backend.travel.domain.schedule.entity.Schedule;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    Optional<Schedule> findByIdAndIsDeleteFalse(Long id);

    @Query(value ="select new backend.travel.domain.schedule.dto.ScheduleListDTO(" +
            "s.title, s.startDate, s.endDate, s.allCost)" +
            "from Schedule s ")
    Slice<ScheduleListDTO> findScheduleList(Pageable pageable);

    @Query(value ="select new backend.travel.domain.schedule.dto.ScheduleListDTO(" +
            "s.title, s.startDate, s.endDate, s.allCost)" +
    "from Schedule s " +
    "where s.user.id = :userId")
    Slice<ScheduleListDTO> findMyScheduleList(Long userId, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Schedule s SET s.likeCount = s.likeCount + 1 WHERE s.id = :scheduleId")
    void updateLikeCount(Long scheduleId);

    Optional<Schedule> findScheduleByIdAndIsDeleteFalse(Long id);
}
