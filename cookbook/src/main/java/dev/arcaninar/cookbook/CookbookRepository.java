package dev.arcaninar.cookbook;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CookbookRepository extends MongoRepository<Cookbook, ObjectId> {
}
