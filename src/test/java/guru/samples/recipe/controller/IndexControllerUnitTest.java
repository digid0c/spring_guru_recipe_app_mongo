package guru.samples.recipe.controller;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.repository.CategoryRepository;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static reactor.core.publisher.Flux.empty;
import static reactor.core.publisher.Flux.fromIterable;

@Disabled("Migration from spring-webmvc to spring-webflux")
public class IndexControllerUnitTest {

    private IndexController tested;

    @Mock
    private RecipeService recipeService;

    @Mock
    private Model model;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Captor
    private ArgumentCaptor<List<Recipe>> argumentCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new IndexController(categoryRepository, unitOfMeasureRepository, recipeService);
    }

    @Test
    public void shouldGetIndexPageView() {
        Set<Recipe> recipes = Stream.of(new Recipe(), new Recipe(), new Recipe()).collect(Collectors.toSet());
        when(recipeService.findAll()).thenReturn(fromIterable(recipes));

        String viewName = tested.index(model);

        verify(recipeService, times(1)).findAll();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        assertThat("index", is(equalTo(viewName)));
        assertThat(argumentCaptor.getValue().size(), is(equalTo(recipes.size())));
    }

    @Test
    public void testMockMVC() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(tested).build();
        when(recipeService.findAll()).thenReturn(empty());

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }
}