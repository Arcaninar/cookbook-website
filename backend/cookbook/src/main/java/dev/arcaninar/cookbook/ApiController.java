package dev.arcaninar.cookbook;

import dev.arcaninar.cookbook.docobjects.Cookbook;
import dev.arcaninar.cookbook.docobjects.SimpleCookbook;
import dev.arcaninar.cookbook.exceptions.ResourceNotFoundException;
import dev.arcaninar.cookbook.reposervice.CookbookService;
import dev.arcaninar.cookbook.reposervice.RatingService;
import dev.arcaninar.cookbook.reposervice.SimpleCookbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
            Cookbook cookbook = cookbookService.cookbookById(id);
            return new ResponseEntity<>(cookbook, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id format", HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/cookbook/title/{name}")
    public ResponseEntity<?> getCookbookByName(@PathVariable String name) {
        try {
            Cookbook cookbook = cookbookService.cookbookByName(name);
            return new ResponseEntity<>(cookbook, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/new/rating")
    public ResponseEntity<?> createNewRating(@RequestBody Map<String, String> payload) {
        try {
            return new ResponseEntity<>(ratingService.createRating(Integer.valueOf(payload.get("ratingValue")), payload.get("review"), payload.get("cookbookId")), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid cookbookId format", HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/modify/rating/{id}")
    public ResponseEntity<String> modifyExistingRating(@PathVariable String id, @RequestBody Map<String, String> payload) {
        try {
            ratingService.modifyRating(id, Integer.valueOf(payload.get("ratingValue")), payload.get("review"), payload.get("cookbookId"));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id or cookbookId format", HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/rating")
    public ResponseEntity<String> deleteExistingRating(@RequestParam String id, @RequestParam String cookbookId) {
        try {
            ratingService.deleteRating(id, cookbookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid Id or cookbookId format", HttpStatus.BAD_REQUEST);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
