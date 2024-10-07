package backend.travel.fixture;

import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.entity.UserRole;

@SuppressWarnings("NonAsciiCharacters")
public class UserFixture {

    public static final User 일반회원1 = User.builder()
            .id(1L)
            .username("normal1234")
            .password("password1234")
            .nickname("일반회원1")
            .role(UserRole.ROLE_USER)
            .isDelete(false)
            .build();

    public static final User 일반회원2 = User.builder()
            .id(2L)
            .username("normal4567")
            .password("password4567")
            .nickname("일반회원2")
            .role(UserRole.ROLE_USER)
            .isDelete(false)
            .build();
}
