package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.Cookbook;
import dev.arcaninar.cookbook.documents.Rating;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CookbookService cookbookService;

    public Rating createRating(String cookbookId, Integer ratingValue, String review) {
        Rating rating = ratingRepository.insert(new Rating(ratingValue, review));

        Cookbook cookbook = cookbookService.cookbookById(new ObjectId(cookbookId));

        double newRating = (cookbook.getRating() * cookbook.getRatingCount() + ratingValue) / (cookbook.getRatingCount() + 1);

        Update update = new Update()
                .push("ratingList").value(rating)
                .set("rating", newRating)
                .inc("ratingCount", 1);

        mongoTemplate.update(Cookbook.class)
                .matching(Criteria.where("_id").is(cookbookId))
                .apply(update)
                .first();

        return rating;
    }

}
