package guru.samples.recipe.controller;

import guru.samples.recipe.exception.NotFoundException;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private static final String RECIPE_FORM = "recipe/recipe-form";

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/{id}/details")
    public String getRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id).block());
        return "recipe/details";
    }

    @GetMapping("/new")
    public String createNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeView());
        return RECIPE_FORM;
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(id).block());
        return RECIPE_FORM;
    }

    @PostMapping("/save")
    public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeView recipe, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(error -> log.debug(error.toString()));

            return RECIPE_FORM;
        }

        RecipeView savedRecipe = recipeService.save(recipe).block();
        return format("redirect:/recipe/%s/details", savedRecipe.getId());
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        recipeService.deleteById(id).block();
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
