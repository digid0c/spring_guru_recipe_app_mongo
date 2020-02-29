package guru.samples.recipe.bootstrap;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.CategoryRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Profile({"dev", "prod"})
@Component
public class MySqlRecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public MySqlRecipeBootstrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (categoryRepository.count() == 0) {
            log.debug("Loading categories...");
            loadCategories();
        }
        if (unitOfMeasureRepository.count() == 0) {
            log.debug("Loading units of measure...");
            loadUnitsOfMeasure();
        }
    }

    private void loadCategories() {
        categoryRepository.saveAll(Stream.of(
                Category.builder().description("American").build(),
                Category.builder().description("Italian").build(),
                Category.builder().description("Mexican").build(),
                Category.builder().description("Fast Food").build()
        ).collect(toList()));
    }

    private void loadUnitsOfMeasure() {
        unitOfMeasureRepository.saveAll(Stream.of(
                UnitOfMeasure.builder().description("Teaspoon").build(),
                UnitOfMeasure.builder().description("Tablespoon").build(),
                UnitOfMeasure.builder().description("Cup").build(),
                UnitOfMeasure.builder().description("Pinch").build(),
                UnitOfMeasure.builder().description("Ounce").build(),
                UnitOfMeasure.builder().description("Each").build(),
                UnitOfMeasure.builder().description("Dash").build(),
                UnitOfMeasure.builder().description("Pint").build()
        ).collect(toList()));
    }
}
