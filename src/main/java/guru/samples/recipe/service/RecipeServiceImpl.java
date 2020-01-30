package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> findAll() {
        log.info("Calling service to obtain recipes");
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false)
                .collect(Collectors.toSet());
    }
}
