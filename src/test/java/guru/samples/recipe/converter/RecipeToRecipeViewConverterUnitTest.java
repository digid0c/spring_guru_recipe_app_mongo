package guru.samples.recipe.converter;

import guru.samples.recipe.domain.*;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static guru.samples.recipe.domain.Difficulty.EASY;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RecipeToRecipeViewConverterUnitTest {

    private static final Long RECIPE_ID = 1L;
    private static final Integer COOKING_TIME = 60;
    private static final Integer PREPARATION_TIME = 30;
    private static final String DESCRIPTION = "Some description";
    private static final String DIRECTIONS = "Some directions";
    private static final Difficulty DIFFICULTY = EASY;
    private static final Integer SERVINGS = 4;
    private static final String SOURCE = "Some source";
    private static final String URL = "Some URL";
    private static final Long FIRST_CATEGORY_ID = 1L;
    private static final Long SECOND_CATEGORY_ID = 2L;
    private static final Integer CATEGORIES_SIZE = 2;
    private static final Long FIRST_INGREDIENT_ID = 1L;
    private static final Long SECOND_INGREDIENT_ID = 2L;
    private static final Integer INGREDIENTS_SIZE = 2;
    private static final Long NOTES_ID = 1L;

    private RecipeToRecipeViewConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new RecipeToRecipeViewConverter(new CategoryToCategoryViewConverter(),
                new IngredientToIngredientViewConverter(new UnitOfMeasureToUnitOfMeasureViewConverter()),
                new NotesToNotesViewConverter());
    }

    @Test
    public void shouldConvertNullRecipe() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyRecipe() {
        assertThat(tested.convert(new Recipe()), is(notNullValue()));
    }

    @Test
    public void shouldConvertRecipe() {
        Recipe recipe = createRecipe();

        RecipeView recipeView = tested.convert(recipe);

        validate(recipeView);
    }

    private Recipe createRecipe() {
        return Recipe.builder()
                .id(RECIPE_ID)
                .cookingTime(COOKING_TIME)
                .preparationTime(PREPARATION_TIME)
                .description(DESCRIPTION)
                .difficulty(DIFFICULTY)
                .directions(DIRECTIONS)
                .servings(SERVINGS)
                .source(SOURCE)
                .url(URL)
                .categories(Stream.of(
                        Category.builder()
                                .id(FIRST_CATEGORY_ID)
                                .build(),
                        Category.builder()
                                .id(SECOND_CATEGORY_ID)
                                .build())
                        .collect(toSet()))
                .ingredients(Stream.of(
                        Ingredient.builder()
                                .id(FIRST_INGREDIENT_ID)
                                .build(),
                        Ingredient.builder()
                                .id(SECOND_INGREDIENT_ID)
                                .build())
                        .collect(toSet()))
                .notes(Notes.builder()
                        .id(NOTES_ID)
                        .build())
                .build();
    }

    private void validate(RecipeView recipeView) {
        assertThat(recipeView, is(notNullValue()));
        assertThat(recipeView.getId(), is(equalTo(RECIPE_ID)));

        assertThat(recipeView.getCookingTime(), is(equalTo(COOKING_TIME)));
        assertThat(recipeView.getPreparationTime(), is(equalTo(PREPARATION_TIME)));

        assertThat(recipeView.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(recipeView.getDifficulty(), is(equalTo(DIFFICULTY)));

        assertThat(recipeView.getDirections(), is(equalTo(DIRECTIONS)));
        assertThat(recipeView.getServings(), is(equalTo(SERVINGS)));

        assertThat(recipeView.getSource(), is(equalTo(SOURCE)));
        assertThat(recipeView.getUrl(), is(equalTo(URL)));

        assertThat(recipeView.getCategories(), is(notNullValue()));
        assertThat(recipeView.getCategories().size(), is(equalTo(CATEGORIES_SIZE)));

        assertThat(recipeView.getIngredients(), is(notNullValue()));
        assertThat(recipeView.getIngredients().size(), is(equalTo(INGREDIENTS_SIZE)));

        assertThat(recipeView.getNotes(), is(notNullValue()));
        assertThat(recipeView.getNotes().getId(), is(equalTo(NOTES_ID)));
    }
}
