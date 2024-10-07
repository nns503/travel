package backend.travel.domain.schedule.controller;

import backend.travel.domain.schedule.dto.request.CreateScheduleRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleDateTimeRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleMemoRequest;
import backend.travel.domain.schedule.dto.response.CreateScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetAllScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetMyAllScheduleResponse;
import backend.travel.domain.schedule.dto.response.GetScheduleResponse;
import backend.travel.domain.schedule.service.ScheduleService;
import backend.travel.global.auth.entity.AuthUser;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CreateScheduleResponse> createSchedule(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody @Validated CreateScheduleRequest request
    ){
        CreateScheduleResponse response = scheduleService.createSchedule(authUser.getUserId(), request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{scheduleId}")
    public ResponseEntity<String> updateScheduleDateTime(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId,
            @RequestBody @Validated UpdateScheduleDateTimeRequest request
    ){
        scheduleService.updateSchedule(authUser.getUserId(), scheduleId, request);
        return ResponseEntity.ok("ok");
    }

    @PatchMapping("/{scheduleId}/memo")
    public ResponseEntity<String> updateScheduleMemo(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId,
            @RequestBody @Validated UpdateScheduleMemoRequest request
    ){
        scheduleService.updateScheduleMemo(authUser.getUserId(), scheduleId, request);
        return ResponseEntity.ok("ok");
    }

    @DeleteMapping("{scheduleId}")
    public ResponseEntity<String> deleteSchedule(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId
    ){
        scheduleService.deleteSchedule(authUser.getUserId(), scheduleId);
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> getSchedule(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long scheduleId
    ){
        GetScheduleResponse response = scheduleService.getSchedule(authUser.getUserId(), scheduleId);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GetAllScheduleResponse> getAllSchedule(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.") Integer page
    ){
        PageRequest pageable = PageRequest.of(page - 1, 10);
        GetAllScheduleResponse response = scheduleService.getAllSchedule(pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my")
    public ResponseEntity<GetMyAllScheduleResponse> getMyAllSchedule(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.") Integer page
    ){
        PageRequest pageable = PageRequest.of(page - 1, 10);
        GetMyAllScheduleResponse response = scheduleService.getMyAllSchedule(authUser.getUserId(), pageable);
        return  ResponseEntity.ok(response);
    }


}
