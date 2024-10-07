package backend.travel.domain.region.dto;

import backend.travel.domain.region.entity.Location;

import java.time.LocalDateTime;

public record RegionListDTO(
        String memo,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Location location
) {
}
