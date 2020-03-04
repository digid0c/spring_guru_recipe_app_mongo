package guru.samples.recipe.service;

import guru.samples.recipe.converter.RecipeToRecipeViewConverter;
import guru.samples.recipe.converter.RecipeViewToRecipeConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.exception.NotFoundException;
import guru.samples.recipe.repository.RecipeRepository;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Optional.empty;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class RecipeServiceUnitTest {

    private static final String RECIPE_ID = "1";

    private RecipeService tested;

    @Mock
    private RecipeToRecipeViewConverter recipeToRecipeViewConverter;

    @Mock
    private RecipeViewToRecipeConverter recipeViewToRecipeConverter;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new RecipeServiceImpl(recipeRepository, recipeToRecipeViewConverter, recipeViewToRecipeConverter);
    }

    @Test
    public void shouldGetAllRecipes() {
        Set<Recipe> recipes = Stream.of(new Recipe(), new Recipe(), new Recipe()).collect(Collectors.toSet());
        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> allRecipes = tested.findAll();
        verify(recipeRepository, times(1)).findAll();
        assertThat(allRecipes.size(), is(equalTo(recipes.size())));
    }

    @Test
    public void shouldGetRecipeById() {
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(new Recipe()));

        Recipe recipe = tested.findById(RECIPE_ID);

        assertThat(recipe, is(notNullValue()));
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void shouldGetRecipeViewById() {
        Recipe recipe = Recipe.builder().id(RECIPE_ID).build();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));

        RecipeView recipeView = RecipeView.builder().id(RECIPE_ID).build();
        when(recipeToRecipeViewConverter.convert(recipe)).thenReturn(recipeView);

        RecipeView foundRecipe = tested.findViewById(RECIPE_ID);
        assertThat(foundRecipe, is(notNullValue()));
        verify(recipeRepository).findById(RECIPE_ID);
        verify(recipeRepository, never()).findAll();
    }

    @Test
    public void shouldDeleteRecipeById() {
        tested.deleteById(RECIPE_ID);

        verify(recipeRepository).deleteById(RECIPE_ID);
    }

    @Test
    public void shouldNotFindRecipe() {
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(empty());

        assertThrows(NotFoundException.class, () -> tested.findById(RECIPE_ID), "Requested recipe is not found!");
    }
}
