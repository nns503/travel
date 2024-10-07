package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.schedule.dto.request.AddScheduleAccessRequest;
import backend.travel.domain.schedule.dto.request.RemoveScheduleAccessRequest;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.entity.ScheduleAccess;
import backend.travel.domain.schedule.repository.ScheduleAccessRepository;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.ScheduleFixture;
import backend.travel.fixture.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class ScheduleAccessIntegrationTest  extends IntegrationTest {

    @Autowired
    private ScheduleAccessRepository scheduleAccessRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄 접근 권한을 부여함")
    void addScheduleAccess() throws Exception {
        User user1 = getUser1();
        User user2 = getUser2();
        Schedule schedule = getSchedule(user1);

        AddScheduleAccessRequest request = new AddScheduleAccessRequest(user2.getId());
        String body = objectMapper.writeValueAsString(request);
        mvc.perform(post("/api/schedule/"  + schedule.getId()+"/access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/scheduleAccess/add-access",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("addUserId").type(JsonFieldType.NUMBER).description("추가할 회원 id")
                                )
                        )
                );

        assertThat(scheduleAccessRepository.existsScheduleAccessByUserIdAndScheduleId(user2.getId(), schedule.getId())).isTrue();
    }

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄 접근 권한을 제거함")
    void removeScheduleAccess() throws Exception{
        User user1 = getUser1();
        User user2 = getUser2();
        Schedule schedule = getSchedule(user1);

        ScheduleAccess scheduleAccess = ScheduleAccess.builder()
                .user(user2)
                .schedule(schedule)
                .build();
        scheduleAccessRepository.save(scheduleAccess);
        RemoveScheduleAccessRequest request = new RemoveScheduleAccessRequest(user2.getId());
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(delete("/api/schedule/"  + schedule.getId()+"/access")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/scheduleAccess/remove-access",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("removeUserId").type(JsonFieldType.NUMBER).description("제거할 회원 id")
                                )
                        )
                );

        assertThat(scheduleAccessRepository.existsScheduleAccessByUserIdAndScheduleId(user2.getId(), schedule.getId())).isFalse();
    }

    private User getUser1() {
        User 테스트유저1 = UserFixture.일반회원1;
        return userRepository.save(테스트유저1);
    }

    private User getUser2() {
        User 테스트유저2 = UserFixture.일반회원2;
        return userRepository.save(테스트유저2);
    }

    private Schedule getSchedule(User user) {
        Schedule 스케줄1 = ScheduleFixture.스케줄1(user);
        return scheduleRepository.save(스케줄1);
    }
}
