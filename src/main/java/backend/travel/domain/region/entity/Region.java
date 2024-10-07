package backend.travel.domain.region.entity;

import backend.travel.domain.schedule.entity.Schedule;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String memo;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Embedded
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="schedule_id")
    private Schedule schedule;

    @Builder
    public Region(Long id, String memo, LocalDateTime startDate, LocalDateTime endDate, Location location, Schedule schedule) {
        this.id = id;
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
        this.schedule = schedule;
    }

    public void updateRegion(String memo, LocalDateTime startDate, LocalDateTime endDate, Location location) {
        this.memo = memo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.location = location;
    }
}
