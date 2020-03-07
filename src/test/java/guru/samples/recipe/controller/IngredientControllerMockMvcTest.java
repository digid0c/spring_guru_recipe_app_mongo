package guru.samples.recipe.controller;

import guru.samples.recipe.service.IngredientService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.service.UnitOfMeasureService;
import guru.samples.recipe.view.IngredientView;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static reactor.core.publisher.Flux.empty;
import static reactor.core.publisher.Mono.just;

@Disabled("Migration from spring-webmvc to spring-webflux")
@ExtendWith(MockitoExtension.class)
public class IngredientControllerMockMvcTest {

    private static final String RECIPE_ID = "1";
    private static final String INGREDIENT_ID = "1";

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

    @Mock
    private UnitOfMeasureService unitOfMeasureService;

    @InjectMocks
    private IngredientController tested;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tested).build();
    }

    @Test
    public void shouldListRecipeIngredients() throws Exception {
        RecipeView recipe = RecipeView.builder()
                .id(RECIPE_ID)
                .build();
        when(recipeService.findViewById(RECIPE_ID)).thenReturn(just(recipe));

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findViewById(RECIPE_ID);
    }

    @Test
    public void shouldGetRecipeIngredient() throws Exception {
        IngredientView ingredient = IngredientView.builder()
                .id(INGREDIENT_ID)
                .recipeId(RECIPE_ID)
                .build();
        when(ingredientService.findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID)).thenReturn(just(ingredient));

        mockMvc.perform(get("/recipe/1/ingredient/1/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/details"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService).findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID);
    }

    @Test
    public void shouldGetRecipeIngredientUpdateForm() throws Exception {
        IngredientView ingredient = IngredientView.builder()
                .id(INGREDIENT_ID)
                .build();
        when(ingredientService.findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID)).thenReturn(just(ingredient));
        when(unitOfMeasureService.findAll()).thenReturn(empty());

        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient-form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitsOfMeasure"));

        verify(ingredientService).findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID);
        verify(unitOfMeasureService).findAll();
    }

    @Test
    public void shouldUpdateRecipeIngredient() throws Exception {
        IngredientView ingredient = IngredientView.builder()
                .id(INGREDIENT_ID)
                .recipeId(RECIPE_ID)
                .build();
        when(ingredientService.save(any())).thenReturn(just(ingredient));

        mockMvc.perform(post("/recipe/1/ingredient")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredient/1/details"));

        verify(ingredientService).save(any());
    }

    @Test
    public void shouldGetNewRecipeIngredientForm() throws Exception {
        RecipeView recipe = RecipeView.builder()
                .id(RECIPE_ID)
                .build();
        when(recipeService.findViewById(RECIPE_ID)).thenReturn(just(recipe));
        when(unitOfMeasureService.findAll()).thenReturn(empty());

        mockMvc.perform(get("/recipe/1/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredient-form"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("unitsOfMeasure"));

        verify(recipeService).findViewById(RECIPE_ID);
        verify(unitOfMeasureService).findAll();
    }

    @Test
    public void shouldDeleteRecipeIngredient() throws Exception {
        when(ingredientService.deleteById(RECIPE_ID, INGREDIENT_ID)).thenReturn(Mono.empty());

        mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingredients"));

        verify(ingredientService).deleteById(INGREDIENT_ID, RECIPE_ID);
    }
}
