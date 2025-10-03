package dev.arcaninar.cookbook.reposervice;

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
    private BackblazeService backblazeService;

    public Cookbook cookbookById(String id) {
        Cookbook cookbook = cookbookRepository.findById(new ObjectId(id)).orElse(new Cookbook());

        if (cookbook.getId() == null) {
            throw new ResourceNotFoundException("Cookbook with the given Id does not exist");
        }

        cookbook.setImage(backblazeService.getImageBase64(cookbook.getImage()));

        return cookbook;
    }

    public Cookbook cookbookByName(String name) {
        Cookbook cookbook = cookbookRepository.findByName(name).orElse(new Cookbook());

        if (cookbook.getId() == null) {
            throw new ResourceNotFoundException("Cookbook with the given name does not exist");
        }

        cookbook.setImage(backblazeService.getImageBase64(cookbook.getImage()));

        return cookbook;
    }

    public Entry<Double, Integer> cookbookRatingStats(String id) {
        Cookbook cookbook = cookbookRepository.findById(new ObjectId(id)).orElse(new Cookbook());

        return new SimpleEntry<>(cookbook.getRating(), cookbook.getRatingCount());
    }
}
