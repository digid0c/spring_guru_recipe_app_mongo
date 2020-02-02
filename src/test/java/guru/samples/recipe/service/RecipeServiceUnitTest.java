package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class RecipeServiceUnitTest {

    private RecipeService tested;

    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void shouldGetAllRecipes() {
        Set<Recipe> recipes = Stream.of(new Recipe(), new Recipe(), new Recipe()).collect(Collectors.toSet());
        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> allRecipes = tested.findAll();
        verify(recipeRepository, times(1)).findAll();
        assertThat(allRecipes.size(), is(equalTo(recipes.size())));
    }
}
