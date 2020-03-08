package guru.samples.recipe.controller;

import guru.samples.recipe.service.IngredientService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.service.UnitOfMeasureService;
import guru.samples.recipe.view.IngredientView;
import guru.samples.recipe.view.RecipeView;
import guru.samples.recipe.view.UnitOfMeasureView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import static java.lang.String.format;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    private WebDataBinder webDataBinder;

    @Autowired
    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @InitBinder("ingredient")
    public void initBinder(WebDataBinder webDataBinder) {
        this.webDataBinder = webDataBinder;
    }

    @ModelAttribute("unitsOfMeasure")
    public Flux<UnitOfMeasureView> populateUnitsOfMeasure() {
        return unitOfMeasureService.findAll();
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String list(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(recipeId));
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/details")
    public String getRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(ingredientId, recipeId));
        return "recipe/ingredient/details";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
        model.addAttribute("ingredient", ingredientService.findByIngredientIdAndRecipeId(ingredientId, recipeId));
        return "recipe/ingredient/ingredient-form";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute("ingredient") IngredientView ingredient) {
        webDataBinder.validate();
        BindingResult bindingResult = webDataBinder.getBindingResult();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(error -> log.debug(error.toString()));
            return "recipe/ingredient/ingredient-form";
        }

        IngredientView savedIngredient = ingredientService.save(ingredient).block();
        return format("redirect:/recipe/%s/ingredient/%s/details", savedIngredient.getRecipeId(), savedIngredient.getId());
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String createRecipeIngredient(@PathVariable String recipeId, Model model) {
        RecipeView recipe = recipeService.findViewById(recipeId).block();
        IngredientView ingredient = IngredientView.builder()
                .recipeId(recipe.getId())
                .unitOfMeasure(new UnitOfMeasureView())
                .build();

        model.addAttribute("ingredient", ingredient);
        return "recipe/ingredient/ingredient-form";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String delete(@PathVariable String recipeId, @PathVariable String ingredientId) {
        ingredientService.deleteById(ingredientId, recipeId);
        return format("redirect:/recipe/%s/ingredients", recipeId);
    }
}
