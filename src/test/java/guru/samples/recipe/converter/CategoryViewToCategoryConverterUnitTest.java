package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.view.CategoryView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CategoryViewToCategoryConverterUnitTest {

    private static final Long ID = 1L;
    private static final String DESCRIPTION = "Some description";

    private CategoryViewToCategoryConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new CategoryViewToCategoryConverter();
    }

    @Test
    public void shouldConvertNullCategoryView() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyCategoryView() {
        assertThat(tested.convert(new CategoryView()), is(notNullValue()));
    }

    @Test
    public void shouldConvertCategoryView() {
        CategoryView categoryView = CategoryView.builder()
                .id(ID)
                .description(DESCRIPTION)
                .build();

        Category category = tested.convert(categoryView);

        assertThat(category, is(notNullValue()));
        assertThat(category.getId(), is(equalTo(ID)));
        assertThat(category.getDescription(), is(equalTo(DESCRIPTION)));
    }
}
