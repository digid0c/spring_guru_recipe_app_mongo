package guru.samples.recipe.service;

import guru.samples.recipe.converter.IngredientToIngredientViewConverter;
import guru.samples.recipe.converter.IngredientViewToIngredientConverter;
import guru.samples.recipe.domain.Ingredient;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.RecipeRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.view.IngredientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.util.Optional.ofNullable;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    private final IngredientToIngredientViewConverter ingredientToIngredientViewConverter;
    private final IngredientViewToIngredientConverter ingredientViewToIngredientConverter;

    @Autowired
    public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository,
                                 IngredientToIngredientViewConverter ingredientToIngredientViewConverter,
                                 IngredientViewToIngredientConverter ingredientViewToIngredientConverter) {
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.ingredientToIngredientViewConverter = ingredientToIngredientViewConverter;
        this.ingredientViewToIngredientConverter = ingredientViewToIngredientConverter;
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

    @Override
    @Transactional
    public IngredientView save(IngredientView ingredientView) {
        Recipe recipe = recipeRepository.findById(ingredientView.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Ingredient must belong to some recipe!"));

        Ingredient ingredient = recipe.getIngredients().stream()
                .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientView.getId()))
                .findFirst()
                .orElse(null);

        if (ingredient == null) {
            ofNullable(ingredientViewToIngredientConverter.convert(ingredientView))
                    .ifPresent(recipe::addIngredient);
        } else {
            ingredient.setDescription(ingredientView.getDescription());
            ingredient.setAmount(ingredientView.getAmount());
            ingredient.setUnitOfMeasure(unitOfMeasureRepository.findById(ingredientView.getUnitOfMeasure().getId())
                    .orElseThrow(() -> new RuntimeException("Unit of measure is not found!")));
        }

        Recipe savedRecipe = recipeRepository.save(recipe);
        return ingredientToIngredientViewConverter.convert(savedRecipe.getIngredients().stream()
                .filter(savedIngredient -> savedIngredient.getId().equals(ingredientView.getId()))
                .findFirst()
                .orElse(null));
    }
}
