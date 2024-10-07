package backend.travel.domain.user.controller;

import backend.travel.domain.user.dto.request.UpdateUserProfileRequest;
import backend.travel.domain.user.service.UserService;
import backend.travel.global.auth.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PatchMapping
    public ResponseEntity<String> updateUserProfile(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody @Validated UpdateUserProfileRequest request
    ){
        userService.updateUserProfile(authUser.getUserId(), request);

        return ResponseEntity.ok("ok");
    }

    @DeleteMapping
    public ResponseEntity<String> deleteUserProfile(
            @AuthenticationPrincipal AuthUser authUser
    ){
        userService.deleteUserProfile(authUser.getUserId());
        return ResponseEntity.ok("ok");
    }
}
