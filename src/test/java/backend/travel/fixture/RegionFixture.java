package backend.travel.fixture;

import backend.travel.domain.region.entity.Location;
import backend.travel.domain.region.entity.Region;
import backend.travel.domain.schedule.entity.Schedule;

import java.time.LocalDateTime;

@SuppressWarnings("NonAsciiCharacters")
public class RegionFixture {

    public static Region 지역1(Schedule schedule, Location location){
        return Region.builder()
                .memo("테스트 지역")
                .startDate(LocalDateTime.of(2024, 5, 4, 0, 0))
                .endDate(LocalDateTime.of(2024, 5, 6, 0, 0))
                .location(location)
                .schedule(schedule)
                .build();
    }
}
