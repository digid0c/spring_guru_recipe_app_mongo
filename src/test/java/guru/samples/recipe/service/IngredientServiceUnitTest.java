package guru.samples.recipe.service;

import guru.samples.recipe.converter.IngredientToIngredientViewConverter;
import guru.samples.recipe.converter.IngredientViewToIngredientConverter;
import guru.samples.recipe.converter.UnitOfMeasureToUnitOfMeasureViewConverter;
import guru.samples.recipe.converter.UnitOfMeasureViewToUnitOfMeasureConverter;
import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import guru.samples.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import guru.samples.recipe.view.IngredientView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.just;

public class IngredientServiceUnitTest {

    private static final String RECIPE_ID = "1";
    private static final String FIRST_INGREDIENT_ID = "1";
    private static final String SECOND_INGREDIENT_ID = "2";
    private static final String THIRD_INGREDIENT_ID = "3";

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private IngredientService tested;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new IngredientServiceImpl(recipeRepository, unitOfMeasureRepository,
                new IngredientToIngredientViewConverter(new UnitOfMeasureToUnitOfMeasureViewConverter()),
                new IngredientViewToIngredientConverter(new UnitOfMeasureViewToUnitOfMeasureConverter()));
    }

    @Test
    public void shouldFindIngredientByIdAndRecipeId() {
        Recipe recipe = createRecipe();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(recipe));

        IngredientView ingredient = tested.findByIngredientIdAndRecipeId(SECOND_INGREDIENT_ID, RECIPE_ID).block();

        verify(recipeRepository).findById(RECIPE_ID);
        assertThat(ingredient, is(notNullValue()));
        assertThat(ingredient.getId(), is(equalTo(SECOND_INGREDIENT_ID)));
    }

    @Test
    public void shouldSaveIngredient() {
        IngredientView ingredient = IngredientView.builder()
                .id(THIRD_INGREDIENT_ID)
                .recipeId(RECIPE_ID)
                .build();
        Recipe recipe = createRecipe();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(recipe));
        when(recipeRepository.save(any())).thenReturn(just(recipe));

        IngredientView savedIngredient = tested.save(ingredient).block();

        assertThat(savedIngredient, is(notNullValue()));
        assertThat(savedIngredient.getId(), is(equalTo(THIRD_INGREDIENT_ID)));
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository).save(any());
    }

    @Test
    public void shouldDeleteIngredient() {
        Recipe recipe = createRecipe();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(recipe));

        tested.deleteById(SECOND_INGREDIENT_ID, RECIPE_ID);

        verify(recipeRepository).findById(RECIPE_ID);
    }

    private Recipe createRecipe() {
        return Recipe.builder()
                .id(RECIPE_ID)
                .ingredients(new HashSet<>())
                .build()
                .addIngredient(Ingredient.builder()
                        .id(FIRST_INGREDIENT_ID)
                        .build())
                .addIngredient(Ingredient.builder()
                        .id(SECOND_INGREDIENT_ID)
                        .build());
    }
}
