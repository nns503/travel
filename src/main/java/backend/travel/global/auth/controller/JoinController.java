package backend.travel.global.auth.controller;

import backend.travel.global.auth.dto.request.JoinRequest;
import backend.travel.global.auth.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class JoinController {

    private final JoinService joinService;

    @PostMapping("/join")
    public ResponseEntity<String> join(
            @RequestBody @Validated JoinRequest request
    ){
        joinService.join(request);

        return ResponseEntity.ok()
                .body("Successfully joined");
    }
}
