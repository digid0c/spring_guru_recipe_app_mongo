package guru.samples.recipe.repository;

import guru.samples.recipe.bootstrap.RecipeBootstrap;
import guru.samples.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired
    private UnitOfMeasureRepository tested;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private RecipeRepository recipeRepository;

    @BeforeEach
    public void setUp() {
        recipeRepository.deleteAll();
        categoryRepository.deleteAll();
        tested.deleteAll();

        RecipeBootstrap recipeBootstrap = new RecipeBootstrap(recipeRepository, categoryRepository, tested);
        recipeBootstrap.onApplicationEvent(null);
    }

    @Test
    public void shouldFindByDescription() {
        Optional<UnitOfMeasure> found = tested.findByDescription("Tablespoon");

        assertThat(found.isPresent(), is(true));
        assertThat(found.get().getDescription(), is(equalTo("Tablespoon")));
    }
}