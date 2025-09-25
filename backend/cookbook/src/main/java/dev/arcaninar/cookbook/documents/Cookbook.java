package dev.arcaninar.cookbook.documents;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import dev.arcaninar.cookbook.ObjectIdSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "cookbook")
@Data
@AllArgsConstructor
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
    private Double rating;
    private Integer ratingCount;
    @DocumentReference
    private List<Rating> ratingList;

    public Cookbook() {
        this.id = null;
        this.name = "";
        this.category = "";
        this.cookingTimeUnit = "";
        this.cookingTimeValue = 0;
        this.ingredients = List.of();
        this.tools = List.of();
        this.steps = List.of();
        this.labels = List.of();
        this.nutritionalFacts = new NutritionalFacts();
        this.rating = 0.0;
        this.ratingCount = 0;
        this.ratingList = List.of();
    }
}
