package backend.travel.domain.schedule.dto;

import java.time.LocalDate;

public record ScheduleListDTO(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        Long cost
) {
}
