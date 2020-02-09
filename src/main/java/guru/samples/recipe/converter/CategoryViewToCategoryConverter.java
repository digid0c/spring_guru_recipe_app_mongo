package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.view.CategoryView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class CategoryViewToCategoryConverter implements Converter<CategoryView, Category> {

    @Synchronized
    @Nullable
    @Override
    public Category convert(@Nullable CategoryView categoryView) {
        return ofNullable(categoryView)
                .map(view -> Category.builder()
                        .id(view.getId())
                        .description(view.getDescription())
                        .build())
                .orElse(null);
    }
}
