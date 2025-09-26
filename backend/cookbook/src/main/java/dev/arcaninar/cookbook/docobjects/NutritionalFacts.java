package dev.arcaninar.cookbook.docobjects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionalFacts {
    private Integer calories;
    private Integer carbs;
    private Integer fat;
    private Integer protein;
}
