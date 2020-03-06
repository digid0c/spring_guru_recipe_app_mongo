package guru.samples.recipe.repository;

import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
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
public class UnitOfMeasureReactiveRepositoryIntegrationTest {

    private static final String DESCRIPTION = "Some description";

    private final UnitOfMeasureReactiveRepository tested;

    @Autowired
    public UnitOfMeasureReactiveRepositoryIntegrationTest(UnitOfMeasureReactiveRepository tested) {
        this.tested = tested;
    }

    @BeforeEach
    public void setUp() {
        tested.deleteAll().block();
    }

    @Test
    public void shouldSaveUnitOfMeasure() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().description(DESCRIPTION).build();

        UnitOfMeasure savedUnitOfMeasure = tested.save(unitOfMeasure).block();

        assertThat(savedUnitOfMeasure, is(notNullValue()));
        assertThat(savedUnitOfMeasure.getId(), is(notNullValue()));
        assertThat(savedUnitOfMeasure.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(tested.count().block(), is(equalTo(1L)));
    }

    @Test
    public void shouldFindUnitOfMeasureByDescription() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder().description(DESCRIPTION).build();
        tested.save(unitOfMeasure).block();

        UnitOfMeasure foundUnitOfMeasure = tested.findByDescription(DESCRIPTION).block();

        assertThat(foundUnitOfMeasure, is(notNullValue()));
        assertThat(foundUnitOfMeasure.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
