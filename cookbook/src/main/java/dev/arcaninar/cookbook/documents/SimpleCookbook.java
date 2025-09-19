package dev.arcaninar.cookbook.documents;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.arcaninar.cookbook.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "cookbook")
@Data
@AllArgsConstructor
public class SimpleCookbook {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String name;
    private String category;
    private String cookingTimeUnit;
    private Integer cookingTimeValue;
    private List<String> labels;
    private Double rating;
}
