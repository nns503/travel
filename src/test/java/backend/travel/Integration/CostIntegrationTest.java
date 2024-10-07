package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.JsonMvcResponseMapper;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.cost.dto.request.CreateCostRequest;
import backend.travel.domain.cost.dto.request.UpdateCostRequest;
import backend.travel.domain.cost.dto.response.CreateCostResponse;
import backend.travel.domain.cost.entity.Cost;
import backend.travel.domain.cost.repository.CostRepository;
import backend.travel.domain.region.entity.Location;
import backend.travel.domain.region.entity.Region;
import backend.travel.domain.region.repository.RegionRepository;
import backend.travel.domain.schedule.entity.Schedule;
import backend.travel.domain.schedule.repository.ScheduleRepository;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class CostIntegrationTest extends IntegrationTest {

    @Autowired
    private CostRepository costRepository;
    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private UserRepository userRepository;


    @Test
    @WithMockCustomUser
    @DisplayName("예산을 생성함")
    void createCost() throws Exception{

        User user = getUser();
        Schedule schedule = getSchedule(user);
        Location location = getLocation();
        Region region = getRegion(schedule, location);

        Long savedExpense = 10000L;
        CreateCostRequest request = new CreateCostRequest(savedExpense, "노래방");

        String body = objectMapper.writeValueAsString(request);
        MvcResult result = mvc.perform(post("/api/schedule/" + schedule.getId() + "/region/" + region.getId() + "/cost")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/cost/create-cost",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("expense").type(JsonFieldType.NUMBER).description("예산 비용"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("예산 설명")
                                )
                        )
                )
                .andReturn();

        CreateCostResponse createCostResponse = JsonMvcResponseMapper.from(result, CreateCostResponse.class);

        Cost savedCost = costRepository.findById(createCostResponse.id())
                .orElseThrow(AssertionError::new);
        assertThat(costRepository.count()).isEqualTo(1);

        Long updateAllCost = regionRepository.findById(savedCost.getRegion().getId())
                .orElseThrow(AssertionError::new)
                .getSchedule()
                .getAllCost();

        assertThat(updateAllCost).isEqualTo(savedExpense);
    }

    @Test
    @WithMockUser
    @DisplayName("예산을 수정함")
    void updateCost() throws Exception{

        User user = getUser();
        Schedule schedule = getSchedule(user);
        Location location = getLocation();
        Region region = getRegion(schedule, location);
        Cost cost = getCost(region);

        Long prvAllCost = schedule.getAllCost();
        Long updateExpense = 20000L;
        String updateDescription = "PC방";
        UpdateCostRequest request = new UpdateCostRequest(updateExpense, updateDescription);
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(patch("/api/schedule/"  + schedule.getId()+"/region/"+ region.getId()+"/cost/" + cost.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/cost/update-cost",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("expense").type(JsonFieldType.NUMBER).description("수정할 예산 비용"),
                                        fieldWithPath("description").type(JsonFieldType.STRING).description("수정할 예산 설명")
                                )
                        )
                );

        Cost findCost = costRepository.findById(cost.getId()).
                orElseThrow(AssertionError::new);
        assertThat(findCost.getExpense()).isEqualTo(updateExpense);
        assertThat(findCost.getDescription()).isEqualTo(updateDescription);
        Long updateAllCost = regionRepository.findById(findCost.getRegion().getId())
                .orElseThrow(AssertionError::new)
                .getSchedule()
                .getAllCost();
        assertThat(updateAllCost).isEqualTo(prvAllCost + updateExpense);
    }


    @Test
    @WithMockUser
    @DisplayName("예산을 삭제함")
    void deleteCost() throws Exception{

        User user = getUser();
        Schedule schedule = getSchedule(user);
        Location location = getLocation();
        Region region = getRegion(schedule, location);
        Cost cost = getCost(region);

        Long prvAllCost = schedule.getAllCost();
        Long expense = cost.getExpense();

        mvc.perform(delete("/api/schedule/"  + schedule.getId()+"/region/"+ region.getId()+"/cost/" + cost.getId()))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/cost/delete-cost"
                        )
                );


        assertThat(costRepository.findById(cost.getId()).isEmpty()).isTrue();
        Schedule findSchedule = scheduleRepository.findById(schedule.getId()).orElseThrow(AssertionError::new);
        Long findScheduleAllCost = findSchedule.getAllCost();
        assertThat(findScheduleAllCost).isEqualTo(prvAllCost - expense);
    }

    private User getUser() {
        User 테스트유저1 = UserFixture.일반회원1;
        return userRepository.save(테스트유저1);
    }

    private Schedule getSchedule(User user) {
        Schedule 스케줄1 = ScheduleFixture.스케줄1(user);
        return scheduleRepository.save(스케줄1);
    }

    private Location getLocation(){
        return LocationFixture.위치1();
    }

    private Region getRegion(Schedule schedule, Location location) {
        Region 지역1 = RegionFixture.지역1(schedule, location);
        return regionRepository.save(지역1);
    }

    private Cost getCost(Region region) {
        Cost 예산1 = CostFixture.예산1(region);
        return costRepository.save(예산1);
    }
}
