package backend.travel.domain.schedule.controller;

import backend.travel.domain.schedule.dto.request.AddScheduleAccessRequest;
import backend.travel.domain.schedule.dto.request.RemoveScheduleAccessRequest;
import backend.travel.domain.schedule.service.ScheduleAccessService;
import backend.travel.global.auth.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule/{scheduleId}/access")
public class ScheduleAccessController {

    private final ScheduleAccessService scheduleAccessService;

    @PostMapping
    public ResponseEntity<String> addScheduleAccess(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody AddScheduleAccessRequest request
    ){
        scheduleAccessService.addAccess(authUser.getUserId(), request.addUserId(), scheduleId);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping
    public ResponseEntity<String> removeScheduleAccess(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable("scheduleId") Long scheduleId,
            @RequestBody RemoveScheduleAccessRequest request
    ){
        scheduleAccessService.removeAccess(authUser.getUserId(), request.removeUserId(), scheduleId);
        return ResponseEntity.ok("ok");
    }
}
