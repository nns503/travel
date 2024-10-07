package backend.travel.domain.region.dto.response;

import backend.travel.domain.region.dto.RegionListDTO;

import java.util.List;

public record GetAllRegionResponse(
        List<RegionListDTO> regionList
) {
}
