package dev.arcaninar.cookbook.reposervice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import dev.arcaninar.cookbook.HelperFunctions;
import dev.arcaninar.cookbook.docobjects.Cookbook;
import dev.arcaninar.cookbook.exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

@Service
public class CookbookService {
    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private HelperFunctions helperFunctions;

    public JsonNode cookbookById(String id) throws JsonProcessingException {
        Cookbook cookbook = cookbookRepository.findById(new ObjectId(id)).orElse(new Cookbook());

        if (cookbook.getId() == null) {
            throw new ResourceNotFoundException("Cookbook with the given Id does not exist");
        }

        return helperFunctions.convertImageToBase64(cookbook, cookbook.getImageName());
    }

    public JsonNode cookbookByName(String name) throws JsonProcessingException {
        Cookbook cookbook = cookbookRepository.findByName(name).orElse(new Cookbook());

        if (cookbook.getId() == null) {
            throw new ResourceNotFoundException("Cookbook with the given name does not exist");
        }

        return helperFunctions.convertImageToBase64(cookbook, cookbook.getImageName());
    }

    public Entry<Double, Integer> cookbookRatingStats(String id) {
        Cookbook cookbook = cookbookRepository.findById(new ObjectId(id)).orElse(new Cookbook());

        return new SimpleEntry<>(cookbook.getRating(), cookbook.getRatingCount());
    }
}
