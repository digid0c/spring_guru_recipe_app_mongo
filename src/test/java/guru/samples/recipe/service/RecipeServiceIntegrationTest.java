package guru.samples.recipe.service;

import guru.samples.recipe.converter.RecipeToRecipeViewConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.RecipeRepository;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RecipeServiceIntegrationTest {

    private static final String NEW_DESCRIPTION = "Some new description";

    @Autowired
    private RecipeService tested;

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private RecipeToRecipeViewConverter recipeToRecipeViewConverter;

    @Test
    @Transactional
    public void shouldUpdateRecipeDescription() {
        Recipe testRecipe = recipeRepository.findAll().iterator().next();
        RecipeView testRecipeView = recipeToRecipeViewConverter.convert(testRecipe);

        testRecipeView.setDescription(NEW_DESCRIPTION);
        RecipeView savedRecipe = tested.save(testRecipeView);

        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(equalTo(testRecipe.getId())));
        assertThat(savedRecipe.getDescription(), is(equalTo(NEW_DESCRIPTION)));
        assertThat(savedRecipe.getCategories().size(), is(equalTo(testRecipe.getCategories().size())));
        assertThat(savedRecipe.getIngredients().size(), is(equalTo(testRecipe.getIngredients().size())));
    }
}
