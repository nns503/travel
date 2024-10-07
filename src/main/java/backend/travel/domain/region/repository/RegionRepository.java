package backend.travel.domain.region.repository;

import backend.travel.domain.region.dto.RegionListDTO;
import backend.travel.domain.region.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {

    @Query(value = "select new backend.travel.domain.region.dto.RegionListDTO(" +
            "r.memo, r.startDate, r.endDate, r.location)" +
            "from Region r " +
            "where r.schedule.id = :scheduleId " +
            "order by r.startDate")
    List<RegionListDTO> findRegionList(Long scheduleId);
}
