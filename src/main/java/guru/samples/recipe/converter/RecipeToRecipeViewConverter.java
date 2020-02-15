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
public class RecipeToRecipeViewConverter implements Converter<Recipe, RecipeView> {

    private final CategoryToCategoryViewConverter categoryToCategoryViewConverter;
    private final IngredientToIngredientViewConverter ingredientToIngredientViewConverter;
    private final NotesToNotesViewConverter notesToNotesViewConverter;

    @Autowired
    public RecipeToRecipeViewConverter(CategoryToCategoryViewConverter categoryToCategoryViewConverter,
                                       IngredientToIngredientViewConverter ingredientToIngredientViewConverter,
                                       NotesToNotesViewConverter notesToNotesViewConverter) {
        this.categoryToCategoryViewConverter = categoryToCategoryViewConverter;
        this.ingredientToIngredientViewConverter = ingredientToIngredientViewConverter;
        this.notesToNotesViewConverter = notesToNotesViewConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public RecipeView convert(@Nullable Recipe recipe) {
        return ofNullable(recipe)
                .map(source -> RecipeView.builder()
                        .id(source.getId())
                        .cookingTime(source.getCookingTime())
                        .preparationTime(source.getPreparationTime())
                        .description(source.getDescription())
                        .difficulty(source.getDifficulty())
                        .directions(source.getDirections())
                        .servings(source.getServings())
                        .source(source.getSource())
                        .url(source.getUrl())
                        .image(source.getImage())
                        .notes(notesToNotesViewConverter.convert(source.getNotes()))
                        .categories(source.getCategories().stream()
                                .map(categoryToCategoryViewConverter::convert)
                                .collect(toSet()))
                        .ingredients(source.getIngredients().stream()
                                .map(ingredientToIngredientViewConverter::convert)
                                .collect(toSet()))
                        .build())
                .orElse(null);
    }
}
