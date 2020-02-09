package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.RecipeView;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toSet;

@Component
public class RecipeViewToRecipeConverter implements Converter<RecipeView, Recipe> {

    private final CategoryViewToCategoryConverter categoryViewToCategoryConverter;
    private final IngredientViewToIngredientConverter ingredientViewToIngredientConverter;
    private final NotesViewToNotesConverter notesViewToNotesConverter;

    @Autowired
    public RecipeViewToRecipeConverter(CategoryViewToCategoryConverter categoryViewToCategoryConverter,
                                       IngredientViewToIngredientConverter ingredientViewToIngredientConverter,
                                       NotesViewToNotesConverter notesViewToNotesConverter) {
        this.categoryViewToCategoryConverter = categoryViewToCategoryConverter;
        this.ingredientViewToIngredientConverter = ingredientViewToIngredientConverter;
        this.notesViewToNotesConverter = notesViewToNotesConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(@Nullable RecipeView recipeView) {
        return ofNullable(recipeView)
                .map(view -> Recipe.builder()
                        .id(view.getId())
                        .cookingTime(view.getCookingTime())
                        .preparationTime(view.getPreparationTime())
                        .description(view.getDescription())
                        .difficulty(view.getDifficulty())
                        .directions(view.getDirections())
                        .servings(view.getServings())
                        .source(view.getSource())
                        .url(view.getUrl())
                        .notes(notesViewToNotesConverter.convert(view.getNotes()))
                        .categories(view.getCategories().stream()
                                .map(categoryViewToCategoryConverter::convert)
                                .collect(toSet()))
                        .ingredients(view.getIngredients().stream()
                                .map(ingredientViewToIngredientConverter::convert)
                                .collect(toSet()))
                        .build())
                .orElse(null);
    }
}
