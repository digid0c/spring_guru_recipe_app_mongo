package guru.samples.recipe.service;

import guru.samples.recipe.view.IngredientView;
import reactor.core.publisher.Mono;

public interface IngredientService {

    Mono<IngredientView> findByIngredientIdAndRecipeId(String ingredientId, String recipeId);

    Mono<IngredientView> save(IngredientView ingredientView);

    Mono<Void> deleteById(String ingredientId, String recipeId);
}
