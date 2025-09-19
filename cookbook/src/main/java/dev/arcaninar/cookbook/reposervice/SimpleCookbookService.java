package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.SimpleCookbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCookbookService {
    @Autowired
    private SimpleCookbookRepository simpleCookbookRepository;

    public List<SimpleCookbook> allSimpleCookbooks() {
        return simpleCookbookRepository.findAll();
    }
}
