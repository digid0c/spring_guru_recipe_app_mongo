package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Difficulty;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.CategoryView;
import guru.samples.recipe.view.IngredientView;
import guru.samples.recipe.view.NotesView;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static guru.samples.recipe.domain.Difficulty.EASY;
import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class RecipeViewToRecipeConverterUnitTest {

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

    private RecipeViewToRecipeConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new RecipeViewToRecipeConverter(new CategoryViewToCategoryConverter(),
                new IngredientViewToIngredientConverter(new UnitOfMeasureViewToUnitOfMeasureConverter()),
                new NotesViewToNotesConverter());
    }

    @Test
    public void shouldConvertNullRecipeView() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyRecipeView() {
        assertThat(tested.convert(new RecipeView()), is(notNullValue()));
    }

    @Test
    public void shouldConvertRecipeView() {
        RecipeView recipeView = createRecipeView();

        Recipe recipe = tested.convert(recipeView);

        validate(recipe);
    }

    private RecipeView createRecipeView() {
        return RecipeView.builder()
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
                        CategoryView.builder()
                                .id(FIRST_CATEGORY_ID)
                                .build(),
                        CategoryView.builder()
                                .id(SECOND_CATEGORY_ID)
                                .build())
                        .collect(toSet()))
                .ingredients(Stream.of(
                        IngredientView.builder()
                                .id(FIRST_INGREDIENT_ID)
                                .build(),
                        IngredientView.builder()
                                .id(SECOND_INGREDIENT_ID)
                                .build())
                        .collect(toSet()))
                .notes(NotesView.builder()
                        .id(NOTES_ID)
                        .build())
                .build();
    }

    private void validate(Recipe recipe) {
        assertThat(recipe, is(notNullValue()));
        assertThat(recipe.getId(), is(equalTo(RECIPE_ID)));

        assertThat(recipe.getCookingTime(), is(equalTo(COOKING_TIME)));
        assertThat(recipe.getPreparationTime(), is(equalTo(PREPARATION_TIME)));

        assertThat(recipe.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(recipe.getDifficulty(), is(equalTo(DIFFICULTY)));

        assertThat(recipe.getDirections(), is(equalTo(DIRECTIONS)));
        assertThat(recipe.getServings(), is(equalTo(SERVINGS)));

        assertThat(recipe.getSource(), is(equalTo(SOURCE)));
        assertThat(recipe.getUrl(), is(equalTo(URL)));

        assertThat(recipe.getCategories(), is(notNullValue()));
        assertThat(recipe.getCategories().size(), is(equalTo(CATEGORIES_SIZE)));

        assertThat(recipe.getIngredients(), is(notNullValue()));
        assertThat(recipe.getIngredients().size(), is(equalTo(INGREDIENTS_SIZE)));

        assertThat(recipe.getNotes(), is(notNullValue()));
        assertThat(recipe.getNotes().getId(), is(equalTo(NOTES_ID)));
    }
}
