package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.docobjects.SimpleCookbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SimpleCookbookService {
    @Autowired
    private SimpleCookbookRepository simpleCookbookRepository;

    @Autowired
    private BackblazeService backblazeService;

    private List<SimpleCookbook> convertImageToBase64(List<SimpleCookbook> simpleCookbookList) {
        for (SimpleCookbook simpleCookbook: simpleCookbookList) {
            simpleCookbook.setImage(backblazeService.getFile(simpleCookbook.getImage()));
        }
        return simpleCookbookList;
    }

    public List<SimpleCookbook> allSimpleCookbooks() {
        List<SimpleCookbook> simpleCookbookList = simpleCookbookRepository.findAll();
        return convertImageToBase64(simpleCookbookList);
    }

    public List<SimpleCookbook> SimpleCookbooksByKeyword(String keyword) {
        List<SimpleCookbook> simpleCookbookList = simpleCookbookRepository.findByKeyword(keyword);
        return convertImageToBase64(simpleCookbookList);
    }
}