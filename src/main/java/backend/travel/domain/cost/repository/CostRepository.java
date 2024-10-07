package backend.travel.domain.cost.repository;

import backend.travel.domain.cost.dto.CostListDTO;
import backend.travel.domain.cost.entity.Cost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CostRepository extends JpaRepository<Cost, Long> {

    @Query(value = "select new backend.travel.domain.cost.dto.CostListDTO(" +
            "c.expense, c.description)" +
            "from Cost c " +
            "where c.region.id = :regionId")
    List<CostListDTO> findCostList(Long regionId);

    void deleteAllByRegionId(Long regionId);
}
