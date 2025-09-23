package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.Cookbook;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CookbookService {
    @Autowired
    private CookbookRepository cookbookRepository;

    public Cookbook cookbookById(ObjectId id) {
        Optional<Cookbook> optionalCookbook = cookbookRepository.findById(id);
        return optionalCookbook.orElse(new Cookbook());
    }

    public Optional<Cookbook> cookbookByName(String name) {
        return cookbookRepository.findByName(name);
    }
}
