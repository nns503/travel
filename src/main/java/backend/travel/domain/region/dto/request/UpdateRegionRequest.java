package backend.travel.domain.region.dto.request;

import java.time.LocalDateTime;

public record UpdateRegionRequest(
        String title,
        String address,
        Integer x,
        Integer y,
        String memo,
        LocalDateTime startDate,
        LocalDateTime endDate
) {
}
