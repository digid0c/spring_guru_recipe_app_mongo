package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.RecipeView;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();

    Recipe findById(Long id);

    RecipeView findViewById(Long id);

    RecipeView save(RecipeView recipe);

    void deleteById(Long id);
}
