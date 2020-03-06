package guru.samples.recipe.service;

import guru.samples.recipe.converter.IngredientToIngredientViewConverter;
import guru.samples.recipe.converter.IngredientViewToIngredientConverter;
import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import guru.samples.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import guru.samples.recipe.view.IngredientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import static java.util.Optional.ofNullable;
import static reactor.core.publisher.Mono.just;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private final IngredientToIngredientViewConverter ingredientToIngredientViewConverter;
    private final IngredientViewToIngredientConverter ingredientViewToIngredientConverter;

    @Autowired
    public IngredientServiceImpl(RecipeReactiveRepository recipeRepository, UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                 IngredientToIngredientViewConverter ingredientToIngredientViewConverter,
                                 IngredientViewToIngredientConverter ingredientViewToIngredientConverter) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientViewConverter = ingredientToIngredientViewConverter;
        this.ingredientViewToIngredientConverter = ingredientViewToIngredientConverter;
    }

    @Override
    public Mono<IngredientView> findByIngredientIdAndRecipeId(final String ingredientId, final String recipeId) {
        return recipeRepository.findById(recipeId)
                .flatMapIterable(Recipe::getIngredients)
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientView ingredientView = ingredientToIngredientViewConverter.convert(ingredient);
                    ingredientView.setRecipeId(recipeId);
                    return ingredientView;
                });
    }

    @Override
    public Mono<IngredientView> save(IngredientView ingredientView) {
        Recipe recipe = recipeRepository.findById(ingredientView.getRecipeId()).block();

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientView.getId()))
                .findFirst()
                .orElse(null);

        if (ingredient == null) {
            ingredient = ofNullable(ingredientViewToIngredientConverter.convert(ingredientView))
                    .orElseThrow(() -> new RuntimeException("Recipe ingredient is not present!"));
            recipe.addIngredient(ingredient);
        } else {
            ingredient.setDescription(ingredientView.getDescription());
            ingredient.setAmount(ingredientView.getAmount());
            ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientView.getUnitOfMeasure().getId()).block());
        }

        recipe.addIngredient(ingredient);
        recipeRepository.save(recipe).block();

        IngredientView savedIngredient = ingredientToIngredientViewConverter.convert(ingredient);
        ofNullable(savedIngredient).ifPresent(savedIngredientView -> savedIngredientView.setRecipeId(recipe.getId()));
        return just(savedIngredient);
    }

    @Override
    public Mono<Void> deleteById(String ingredientId, String recipeId) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    recipe.getIngredients().removeIf(ingredient -> ingredientId.equals(ingredient.getId()));
                    recipeRepository.save(recipe).block();
                    return recipe;
                }).then();
    }
}
