package backend.travel.domain.like.controller;

import backend.travel.domain.like.service.LikeService;
import backend.travel.global.auth.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/schedule/{scheduleId}/like")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<String> likeSchedule(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable  Long scheduleId
    ) {
        likeService.likeSchedule(authUser.getUserId(), scheduleId);
        return ResponseEntity.ok("ok");
    }
}
