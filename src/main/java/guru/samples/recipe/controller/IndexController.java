package guru.samples.recipe.controller;

import guru.samples.recipe.domain.Category;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.CategoryRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index", "/index.html"})
    public String index() {
        Optional<Category> category = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Teaspoon");

        category.ifPresent(c -> System.out.println("Category ID is: " + c.getId()));
        unitOfMeasure.ifPresent(uom -> System.out.println("Unit of measure ID is: " + uom.getId()));

        return "index";
    }
}
