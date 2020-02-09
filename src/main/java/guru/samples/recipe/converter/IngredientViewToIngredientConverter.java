package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.view.IngredientView;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class IngredientViewToIngredientConverter implements Converter<IngredientView, Ingredient> {

    private final UnitOfMeasureViewToUnitOfMeasureConverter unitOfMeasureViewToUnitOfMeasureConverter;

    @Autowired
    public IngredientViewToIngredientConverter(UnitOfMeasureViewToUnitOfMeasureConverter unitOfMeasureViewToUnitOfMeasureConverter) {
        this.unitOfMeasureViewToUnitOfMeasureConverter = unitOfMeasureViewToUnitOfMeasureConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(@Nullable IngredientView ingredientView) {
        return ofNullable(ingredientView)
                .map(view -> Ingredient.builder()
                        .id(view.getId())
                        .amount(view.getAmount())
                        .description(view.getDescription())
                        .unitOfMeasure(unitOfMeasureViewToUnitOfMeasureConverter.convert(view.getUnitOfMeasure()))
                        .build())
                .orElse(null);
    }
}
