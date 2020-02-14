package guru.samples.recipe.service;

import guru.samples.recipe.view.IngredientView;

public interface IngredientService {

    IngredientView findByIngredientIdAndRecipeId(Long ingredientId, Long recipeId);

    IngredientView save(IngredientView ingredientView);
}
