package guru.samples.recipe.service;

import guru.samples.recipe.converter.RecipeToRecipeViewConverter;
import guru.samples.recipe.converter.RecipeViewToRecipeConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import guru.samples.recipe.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeToRecipeViewConverter recipeToRecipeViewConverter;
    private final RecipeViewToRecipeConverter recipeViewToRecipeConverter;

    @Autowired
    public RecipeServiceImpl(RecipeReactiveRepository recipeRepository, RecipeToRecipeViewConverter recipeToRecipeViewConverter,
                             RecipeViewToRecipeConverter recipeViewToRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeViewConverter = recipeToRecipeViewConverter;
        this.recipeViewToRecipeConverter = recipeViewToRecipeConverter;
    }

    @Override
    public Flux<Recipe> findAll() {
        log.info("Calling service to obtain recipes");
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return recipeRepository.findById(id);
    }

    @Override
    public Mono<RecipeView> findViewById(String id) {
        return findById(id).map(recipeToRecipeViewConverter::convert);
    }

    @Override
    public Mono<RecipeView> save(RecipeView recipe) {
        return recipeRepository.save(recipeViewToRecipeConverter.convert(recipe))
                .map(recipeToRecipeViewConverter::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        return recipeRepository.deleteById(id);
    }
}
