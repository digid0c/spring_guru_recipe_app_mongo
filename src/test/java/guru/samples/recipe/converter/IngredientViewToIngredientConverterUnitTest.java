package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.view.IngredientView;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class IngredientViewToIngredientConverterUnitTest {

    private static final String ID = "1";
    private static final String UNIT_OF_MEASURE_ID = "2";
    private static final BigDecimal AMOUNT = ONE;
    private static final String DESCRIPTION = "Some description";

    private IngredientViewToIngredientConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new IngredientViewToIngredientConverter(new UnitOfMeasureViewToUnitOfMeasureConverter());
    }

    @Test
    public void shouldConvertNullIngredientView() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyIngredientView() {
        assertThat(tested.convert(new IngredientView()), is(notNullValue()));
    }

    @Test
    public void shouldConvertIngredientView() {
        IngredientView ingredientView = IngredientView.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .unitOfMeasure(UnitOfMeasureView.builder()
                        .id(UNIT_OF_MEASURE_ID)
                        .build())
                .build();

        Ingredient ingredient = tested.convert(ingredientView);

        assertThat(ingredient, is(notNullValue()));
        assertThat(ingredient.getUnitOfMeasure(), is(notNullValue()));
        assertThat(ingredient.getId(), is(equalTo(ID)));
        assertThat(ingredient.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredient.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredient.getUnitOfMeasure().getId(), is(equalTo(UNIT_OF_MEASURE_ID)));
    }

    @Test
    public void shouldConvertIngredientViewWithNullUnitOfMeasureView() {
        IngredientView ingredientView = IngredientView.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        Ingredient ingredient = tested.convert(ingredientView);

        assertThat(ingredient, is(notNullValue()));
        assertThat(ingredient.getUnitOfMeasure(), is(nullValue()));
        assertThat(ingredient.getId(), is(equalTo(ID)));
        assertThat(ingredient.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredient.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
