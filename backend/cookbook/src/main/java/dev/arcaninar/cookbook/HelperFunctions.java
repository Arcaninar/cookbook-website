package dev.arcaninar.cookbook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import dev.arcaninar.cookbook.reposervice.BackblazeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HelperFunctions {
    @Autowired
    private BackblazeService backblazeService;

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public JsonNode convertImageToBase64(Object cookbook, String imageKey) throws JsonProcessingException {
        String base64Image = backblazeService.getFile(imageKey);
        String jsonString = objectMapper.writeValueAsString(cookbook);
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        ((ObjectNode) jsonNode).remove("imageKey");
        ((ObjectNode) jsonNode).put("image", base64Image);
        return jsonNode;
    }
}
