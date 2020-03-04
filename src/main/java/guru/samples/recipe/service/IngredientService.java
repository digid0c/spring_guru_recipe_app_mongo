package guru.samples.recipe.service;

import guru.samples.recipe.view.IngredientView;

public interface IngredientService {

    IngredientView findByIngredientIdAndRecipeId(String ingredientId, String recipeId);

    IngredientView save(IngredientView ingredientView);

    void deleteById(String ingredientId, String recipeId);
}
