package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.IngredientView;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class IngredientToIngredientViewConverter implements Converter<Ingredient, IngredientView> {

    private final UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter;

    @Autowired
    public IngredientToIngredientViewConverter(UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter) {
        this.unitOfMeasureToUnitOfMeasureViewConverter = unitOfMeasureToUnitOfMeasureViewConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientView convert(@Nullable Ingredient ingredient) {
        return ofNullable(ingredient)
                .map(source -> IngredientView.builder()
                        .id(source.getId())
                        .recipeId(ofNullable(source.getRecipe())
                                .map(Recipe::getId)
                                .orElse(null))
                        .amount(source.getAmount())
                        .description(source.getDescription())
                        .unitOfMeasure(unitOfMeasureToUnitOfMeasureViewConverter.convert(source.getUnitOfMeasure()))
                        .build())
                .orElse(null);
    }
}
