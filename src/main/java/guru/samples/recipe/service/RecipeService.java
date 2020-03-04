package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.RecipeView;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();

    Recipe findById(String id);

    RecipeView findViewById(String id);

    RecipeView save(RecipeView recipe);

    void deleteById(String id);
}
