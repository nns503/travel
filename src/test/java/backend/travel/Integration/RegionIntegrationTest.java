package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.JsonMvcResponseMapper;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.region.dto.request.CreateRegionRequest;
import backend.travel.domain.region.dto.response.CreateRegionResponse;
import backend.travel.domain.region.entity.Region;
import backend.travel.domain.region.repository.RegionRepository;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.ScheduleFixture;
import backend.travel.fixture.UserFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MvcResult;

import java.time.LocalDateTime;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class RegionIntegrationTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private RegionRepository regionRepository;

    @Test
    @WithMockCustomUser
    @DisplayName("일정을 생성함")
    void createRegion() throws Exception{
        User user = getUser();
        Schedule schedule = getSchedule(user);
        Schedule savedSchedule = scheduleRepository.save(schedule);
        LocalDateTime prvUpdatedAt = savedSchedule.getUpdatedAt();
        CreateRegionRequest request = new CreateRegionRequest(
                "테스트제목",
                "대전 광역시 서구 월평로",
                256,
                256,
                "테스트메모",
                LocalDateTime.of(2024, 5, 3, 0, 0),
                LocalDateTime.of(2024, 5, 6, 0, 0));

        String body = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/api/schedule/" + savedSchedule.getId() + "/region")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/region/create-region",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("일정 이름"),
                                        fieldWithPath("address").type(JsonFieldType.STRING).description("일정 주소"),
                                        fieldWithPath("x").type(JsonFieldType.NUMBER).description("지역의 x 좌표"),
                                        fieldWithPath("y").type(JsonFieldType.NUMBER).description("지역의 y 좌표"),
                                        fieldWithPath("memo").type(JsonFieldType.STRING).description("일정 메모"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("일정 시작 날짜"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("일정 종료 날짜")
                                )
                        )
                )
                .andReturn();

        Assertions.assertThat(regionRepository.findAll()).hasSize(1);
        CreateRegionResponse createRegionResponse = JsonMvcResponseMapper.from(result, CreateRegionResponse.class);
        Region savedRegion = regionRepository.findById(createRegionResponse.id())
                .orElseThrow(AssertionError::new);
        Assertions.assertThat(savedRegion.getSchedule().getUpdatedAt()).isAfter(prvUpdatedAt);
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
