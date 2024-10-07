package backend.travel.domain.cost.dto.response;

import backend.travel.domain.cost.dto.CostListDTO;

import java.util.List;

public record GetAllCostResponse(
        List<CostListDTO> costList
) {
}
