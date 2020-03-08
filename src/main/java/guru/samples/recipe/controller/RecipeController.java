package guru.samples.recipe.controller;

import guru.samples.recipe.exception.NotFoundException;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.exceptions.TemplateInputException;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private static final String RECIPE_FORM = "recipe/recipe-form";

    private final RecipeService recipeService;

    private WebDataBinder webDataBinder;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @GetMapping("/{id}/details")
    public String getRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findById(id));
        return "recipe/details";
    }

    @GetMapping("/new")
    public String createNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeView());
        return RECIPE_FORM;
    }

    @GetMapping("/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(id));
        return RECIPE_FORM;
    }

    @PostMapping("/save")
    public String saveOrUpdate(@ModelAttribute("recipe") RecipeView recipe) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

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
        recipeService.deleteById(id);
        return "redirect:/index";
    }

    @ExceptionHandler({NotFoundException.class, TemplateInputException.class})
    @ResponseStatus(NOT_FOUND)
    public String handleNotFoundException(Exception exception, Model model) {
        log.error(exception.getMessage(), exception);
        model.addAttribute("exception", exception);
        return "error-404";
    }
}
