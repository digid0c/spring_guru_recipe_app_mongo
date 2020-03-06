package guru.samples.recipe.repository;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.repository.reactive.CategoryReactiveRepository;
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
public class CategoryReactiveRepositoryIntegrationTest {

    private static final String DESCRIPTION = "Some description";

    private final CategoryReactiveRepository tested;

    @Autowired
    public CategoryReactiveRepositoryIntegrationTest(CategoryReactiveRepository tested) {
        this.tested = tested;
    }

    @BeforeEach
    public void setUp() {
        tested.deleteAll().block();
    }

    @Test
    public void shouldSaveCategory() {
        Category category = Category.builder().description(DESCRIPTION).build();

        Category savedCategory = tested.save(category).block();

        assertThat(savedCategory, is(notNullValue()));
        assertThat(savedCategory.getId(), is(notNullValue()));
        assertThat(savedCategory.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(tested.count().block(), is(equalTo(1L)));
    }

    @Test
    public void shouldFindCategoryByDescription() {
        Category category = Category.builder().description(DESCRIPTION).build();
        tested.save(category).block();

        Category foundCategory = tested.findByDescription(DESCRIPTION).block();

        assertThat(foundCategory, is(notNullValue()));
        assertThat(foundCategory.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
