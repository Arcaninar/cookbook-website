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

        Cookbook cookbook = cookbookService.cookbookById(cookbookId);

        double newRatingValue = (cookbook.getRating() * cookbook.getRatingCount() + ratingValue) / (cookbook.getRatingCount() + 1);

        Update update = new Update()
                .push("ratingList").value(rating)
                .set("rating", newRatingValue)
                .inc("ratingCount", 1);

        mongoTemplate.update(Cookbook.class)
                .matching(Criteria.where("_id").is(cookbookId))
                .apply(update)
                .first();

        return rating;
    }

    public Rating modifyRating(String id, Integer ratingValue, String review, String cookbookId) {
        Rating rating = ratingRepository.findById(new ObjectId(id)).orElse(new Rating());

        if (rating.getId() == null) {
            return new Rating();
        }

        if (!Objects.equals(rating.getRating(), ratingValue)) {
            Cookbook cookbook = cookbookService.cookbookById(cookbookId);

            double newRatingValue = (cookbook.getRating() * cookbook.getRatingCount() - rating.getRating() + ratingValue) / cookbook.getRatingCount();

            mongoTemplate.update(Cookbook.class)
                    .matching(Criteria.where("_id").is(cookbookId))
                    .apply(new Update().set("rating", newRatingValue))
                    .first();
        }

        Update update = new Update()
                .set("rating", ratingValue)
                .set("review", review);

        mongoTemplate.update(Rating.class)
                .matching(Criteria.where("_id").is(id))
                .apply(update)
                .first();

        return rating;
    }

    public Rating deleteRating(String id, String cookbookId) {
        Rating rating = ratingRepository.findById(new ObjectId(id)).orElse(new Rating());

        if (rating.getId() == null) {
            return new Rating();
        }

        Cookbook cookbook = cookbookService.cookbookById(cookbookId);

        double newRatingValue = (cookbook.getRating() * cookbook.getRatingCount() - rating.getRating()) / (cookbook.getRatingCount() - 1);

        Update update = new Update()
                .pull("ratingList", rating)
                .set("rating", newRatingValue)
                .inc("ratingCount", -1);

        mongoTemplate.update(Cookbook.class)
                .matching(Criteria.where("_id").is(cookbookId))
                .apply(update)
                .first();

        ratingRepository.deleteById(new ObjectId(id));

        return rating;
    }

}
