package guru.samples.recipe.view;

import guru.samples.recipe.domain.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecipeView {

    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(200)
    private Integer preparationTime;

    @Min(1)
    @Max(200)
    private Integer cookingTime;

    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private List<IngredientView> ingredients = new ArrayList<>();
    private Byte[] image;
    private Difficulty difficulty;
    private NotesView notes;
    private List<CategoryView> categories = new ArrayList<>();
}
