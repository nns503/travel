package backend.travel.fixture;

import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.entity.ScheduleStatus;
import backend.travel.domain.user.entity.User;

import java.time.LocalDate;

@SuppressWarnings("NonAsciiCharacters")
public class ScheduleFixture {

    public static Schedule 스케줄1(User user){

        return Schedule.builder()
                .id(1L)
                .title("스케줄 제목")
                .startDate(LocalDate.of(2024, 5, 3))
                .endDate(LocalDate.of(2024, 6, 12))
                .allCost(0L)
                .memo("스케줄 메모")
                .status(ScheduleStatus.PLANNED)
                .isPublic(false)
                .isDelete(false)
                .likeCount(0L)
                .user(user)
                .build();
    }


}
