package guru.samples.recipe.converter;

import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitOfMeasureToUnitOfMeasureViewConverterUnitTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    private UnitOfMeasureToUnitOfMeasureViewConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new UnitOfMeasureToUnitOfMeasureViewConverter();
    }

    @Test
    public void shouldConvertNullUnitOfMeasure() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyUnitOfMeasure() {
        assertThat(tested.convert(new UnitOfMeasure()), is(notNullValue()));
    }

    @Test
    public void shouldConvertUnitOfMeasure() {
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.builder()
                .id(ID)
                .description(DESCRIPTION)
                .build();

        UnitOfMeasureView unitOfMeasureView = tested.convert(unitOfMeasure);

        assertThat(unitOfMeasureView, is(notNullValue()));
        assertThat(unitOfMeasureView.getId(), is(equalTo(ID)));
        assertThat(unitOfMeasureView.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
