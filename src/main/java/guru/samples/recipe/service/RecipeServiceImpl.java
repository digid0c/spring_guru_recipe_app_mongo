package guru.samples.recipe.service;

import guru.samples.recipe.converter.RecipeToRecipeViewConverter;
import guru.samples.recipe.converter.RecipeViewToRecipeConverter;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.exception.NotFoundException;
import guru.samples.recipe.repository.RecipeRepository;
import guru.samples.recipe.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static java.lang.String.format;
import static java.util.Optional.ofNullable;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeToRecipeViewConverter recipeToRecipeViewConverter;
    private final RecipeViewToRecipeConverter recipeViewToRecipeConverter;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeViewConverter recipeToRecipeViewConverter,
                             RecipeViewToRecipeConverter recipeViewToRecipeConverter) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeViewConverter = recipeToRecipeViewConverter;
        this.recipeViewToRecipeConverter = recipeViewToRecipeConverter;
    }

    @Override
    public Set<Recipe> findAll() {
        log.info("Calling service to obtain recipes");
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public Recipe findById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(format("Requested recipe id=%d is not found!", id)));
    }

    @Override
    @Transactional
    public RecipeView findViewById(Long id) {
        return recipeToRecipeViewConverter.convert(findById(id));
    }

    @Override
    @Transactional
    public RecipeView save(RecipeView recipe) {
        Recipe detachedRecipe = recipeViewToRecipeConverter.convert(recipe);
        Recipe savedRecipe = ofNullable(detachedRecipe)
                .map(recipeRepository::save)
                .orElse(null);
        return recipeToRecipeViewConverter.convert(savedRecipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }
}
