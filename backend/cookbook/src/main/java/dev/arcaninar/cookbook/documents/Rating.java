package dev.arcaninar.cookbook.documents;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.arcaninar.cookbook.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "rating")
@Data
@AllArgsConstructor
public class Rating {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private Integer rating;
    private String review;

    public Rating() {
        this.id = null;
        this.rating = 0;
        this.review = "";
    }


    public Rating(Integer rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
