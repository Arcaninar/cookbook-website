package dev.arcaninar.cookbook;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/cookbook/{id}")
    public ResponseEntity<?> getCookbookById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id); // Convert string to ObjectId
            Cookbook cookbook = cookbookService.cookbookById(objectId);
            if (cookbook.getId() == null) {
                return new ResponseEntity<>("Cookbook with the given Id does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cookbook, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id format", HttpStatus.BAD_REQUEST);
        }
    }
}
