package dev.arcaninar.cookbook.documents;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rating")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rating {
    @Id
    private ObjectId id;
    private Integer rating;
    private String review;

    public Rating(Integer rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
