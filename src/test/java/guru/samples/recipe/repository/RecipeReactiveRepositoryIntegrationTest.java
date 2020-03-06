package guru.samples.recipe.repository;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class RecipeReactiveRepositoryIntegrationTest {

    private static final String DESCRIPTION = "Some description";

    private final RecipeReactiveRepository tested;

    @Autowired
    public RecipeReactiveRepositoryIntegrationTest(RecipeReactiveRepository tested) {
        this.tested = tested;
    }

    @BeforeEach
    public void setUp() {
        tested.deleteAll().block();
    }

    @Test
    public void shouldSaveRecipe() {
        Recipe recipe = Recipe.builder().description(DESCRIPTION).build();

        Recipe savedRecipe = tested.save(recipe).block();

        assertThat(savedRecipe, is(notNullValue()));
        assertThat(savedRecipe.getId(), is(notNullValue()));
        assertThat(savedRecipe.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(tested.count().block(), is(equalTo(1L)));
    }
}
