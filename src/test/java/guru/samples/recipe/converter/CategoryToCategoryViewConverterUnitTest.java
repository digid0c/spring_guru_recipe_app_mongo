package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.view.CategoryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryToCategoryViewConverterUnitTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    private CategoryToCategoryViewConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new CategoryToCategoryViewConverter();
    }

    @Test
    public void shouldConvertNullCategory() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyCategory() {
        assertThat(tested.convert(new Category()), is(notNullValue()));
    }

    @Test
    public void shouldConvertCategory() {
        Category category = Category.builder()
                .id(ID)
                .description(DESCRIPTION)
                .build();

        CategoryView categoryView = tested.convert(category);

        assertThat(categoryView, is(notNullValue()));
        assertThat(categoryView.getId(), is(equalTo(ID)));
        assertThat(categoryView.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
