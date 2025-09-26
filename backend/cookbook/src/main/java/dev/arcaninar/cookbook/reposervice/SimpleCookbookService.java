package dev.arcaninar.cookbook.reposervice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.arcaninar.cookbook.HelperFunctions;
import dev.arcaninar.cookbook.docobjects.SimpleCookbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SimpleCookbookService {
    @Autowired
    private SimpleCookbookRepository simpleCookbookRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    private List<JsonNode> addBase64Image(List<SimpleCookbook> simpleCookbookList) throws JsonProcessingException {
        ArrayList<JsonNode> jsonResponse = new ArrayList<>();
        for (SimpleCookbook cookbook: simpleCookbookList) {
            jsonResponse.add(helperFunctions.convertImageToBase64(cookbook, cookbook.getImageName()));
        }
        return jsonResponse;
    }

    public List<JsonNode> allSimpleCookbooks() throws JsonProcessingException {
        return addBase64Image(simpleCookbookRepository.findAll());

    }

    public List<JsonNode> SimpleCookbooksByKeyword(String keyword) throws JsonProcessingException {
        return addBase64Image(simpleCookbookRepository.findByKeyword(keyword));
    }
}