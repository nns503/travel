package backend.travel.common;

import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.UserFixture;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("test")
public class InitDB {

    private final UserRepository userRepository;

    public InitDB(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void initDB() {
        userRepository.save(UserFixture.일반회원1);
    }
}
