package guru.samples.recipe.converter;

import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.view.UnitOfMeasureView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class UnitOfMeasureToUnitOfMeasureViewConverter implements Converter<UnitOfMeasure, UnitOfMeasureView> {

    @Synchronized
    @Nullable
    @Override
    public UnitOfMeasureView convert(@Nullable UnitOfMeasure unitOfMeasure) {
        return ofNullable(unitOfMeasure)
                .map(source -> UnitOfMeasureView.builder()
                        .id(source.getId())
                        .description(source.getDescription())
                        .build())
                .orElse(null);
    }
}
