package guru.samples.recipe.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

import static java.util.UUID.randomUUID;

@Data
@Builder
@AllArgsConstructor
public class Ingredient {

    private String id = randomUUID().toString();
    private String description;
    private BigDecimal amount;
    private UnitOfMeasure unitOfMeasure;

    public Ingredient() {

    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure unitOfMeasure) {
        this.description = description;
        this.amount = amount;
        this.unitOfMeasure = unitOfMeasure;
    }
}
