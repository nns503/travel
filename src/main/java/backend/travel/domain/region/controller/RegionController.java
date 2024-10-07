package backend.travel.domain.region.controller;

import backend.travel.domain.region.dto.request.CreateRegionRequest;
import backend.travel.domain.region.dto.request.UpdateRegionRequest;
import backend.travel.domain.region.dto.response.CreateRegionResponse;
import backend.travel.domain.region.dto.response.GetAllRegionResponse;
import backend.travel.domain.region.service.RegionService;
import backend.travel.global.auth.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule/{scheduleId}/region")
public class RegionController {

    private final RegionService regionService;

    @PostMapping
    public ResponseEntity<CreateRegionResponse> createRegion(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId,
            @RequestBody @Validated CreateRegionRequest request
            ) {
        CreateRegionResponse response = regionService.createRegion(authUser.getUserId(), scheduleId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{regionId}")
    public ResponseEntity<String> updateRegion(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId,
            @PathVariable Long regionId,
            @RequestBody @Validated UpdateRegionRequest request
    ){
        regionService.updateRegion(authUser.getUserId(), scheduleId, regionId, request);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("/{regionId}")
    public ResponseEntity<String> deleteRegion(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId,
            @PathVariable Long regionId
    ){
        regionService.deleteRegion(authUser.getUserId(), regionId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping
    public ResponseEntity<GetAllRegionResponse> getAllRegion(
            @PathVariable Long scheduleId
    ){
        GetAllRegionResponse response = regionService.getAllRegion(scheduleId);
        return ResponseEntity.ok(response);
    }
}
