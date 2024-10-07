package backend.travel.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;

public class JsonMvcResponseMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T from(MvcResult result, Class<T> responseType) throws Exception {
        String responseBody = result.getResponse().getContentAsString();
        return objectMapper.readValue(responseBody, responseType);
    }
}
