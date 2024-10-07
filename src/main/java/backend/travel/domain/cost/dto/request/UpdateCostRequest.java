package backend.travel.domain.cost.dto.request;

public record UpdateCostRequest(
        Long expense,
        String description
) {
}
