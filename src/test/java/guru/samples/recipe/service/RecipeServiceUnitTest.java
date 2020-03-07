package guru.samples.recipe.service;

import guru.samples.recipe.converter.RecipeToRecipeViewConverter;
import guru.samples.recipe.converter.RecipeViewToRecipeConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

public class RecipeServiceUnitTest {

    private static final String RECIPE_ID = "1";
    private static final int RECIPES_SIZE = 3;

    private RecipeService tested;

    @Mock
    private RecipeToRecipeViewConverter recipeToRecipeViewConverter;

    @Mock
    private RecipeViewToRecipeConverter recipeViewToRecipeConverter;

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new RecipeServiceImpl(recipeRepository, recipeToRecipeViewConverter, recipeViewToRecipeConverter);
    }

    @Test
    public void shouldGetAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(Flux.just(new Recipe(), new Recipe(), new Recipe()));

        List<Recipe> allRecipes = tested.findAll().collectList().block();
        verify(recipeRepository, times(1)).findAll();
        assertThat(allRecipes, is(notNullValue()));
        assertThat(allRecipes.size(), is(equalTo(RECIPES_SIZE)));
    }

    @Test
    public void shouldGetRecipeById() {
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(new Recipe()));

        Recipe recipe = tested.findById(RECIPE_ID).block();

        assertThat(recipe, is(notNullValue()));
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void shouldGetRecipeViewById() {
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(recipe));

        RecipeView recipeView = RecipeView.builder().id(RECIPE_ID).build();
        when(recipeToRecipeViewConverter.convert(recipe)).thenReturn(recipeView);

        RecipeView foundRecipe = tested.findViewById(RECIPE_ID).block();
        assertThat(foundRecipe, is(notNullValue()));
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void shouldDeleteRecipeById() {
        tested.deleteById(RECIPE_ID);

        verify(recipeRepository).deleteById(RECIPE_ID);
    }
}
