package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.docobjects.Cookbook;
import dev.arcaninar.cookbook.docobjects.Rating;
import dev.arcaninar.cookbook.exceptions.ResourceNotFoundException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Map.Entry;
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

        Entry<Double, Integer> ratingStats = cookbookService.cookbookRatingStats(cookbookId);
        Double currentRatingValue = ratingStats.getKey();
        Integer currentRatingCount = ratingStats.getValue();

        double newRatingValue = (currentRatingValue * currentRatingCount + ratingValue) / (currentRatingCount + 1);

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

    public void modifyRating(String id, Integer ratingValue, String review, String cookbookId) {
        Rating rating = ratingRepository.findById(new ObjectId(id)).orElse(new Rating());

        if (rating.getId() == null) {
            throw new ResourceNotFoundException("Rating with the given Id does not exist");
        }

        if (!Objects.equals(rating.getRating(), ratingValue)) {
            Entry<Double, Integer> ratingStats = cookbookService.cookbookRatingStats(cookbookId);
            Double currentRatingValue = ratingStats.getKey();
            Integer currentRatingCount = ratingStats.getValue();

            double newRatingValue = (currentRatingValue * currentRatingCount - rating.getRating() + ratingValue) / currentRatingCount;

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
    }

    public void deleteRating(String id, String cookbookId) {
        Rating rating = ratingRepository.findById(new ObjectId(id)).orElse(new Rating());

        if (rating.getId() == null) {
            throw new ResourceNotFoundException("Rating with the given Id does not exist");
        }

        Entry<Double, Integer> ratingStats = cookbookService.cookbookRatingStats(cookbookId);
        Double currentRatingValue = ratingStats.getKey();
        Integer currentRatingCount = ratingStats.getValue();

        double newRatingValue = (currentRatingValue * currentRatingCount - rating.getRating()) / (currentRatingCount - 1);

        Update update = new Update()
                .pull("ratingList", rating)
                .set("rating", newRatingValue)
                .inc("ratingCount", -1);

        mongoTemplate.update(Cookbook.class)
                .matching(Criteria.where("_id").is(cookbookId))
                .apply(update)
                .first();

        ratingRepository.deleteById(new ObjectId(id));
    }
}
