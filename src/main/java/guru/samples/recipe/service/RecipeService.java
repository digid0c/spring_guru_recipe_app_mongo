package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> findAll();
}
