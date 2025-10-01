package dev.arcaninar.cookbook.docobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.List;

@Document(collection = "cookbook")
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cookbook extends SimpleCookbook {
    private List<String> tools;
    private List<String> steps;
    private NutritionalFacts nutritionalFacts;
    private Integer ratingCount;
    @DocumentReference
    private List<Rating> ratingList;
}
