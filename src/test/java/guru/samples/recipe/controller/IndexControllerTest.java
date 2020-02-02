package guru.samples.recipe.controller;

import guru.samples.recipe.repository.CategoryRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    private IndexController tested;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void shouldGetIndexPageView() {
        String viewName = tested.index(model);

        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), anySet());
        assertThat("index", is(equalTo(viewName)));
    }
}