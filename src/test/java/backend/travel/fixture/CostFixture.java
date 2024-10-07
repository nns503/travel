package backend.travel.fixture;

import backend.travel.domain.cost.entity.Cost;
import backend.travel.domain.region.entity.Region;

@SuppressWarnings("NonAsciiCharacters")
public class CostFixture {

    public static Cost 예산1(Region region){
        return Cost.builder()
                .id(1L)
                .expense(0L)
                .description("그냥놈")
                .region(region)
                .build();
    }
}
