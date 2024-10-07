package backend.travel.domain.schedule.dto.response;

import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.entity.ScheduleStatus;

import java.time.LocalDate;

public record GetScheduleResponse(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Long cost,
        String memo,
        ScheduleStatus status,
        Boolean isPublic,
        Long likeCount
) {
    public static GetScheduleResponse of(Schedule schedule) {
        return new GetScheduleResponse(
                schedule.getTitle(),
                schedule.getStartDate(),
                schedule.getEndDate(),
                schedule.getAllCost(),
                schedule.getMemo(),
                schedule.getStatus(),
                schedule.getIsPublic(),
                schedule.getLikeCount());
    }
}
