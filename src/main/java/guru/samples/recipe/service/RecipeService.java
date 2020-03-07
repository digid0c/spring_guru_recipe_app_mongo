package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.view.RecipeView;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RecipeService {

    Flux<Recipe> findAll();

    Mono<Recipe> findById(String id);

    Mono<RecipeView> findViewById(String id);

    Mono<RecipeView> save(RecipeView recipe);

    Mono<Void> deleteById(String id);
}
