package guru.samples.recipe.controller;

import guru.samples.recipe.exception.NotFoundException;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import static java.lang.Long.valueOf;
import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/details")
    public String getRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(valueOf(id)));
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

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception exception) {
        log.error(exception.getMessage(), exception);

        ModelAndView modelAndView = new ModelAndView("error-404");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }
}
