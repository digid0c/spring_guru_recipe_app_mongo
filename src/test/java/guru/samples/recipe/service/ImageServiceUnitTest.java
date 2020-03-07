package guru.samples.recipe.service;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static reactor.core.publisher.Mono.just;

public class ImageServiceUnitTest {

    private static final String RECIPE_ID = "1";

    @Mock
    private RecipeReactiveRepository recipeRepository;

    @Captor
    private ArgumentCaptor<Recipe> recipeArgumentCaptor;

    private ImageService tested;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new ImageServiceImpl(recipeRepository);
    }

    @Test
    public void shouldSaveImage() throws Exception {
        MultipartFile image = new MockMultipartFile("image", "test.txt", "text/plain", "test".getBytes());
        Recipe recipe = Recipe.builder()
                .id(RECIPE_ID)
                .build();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(just(recipe));
        when(recipeRepository.save(any(Recipe.class))).thenReturn(just(recipe));

        tested.save(RECIPE_ID, image).block();

        verify(recipeRepository).save(recipeArgumentCaptor.capture());
        Recipe savedRecipe = recipeArgumentCaptor.getValue();
        assertThat(savedRecipe.getImage().length, is(equalTo(image.getBytes().length)));
    }
}
