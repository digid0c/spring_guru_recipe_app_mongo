package guru.samples.recipe.controller;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.CategoryRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

    @Autowired
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String index(Model model) {
        Optional<Category> category = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        category.ifPresent(c -> log.info("Category ID is: " + c.getId()));
        unitOfMeasure.ifPresent(uom -> log.info("Unit of measure ID is: " + uom.getId()));

        model.addAttribute("recipes", recipeService.findAll().collectList().block());

        return "index";
    }
}
