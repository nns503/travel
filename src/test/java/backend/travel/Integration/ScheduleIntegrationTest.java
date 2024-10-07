package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.schedule.dto.request.CreateScheduleRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleDateTimeRequest;
import backend.travel.domain.schedule.dto.request.UpdateScheduleMemoRequest;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.fixture.ScheduleFixture;
import backend.travel.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class ScheduleIntegrationTest extends IntegrationTest {

    @Autowired
    private ScheduleRepository scheduleRepository;


    User 테스트유저1;
    Schedule 테스트스케줄1;

    @BeforeEach
    void setup(){
        테스트유저1 = UserFixture.일반회원1;
        테스트스케줄1 = ScheduleFixture.스케줄1(테스트유저1);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄을 생성함")
    void createSchedule() throws Exception {
        CreateScheduleRequest request = new CreateScheduleRequest("테스트제목",
                LocalDate.of(2024, 9, 9),
                LocalDate.of(2024, 10, 10));

        String body = objectMapper.writeValueAsString(request);
        mvc.perform(post("/api/schedule")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/schedule/create-schedule",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("스케줄 제목"),
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("스케줄 시작 날짜"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("스케줄 종료 날짜")
                                )
                        )
                );
        assertThat(scheduleRepository.count()).isEqualTo(1);

    }

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄 시간을 변경함")
    void updateScheduleDateTime() throws Exception{


        LocalDate startDate = LocalDate.of(2024, 11, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 1);

        UpdateScheduleDateTimeRequest request = new UpdateScheduleDateTimeRequest(
                startDate, endDate);

        Schedule saveSchedule = scheduleRepository.save(테스트스케줄1);

        String body = objectMapper.writeValueAsString(request);
        mvc.perform(patch("/api/schedule/" + saveSchedule.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/schedule/updateScheduleDateTime",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("startDate").type(JsonFieldType.STRING).description("변경할 스케줄 시작 날짜"),
                                        fieldWithPath("endDate").type(JsonFieldType.STRING).description("변경할 스케줄 종료 날짜")
                                ))
                );

        Schedule updatedSchedule = scheduleRepository.findById(saveSchedule.getId())
                .orElseThrow(AssertionError::new);

        assertThat(updatedSchedule.getStartDate()).isEqualTo(startDate);
        assertThat(updatedSchedule.getEndDate()).isEqualTo(endDate);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄 메모를 작성함")
    void updateScheduleMemo() throws Exception{
        String testMemo = "테스트 메모";
        UpdateScheduleMemoRequest request = new UpdateScheduleMemoRequest(testMemo);

        Schedule saveSchedule = scheduleRepository.save(테스트스케줄1);

        String body = objectMapper.writeValueAsString(request);
        mvc.perform(patch("/api/schedule/" + saveSchedule.getId() +"/memo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/schedule/updateScheduleMemo",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("memo").type(JsonFieldType.STRING).description("변경할 스케줄 메모")
                                ))
                );

        Schedule updatedSchedule = scheduleRepository.findById(saveSchedule.getId())
                .orElseThrow(AssertionError::new);
        assertThat(updatedSchedule.getMemo()).isEqualTo(testMemo);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("스케줄을 삭제함")
    void deleteSchedule() throws Exception{
        Schedule saveSchedule = scheduleRepository.save(테스트스케줄1);
        mvc.perform(delete("/api/schedule/" + saveSchedule.getId()))
                .andDo(
                        document(
                                "Integration/schedule/deleteSchedule",
                                ApiDocumentUtils.getDocumentResponse()
                        )
                );

        Schedule updatedSchedule = scheduleRepository.findById(saveSchedule.getId())
                .orElseThrow(AssertionError::new);
        assertThat(updatedSchedule.getIsDelete()).isTrue();
    }


}
