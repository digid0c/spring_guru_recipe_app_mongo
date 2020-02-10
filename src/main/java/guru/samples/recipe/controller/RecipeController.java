package guru.samples.recipe.controller;

import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static java.lang.String.format;

@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/details")
    public String getRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/details";
    }

    @GetMapping("/new")
    public String createNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeView());
        return "recipe/recipe-form";
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable Long id, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(id));
        return "recipe/recipe-form";
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute RecipeView recipe) {
        RecipeView savedRecipe = recipeService.save(recipe);
        return format("redirect:/recipe/%d/details", savedRecipe.getId());
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        recipeService.deleteById(id);
        return "redirect:/index";
    }
}
