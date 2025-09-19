package dev.arcaninar.cookbook;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "cookbook")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cookbook {
    @Id
    @JsonSerialize(using = ObjectIdSerializer.class)
    private ObjectId id;
    private String name;
    private String category;
    private String cookingTimeUnit;
    private Integer cookingTimeValue;
    private List<String> ingredients;
    private List<String> tools;
    private List<String> steps;
    private List<String> labels;
    private NutritionalFacts nutritionalFacts;
    @DocumentReference
    private List<Rating> ratingList;
}
