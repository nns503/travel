package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.like.repository.LikeRepository;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.ScheduleFixture;
import backend.travel.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class LikeIntegrationTest extends IntegrationTest {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄에 좋아요를 누름")
    void likeSchedule() throws Exception{

        User user = getUser();
        Schedule schedule = getSchedule(user);

        mvc.perform(post("/api/schedule/"+schedule.getId()+"/like"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/schedule/updateScheduleDateTime",
                                ApiDocumentUtils.getDocumentResponse())
                );

        Schedule updatedSchedule = scheduleRepository.findById(schedule.getId())
                .orElseThrow(AssertionError::new);
        assertThat(likeRepository.existsByUserIdAndScheduleId(user.getId(), updatedSchedule.getId())).isTrue();
        assertThat(updatedSchedule.getLikeCount()).isEqualTo(1L);
    }

    private User getUser() {
        User 테스트유저1 = UserFixture.일반회원1;
        return userRepository.save(테스트유저1);
    }

    private Schedule getSchedule(User user) {
        Schedule 스케줄1 = ScheduleFixture.스케줄1(user);
        return scheduleRepository.save(스케줄1);
    }
}
