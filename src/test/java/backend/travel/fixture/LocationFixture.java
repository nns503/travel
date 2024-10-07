package backend.travel.fixture;

import backend.travel.domain.region.entity.Location;

@SuppressWarnings("NonAsciiCharacters")
public class LocationFixture {

    public static Location 위치1() {
        return Location.builder()
                .title("집")
                .address("대전 광역시 서구 월평로")
                .y(256)
                .x(256)
                .build();
    }
}
