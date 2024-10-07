package backend.travel.domain.region.entity;

import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Embeddable
public class Location {

    private String title;
    private String address;
    private Integer x;
    private Integer y;

    @Builder
    public Location(String title, String address, Integer x, Integer y) {
        this.title = title;
        this.address = address;
        this.x = x;
        this.y = y;
    }
}
