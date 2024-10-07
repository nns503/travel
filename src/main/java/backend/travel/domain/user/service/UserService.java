package backend.travel.domain.user.service;

import backend.travel.domain.user.dto.request.UpdateUserProfileRequest;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.exception.NotFoundUserException;
import backend.travel.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void updateUserProfile(Long userId, UpdateUserProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.updateNickname(request.nickname());
    }

    @Transactional
    public void deleteUserProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(NotFoundUserException::new);

        user.delete();
    }
}
