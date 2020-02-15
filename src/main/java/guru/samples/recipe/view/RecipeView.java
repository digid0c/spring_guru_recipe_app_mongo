package guru.samples.recipe.view;

import guru.samples.recipe.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeView {

    private Long id;
    private String description;
    private Integer preparationTime;
    private Integer cookingTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Set<IngredientView> ingredients = new HashSet<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesView notes;
    private Set<CategoryView> categories = new HashSet<>();
}
