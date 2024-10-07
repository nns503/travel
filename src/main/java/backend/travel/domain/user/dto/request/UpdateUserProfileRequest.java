package backend.travel.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
        @NotBlank(message = "닉네임을 입력해주세요")
        @Pattern(regexp = "^[0-9a-zA-Zㄱ-ㅎ가-힣 ]*$", message = "한글, 영문, 숫자만 사용 가능합니다.")
        @Size(min = 4, max = 10, message = "길이는 4자에서 10자 이내로 작성해주세요")
        String nickname
) {
}
