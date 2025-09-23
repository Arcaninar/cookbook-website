package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.Cookbook;
import dev.arcaninar.cookbook.documents.Rating;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CookbookService cookbookService;

    public Rating createRating(Integer ratingValue, String review, String cookbookId) {
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

    public Rating modifyRating(ObjectId objectId, Integer ratingValue, String review, String cookbookId) {
        Rating rating = ratingRepository.findById(objectId).orElse(new Rating());

        if (rating.getId() == null) {
            return new Rating();
        }

        if (!Objects.equals(rating.getRating(), ratingValue)) {
            Cookbook cookbook = cookbookService.cookbookById(new ObjectId(cookbookId));

            double newRating = (cookbook.getRating() * cookbook.getRatingCount() - rating.getRating() + ratingValue) / cookbook.getRatingCount();

            mongoTemplate.update(Cookbook.class)
                    .matching(Criteria.where("_id").is(cookbookId))
                    .apply(new Update().set("rating", newRating))
                    .first();
        }

        Update update = new Update()
                .set("rating", ratingValue)
                .set("review", review);

        mongoTemplate.update(Rating.class)
                .matching(Criteria.where("_id").is(objectId.toString()))
                .apply(update)
                .first();

        return rating;
    }

}
