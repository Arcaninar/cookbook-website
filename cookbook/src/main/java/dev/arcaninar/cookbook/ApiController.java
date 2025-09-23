package dev.arcaninar.cookbook;

import dev.arcaninar.cookbook.documents.Cookbook;
import dev.arcaninar.cookbook.documents.Rating;
import dev.arcaninar.cookbook.documents.SimpleCookbook;
import dev.arcaninar.cookbook.reposervice.CookbookService;
import dev.arcaninar.cookbook.reposervice.RatingService;
import dev.arcaninar.cookbook.reposervice.SimpleCookbookService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class ApiController {
    @Autowired
    private SimpleCookbookService simpleCookbookService;
    @Autowired
    private CookbookService cookbookService;
    @Autowired
    private RatingService ratingService;

    @GetMapping("/cookbooks")
    public ResponseEntity<List<SimpleCookbook>> getAllCookbooks() {
        return new ResponseEntity<>(simpleCookbookService.allSimpleCookbooks(), HttpStatus.OK);
    }

    @GetMapping("/cookbooks/search")
    public ResponseEntity<List<SimpleCookbook>> getCookbooksByKeyword(@RequestParam String keyword) {
        return new ResponseEntity<>(simpleCookbookService.SimpleCookbooksByKeyword(keyword), HttpStatus.OK);
    }

    @GetMapping("/cookbook/{id}")
    public ResponseEntity<?> getCookbookById(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            Cookbook cookbook = cookbookService.cookbookById(objectId);
            if (cookbook.getId() == null) {
                return new ResponseEntity<>("Cookbook with the given Id does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(cookbook, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id format", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/cookbook/title/{name}")
    public ResponseEntity<Optional<Cookbook>> getCookbookByName(@PathVariable String name) {
        return new ResponseEntity<>(cookbookService.cookbookByName(name), HttpStatus.OK);
    }

    @PostMapping("/new/rating")
    public ResponseEntity<Rating> createNewRating(@RequestBody Map<String, String> payload) {
        return new ResponseEntity<>(ratingService.createRating(Integer.valueOf(payload.get("ratingValue")), payload.get("review"), payload.get("cookbookId")), HttpStatus.CREATED);
    }

    @PutMapping("/modify/rating/{id}")
    public ResponseEntity<String> modifyExistingRating(@PathVariable String id, @RequestBody Map<String, String> payload) {
        try {
            ObjectId objectId = new ObjectId(id);
            Rating rating = ratingService.modifyRating(objectId, Integer.valueOf(payload.get("ratingValue")), payload.get("review"), payload.get("cookbookId"));
            if (rating.getId() == null) {
                return new ResponseEntity<>("Rating with the given Id does not exist", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id format", HttpStatus.BAD_REQUEST);
        }
    }
}
