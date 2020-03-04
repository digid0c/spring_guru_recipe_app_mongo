package guru.samples.recipe.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipe")
@ToString(exclude = "recipe")
public class Notes {

    private String id;
    private Recipe recipe;
    private String recipeNotes;
}
