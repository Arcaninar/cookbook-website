package dev.arcaninar.cookbook.docobjects;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.arcaninar.cookbook.config.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rating")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private Integer rating;
    private String review;

    public Rating(Integer rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
