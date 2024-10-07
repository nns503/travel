package backend.travel.domain.cost.entity;

import backend.travel.domain.region.entity.Region;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Cost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "region_id")
    private Region region;

    private Long expense;

    private String description;

    @Builder
    public Cost(Long id, Region region, Long expense, String description) {
        this.id = id;
        this.region = region;
        this.expense = expense;
        this.description = description;
    }

    public void updateExpense(Long expense, String description) {
        this.expense = expense;
        this.description = description;
    }
}
