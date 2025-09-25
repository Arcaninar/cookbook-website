package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.Cookbook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CookbookRepository extends MongoRepository<Cookbook, ObjectId> {

    Optional<Cookbook> findByName(String name);
}
