package guru.samples.recipe.controller;

import guru.samples.recipe.service.IngredientService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.IngredientView;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class IngredientControllerMockMvcTest {

    private static final Long RECIPE_ID = 1L;
    private static final Long INGREDIENT_ID = 1L;

    @Mock
    private RecipeService recipeService;

    @Mock
    private IngredientService ingredientService;

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
        when(recipeService.findViewById(RECIPE_ID)).thenReturn(recipe);

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
        when(ingredientService.findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID)).thenReturn(ingredient);

        mockMvc.perform(get("/recipe/1/ingredient/1/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/details"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService).findByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID);
    }
}
