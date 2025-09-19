package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.SimpleCookbook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleCookbookRepository extends MongoRepository<SimpleCookbook, ObjectId> {
}
