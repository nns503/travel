package backend.travel.domain.cost.controller;

import backend.travel.domain.cost.dto.request.CreateCostRequest;
import backend.travel.domain.cost.dto.request.UpdateCostRequest;
import backend.travel.domain.cost.dto.response.CreateCostResponse;
import backend.travel.domain.cost.dto.response.GetAllCostResponse;
import backend.travel.domain.cost.service.CostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule/{scheduleId}/region/{regionId}/cost")
public class CostController {

    private final CostService costService;

    @PostMapping
    public ResponseEntity<CreateCostResponse> createCost(
            @PathVariable Long scheduleId,
            @PathVariable Long regionId,
            @RequestBody @Validated CreateCostRequest request
            ){
        CreateCostResponse response = costService.createCost(scheduleId, regionId, request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{costId}")
    public ResponseEntity<String> updateCost(
            @PathVariable Long scheduleId,
            @PathVariable Long regionId,
            @PathVariable Long costId,
            @RequestBody @Validated UpdateCostRequest request
    ){
        costService.updateCost(regionId, costId, request);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{costId}")
    public ResponseEntity<String> deleteCost(
            @PathVariable Long scheduleId,
            @PathVariable Long regionId,
            @PathVariable Long costId
    ){
        costService.deleteCost(regionId, costId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<GetAllCostResponse> getAllCostList(
            @PathVariable Long scheduleId,
            @PathVariable Long regionId
    ){
        GetAllCostResponse response = costService.getAllCostList(regionId);
        return ResponseEntity.ok(response);
    }
}
