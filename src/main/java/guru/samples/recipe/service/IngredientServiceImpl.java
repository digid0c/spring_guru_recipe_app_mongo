package guru.samples.recipe.service;

import guru.samples.recipe.converter.IngredientToIngredientViewConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.RecipeRepository;
import guru.samples.recipe.view.IngredientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientToIngredientViewConverter ingredientToIngredientViewConverter;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientViewConverter ingredientToIngredientViewConverter) {
        this.recipeRepository = recipeRepository;
        this.ingredientToIngredientViewConverter = ingredientToIngredientViewConverter;
    }

    @Override
    public IngredientView findByIngredientIdAndRecipeId(final Long ingredientId, final Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Requested recipe is not found!"));

        return recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientToIngredientViewConverter::convert)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Requested ingredient is not found!"));
    }
}
