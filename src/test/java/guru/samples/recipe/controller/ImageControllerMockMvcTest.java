package guru.samples.recipe.controller;

import guru.samples.recipe.service.ImageService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static reactor.core.publisher.Mono.just;

@ExtendWith(MockitoExtension.class)
public class ImageControllerMockMvcTest {

    private static final String RECIPE_ID = "1";
    private static final String CONTENT = "test";

    @Mock
    private ImageService imageService;

    @Mock
    private RecipeService recipeService;

    @InjectMocks
    private ImageController tested;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tested)
                .setControllerAdvice(new ControllerExceptionHandler())
                .build();
    }

    @Test
    public void shouldGetImageUploadForm() throws Exception {
        RecipeView recipe = RecipeView.builder()
                .id(RECIPE_ID)
                .build();
        when(recipeService.findViewById(RECIPE_ID)).thenReturn(just(recipe));

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/image-upload-form"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findViewById(RECIPE_ID);
    }

    @Test
    public void shouldUploadImage() throws Exception {
        MockMultipartFile image = new MockMultipartFile("image", "test.txt", "text/plain", CONTENT.getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(image))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/details"))
                .andExpect(header().string("Location", "/recipe/1/details"));

        verify(imageService).save(RECIPE_ID, image);
    }

    @Test
    public void shouldRenderImage() throws Exception {
        RecipeView recipe = RecipeView.builder()
                .id(RECIPE_ID)
                .image(createImageBytes())
                .build();
        when(recipeService.findViewById(RECIPE_ID)).thenReturn(just(recipe));

        MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipe-image"))
                .andExpect(status().isOk())
                .andReturn().getResponse();

        verify(recipeService).findViewById(RECIPE_ID);
        byte[] responseBytes = response.getContentAsByteArray();
        assertThat(responseBytes.length, is(equalTo(CONTENT.getBytes().length)));
    }

    private Byte[] createImageBytes() {
        Byte[] imageBytes = new Byte[CONTENT.getBytes().length];
        int i = 0;

        for (byte b : CONTENT.getBytes()) {
            imageBytes[i++] = b;
        }

        return imageBytes;
    }
}
