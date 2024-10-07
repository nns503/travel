package backend.travel.domain.cost.service;

import backend.travel.domain.cost.dto.request.CreateCostRequest;
import backend.travel.domain.cost.dto.request.UpdateCostRequest;
import backend.travel.domain.cost.dto.response.CreateCostResponse;
import backend.travel.domain.cost.dto.response.GetAllCostResponse;
import backend.travel.domain.cost.entity.Cost;
import backend.travel.domain.cost.exception.NotFoundCostException;
import backend.travel.domain.cost.repository.CostRepository;
import backend.travel.domain.region.entity.Region;
import backend.travel.domain.region.exception.NotFoundRegionException;
import backend.travel.domain.region.repository.RegionRepository;
import backend.travel.domain.schedule.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CostService {

    private final CostRepository costRepository;
    private final RegionRepository regionRepository;

    @Transactional
    public CreateCostResponse createCost(Long scheduleId, Long regionId, CreateCostRequest request){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(NotFoundRegionException::new);

        Schedule schedule = region.getSchedule();

        Cost cost = Cost.builder()
                .expense(request.expense())
                .description(request.description())
                .region(region)
                .build();

        schedule.updateAllCost(0L, request.expense());
        Cost savedCost = costRepository.save(cost);

        return new CreateCostResponse(savedCost.getId());
    }

    @Transactional
    public void updateCost(Long regionId, Long costId, UpdateCostRequest request){
        Region region = regionRepository.findById(regionId)
                .orElseThrow(NotFoundRegionException::new);
        Cost cost = costRepository.findById(costId)
                .orElseThrow(NotFoundCostException::new);

        Schedule schedule = region.getSchedule();
        Long previousExpense = cost.getExpense();
        cost.updateExpense(request.expense(), request.description());
        Long currentExpense = cost.getExpense();

        schedule.updateAllCost(previousExpense, currentExpense);
    }

    @Transactional
    public void deleteCost(Long regionId, Long costId) {
        Region region = regionRepository.findById(regionId)
                .orElseThrow(NotFoundRegionException::new);
        Cost cost = costRepository.findById(costId)
                .orElseThrow(NotFoundCostException::new);

        Schedule schedule = region.getSchedule();
        schedule.updateAllCost(cost.getExpense(), 0L);
        costRepository.delete(cost);
    }

    public GetAllCostResponse getAllCostList(Long regionId) {
        return new GetAllCostResponse(costRepository.findCostList(regionId));
    }

}
