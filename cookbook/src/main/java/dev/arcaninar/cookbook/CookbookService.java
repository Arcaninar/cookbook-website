package dev.arcaninar.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CookbookService {
    @Autowired
    private CookbookRepository cookbookRepository;
    public List<Cookbook> allCookbook() {
        return cookbookRepository.findAll();
    }

}
