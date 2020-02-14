package guru.samples.recipe.controller;

import guru.samples.recipe.service.IngredientService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.service.UnitOfMeasureService;
import guru.samples.recipe.view.IngredientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static java.lang.String.format;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String list(@PathVariable Long recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/details")
    public String getRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(ingredientId, recipeId));
        return "recipe/ingredient/details";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(ingredientId, recipeId));
        model.addAttribute("unitsOfMeasure", unitOfMeasureService.findAll());
        return "recipe/ingredient/ingredient-form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientView ingredient) {
        IngredientView savedIngredient = ingredientService.save(ingredient);
        return format("redirect:/recipe/%d/ingredient/%d/details", savedIngredient.getRecipeId(), savedIngredient.getId());
    }
}
