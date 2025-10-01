package dev.arcaninar.cookbook.docobjects;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.arcaninar.cookbook.config.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cookbook")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleCookbook {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String name;
    private String category;
    private Integer cookingTime;
    private List<String> ingredients;
    private List<String> labels;
    private Double rating;
    private String image;
}
