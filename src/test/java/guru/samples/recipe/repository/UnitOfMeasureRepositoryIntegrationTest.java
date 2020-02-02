package guru.samples.recipe.repository;

import guru.samples.recipe.domain.UnitOfMeasure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIntegrationTest {

    @Autowired
    private UnitOfMeasureRepository tested;

    @Test
    public void shouldFindByDescription() {
        Optional<UnitOfMeasure> found = tested.findByDescription("Tablespoon");

        assertThat(found.isPresent(), is(true));
        assertThat(found.get().getDescription(), is(equalTo("Tablespoon")));
    }
}