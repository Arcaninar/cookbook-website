package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.Rating;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends MongoRepository<Rating, ObjectId> {
}
