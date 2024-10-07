package backend.travel.global.auth.service;

import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.entity.UserRole;
import backend.travel.domain.user.exception.DuplicateNicknameException;
import backend.travel.domain.user.exception.DuplicateUsernameException;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.global.auth.dto.request.JoinRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class JoinService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void join(JoinRequest joinRequest) {
        String username = joinRequest.username();
        String password = joinRequest.password();
        String nickname = joinRequest.nickname();

        checkDuplicateUsername(username);
        checkDuplicateNickname(nickname);

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(UserRole.ROLE_USER)
                .isDelete(false)
                .build();

        userRepository.save(user);
    }

    private void checkDuplicateNickname(String nickname) {
        if(userRepository.existsByNickname(nickname)){
            throw new DuplicateNicknameException();
        }
    }

    private void checkDuplicateUsername(String username) {
        if(userRepository.existsByUsername(username)){
            throw new DuplicateUsernameException();
        }
    }
}
