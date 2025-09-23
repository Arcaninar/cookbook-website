package dev.arcaninar.cookbook.reposervice;

import dev.arcaninar.cookbook.documents.SimpleCookbook;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimpleCookbookRepository extends MongoRepository<SimpleCookbook, ObjectId> {
    @Query(value = "{ $or: [ { 'name': { $regex: ?0, $options: 'i' } }, { 'ingredients': { $regex: ?0, $options: 'i' } } ] }")
    List<SimpleCookbook> findByKeyword(String searchQuery);
}
