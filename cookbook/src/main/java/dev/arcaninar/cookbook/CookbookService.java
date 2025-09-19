package dev.arcaninar.cookbook;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CookbookService {
    @Autowired
    private CookbookRepository cookbookRepository;

    public List<Cookbook> allCookbook() {
        return cookbookRepository.findAll();
    }

    public Cookbook cookbookById(ObjectId id) {
        Optional<Cookbook> optionalCookbook = cookbookRepository.findById(id);
        return optionalCookbook.orElse(new Cookbook());
    }
}
