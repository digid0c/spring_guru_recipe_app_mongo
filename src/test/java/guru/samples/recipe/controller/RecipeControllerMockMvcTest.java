package guru.samples.recipe.controller;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerMockMvcTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController tested;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tested).build();
    }

    @Test
    public void shouldGetRecipeById() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        mockMvc.perform(get("/recipe/1/details"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/details"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void shouldGetNewRecipeForm() throws Exception {
        mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void shouldPostNewRecipeForm() throws Exception {
        RecipeView recipe = RecipeView.builder().id(1L).build();
        when(recipeService.save(any(RecipeView.class))).thenReturn(recipe);

        mockMvc.perform(post("/recipe/save")
                .contentType(APPLICATION_FORM_URLENCODED)
                .param("id", "1")
                .param("description", "some description"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/details"));
    }

    @Test
    public void shouldGetUpdateRecipeForm() throws Exception {
        RecipeView recipe = RecipeView.builder().id(1L).build();
        when(recipeService.findViewById(anyLong())).thenReturn(recipe);

        mockMvc.perform(get("/recipe/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipe-form"))
                .andExpect(model().attributeExists("recipe"));
    }
}
