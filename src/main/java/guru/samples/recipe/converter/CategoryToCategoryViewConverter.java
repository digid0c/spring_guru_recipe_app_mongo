package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.view.CategoryView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class CategoryToCategoryViewConverter implements Converter<Category, CategoryView> {

    @Synchronized
    @Nullable
    @Override
    public CategoryView convert(@Nullable Category category) {
        return ofNullable(category)
                .map(source -> CategoryView.builder()
                        .id(source.getId())
                        .description(source.getDescription())
                        .build())
                .orElse(null);
    }
}
