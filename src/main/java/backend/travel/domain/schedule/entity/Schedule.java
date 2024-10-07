package backend.travel.domain.schedule.entity;

import backend.travel.domain.user.entity.User;
import backend.travel.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedules")
@Entity
public class Schedule extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "allCost", nullable = false)
    private Long allCost;

    @Column(name = "memo", nullable = false)
    private String memo;

    @Enumerated(EnumType.STRING)
    private ScheduleStatus status;

    @Column(name = "is_public", nullable = false)
    private Boolean isPublic;

    @Column(name = "is_delete", nullable = false)
    private Boolean isDelete;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @Builder
    public Schedule(Long id, String title, LocalDate startDate, LocalDate endDate, Long allCost, String memo, ScheduleStatus status, Boolean isPublic, Boolean isDelete, Long likeCount, User user) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.allCost = allCost;
        this.memo = memo;
        this.status = status;
        this.isPublic = isPublic;
        this.isDelete = isDelete;
        this.likeCount = likeCount;
        this.user = user;
    }

    public void updateDateTime(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void updateMemo(String memo){
        this.memo = memo;
    }

    public void updateAllCost(Long previousExpense, Long currentExpense){
        this.allCost -= previousExpense;
        this.allCost += currentExpense;
    }

    public void delete(){
        this.isDelete = true;
    }
}
