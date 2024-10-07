package backend.travel.domain.schedule.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record CreateScheduleRequest(
        @NotEmpty(message = "제목을 작성해주세요")
        @Size(min = 1, max = 20, message = "제목은 1자에서 20자 사이로 작성해주세요")
        String title,
        @NotNull(message = "시작 날짜를 작성해주세요")
        LocalDate startDate,
        @NotNull(message = "종료 날짜를 작성해주세요")
        LocalDate endDate
) {
}
