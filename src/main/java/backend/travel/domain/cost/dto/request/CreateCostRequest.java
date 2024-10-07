package backend.travel.domain.cost.dto.request;

public record CreateCostRequest(
        Long expense,
        String description
) {
}
