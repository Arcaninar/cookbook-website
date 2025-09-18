package dev.arcaninar.cookbook;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    private CookbookService cookbookService;

    @GetMapping("/cookbooks")
    public ResponseEntity<List<Cookbook>> getAllCookbook() {
        return new ResponseEntity<>(cookbookService.allCookbook(), HttpStatus.OK);
    }
}
