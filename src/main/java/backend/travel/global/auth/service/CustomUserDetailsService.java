package backend.travel.global.auth.service;

import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.exception.NotFoundUsernameException;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.global.auth.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(NotFoundUsernameException::new);

        return new AuthUser(user);
    }
}
