package guru.samples.recipe.domain;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipes")
@ToString(exclude = "recipes")
public class Category {

    private String id;
    private String description;
    private Set<Recipe> recipes = new HashSet<>();
}
