package guru.samples.recipe.controller;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.service.RecipeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class RecipeControllerMockMvcTest {

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private RecipeController tested;

    @Test
    public void shouldGetRecipeById() throws Exception {
        when(recipeService.findById(anyLong())).thenReturn(new Recipe());

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tested).build();

        mockMvc.perform(get("/recipe/details/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/details"));
    }
}
