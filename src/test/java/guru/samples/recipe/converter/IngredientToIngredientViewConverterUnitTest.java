package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.view.IngredientView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static java.math.BigDecimal.ONE;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class IngredientToIngredientViewConverterUnitTest {

    private static final String ID = "1";
    private static final String UNIT_OF_MEASURE_ID = "2";
    private static final BigDecimal AMOUNT = ONE;
    private static final String DESCRIPTION = "Some description";

    private IngredientToIngredientViewConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new IngredientToIngredientViewConverter(new UnitOfMeasureToUnitOfMeasureViewConverter());
    }

    @Test
    public void shouldConvertNullIngredient() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyIngredient() {
        assertThat(tested.convert(new Ingredient()), is(notNullValue()));
    }

    @Test
    public void shouldConvertIngredient() {
        Ingredient ingredient = Ingredient.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .unitOfMeasure(UnitOfMeasure.builder()
                        .id(UNIT_OF_MEASURE_ID)
                        .build())
                .build();

        IngredientView ingredientView = tested.convert(ingredient);

        assertThat(ingredientView, is(notNullValue()));
        assertThat(ingredientView.getUnitOfMeasure(), is(notNullValue()));
        assertThat(ingredientView.getId(), is(equalTo(ID)));
        assertThat(ingredientView.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredientView.getDescription(), is(equalTo(DESCRIPTION)));
        assertThat(ingredientView.getUnitOfMeasure().getId(), is(equalTo(UNIT_OF_MEASURE_ID)));
    }

    @Test
    public void shouldConvertIngredientWithNullUnitOfMeasure() {
        Ingredient ingredient = Ingredient.builder()
                .id(ID)
                .amount(AMOUNT)
                .description(DESCRIPTION)
                .build();

        IngredientView ingredientView = tested.convert(ingredient);

        assertThat(ingredientView, is(notNullValue()));
        assertThat(ingredientView.getUnitOfMeasure(), is(nullValue()));
        assertThat(ingredientView.getId(), is(equalTo(ID)));
        assertThat(ingredientView.getAmount(), is(equalTo(AMOUNT)));
        assertThat(ingredientView.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
