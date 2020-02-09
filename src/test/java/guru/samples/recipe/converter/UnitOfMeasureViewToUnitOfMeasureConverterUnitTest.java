package guru.samples.recipe.converter;

import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class UnitOfMeasureViewToUnitOfMeasureConverterUnitTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    private UnitOfMeasureViewToUnitOfMeasureConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new UnitOfMeasureViewToUnitOfMeasureConverter();
    }

    @Test
    public void shouldConvertNullUnitOfMeasureView() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyUnitOfMeasureView() {
        assertThat(tested.convert(new UnitOfMeasureView()), is(notNullValue()));
    }

    @Test
    public void shouldConvertUnitOfMeasureView() {
        UnitOfMeasureView unitOfMeasureView = UnitOfMeasureView.builder()
                .id(ID)
                .description(DESCRIPTION)
                .build();

        UnitOfMeasure unitOfMeasure = tested.convert(unitOfMeasureView);

        assertThat(unitOfMeasure, is(notNullValue()));
        assertThat(unitOfMeasure.getId(), is(equalTo(ID)));
        assertThat(unitOfMeasure.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
