package backend.travel.Integration;

import backend.travel.common.ApiDocumentUtils;
import backend.travel.common.IntegrationTest;
import backend.travel.common.WithMockCustomUser;
import backend.travel.domain.user.dto.request.UpdateUserProfileRequest;
import backend.travel.domain.user.entity.User;
import backend.travel.domain.user.repository.UserRepository;
import backend.travel.fixture.UserFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("NonAsciiCharacters")
public class UserIntegrationTest extends IntegrationTest {

    @Autowired
    private UserRepository userRepository;

    User 테스트유저1;

    @BeforeEach
    void setup(){
        테스트유저1 = UserFixture.일반회원1;
    }

    @Test
    @WithMockCustomUser
    @DisplayName("회원 정보를 업데이트함")
    void updateUserProfile() throws Exception {
        User savedUser = userRepository.save(테스트유저1);
        String changedNickname = "변경된 닉네임";
        UpdateUserProfileRequest request = new UpdateUserProfileRequest(changedNickname);
        String body = objectMapper.writeValueAsString(request);

        mvc.perform(patch("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/user/updateUserProfile",
                                ApiDocumentUtils.getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("nickname").type(JsonFieldType.STRING).description("변경할 닉네임")
                                ))
                );

        assertThat(savedUser.getNickname()).isEqualTo(changedNickname);
    }

    @Test
    @WithMockCustomUser
    @DisplayName("회원 계정을 삭제함")
    void deleteUserProfile() throws Exception {
        User savedUser = userRepository.save(테스트유저1);

        mvc.perform(delete("/api/users"))
                .andExpect(status().isOk())
                .andDo(
                        document(
                                "Integration/user/deleteUserProfile",
                                ApiDocumentUtils.getDocumentResponse()
                        )
                );

        assertThat(savedUser.getIsDelete()).isTrue();
    }
}
