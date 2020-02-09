package guru.samples.recipe.converter;

import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.view.UnitOfMeasureView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class UnitOfMeasureViewToUnitOfMeasureConverter implements Converter<UnitOfMeasureView, UnitOfMeasure> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasure convert(@Nullable UnitOfMeasureView unitOfMeasureView) {
        return ofNullable(unitOfMeasureView)
                .map(view -> UnitOfMeasure.builder()
                        .id(view.getId())
                        .description(view.getDescription())
                        .build())
                .orElse(null);
    }
}
