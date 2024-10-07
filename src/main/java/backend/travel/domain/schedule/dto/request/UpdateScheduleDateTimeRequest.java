package backend.travel.domain.schedule.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UpdateScheduleDateTimeRequest(
        @NotNull(message = "시작 날짜를 작성해주세요")
        LocalDate startDate,
        @NotNull(message = "종료 날짜를 작성해주세요")
        LocalDate endDate
) {
}
