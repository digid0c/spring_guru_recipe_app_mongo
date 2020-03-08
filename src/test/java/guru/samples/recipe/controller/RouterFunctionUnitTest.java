package guru.samples.recipe.controller;

import guru.samples.recipe.config.WebConfiguration;
import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.reactive.server.WebTestClient.bindToRouterFunction;
import static reactor.core.publisher.Flux.just;

public class RouterFunctionUnitTest {

    @Mock
    private RecipeService recipeService;

    private WebTestClient webTestClient;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        RouterFunction<ServerResponse> routerFunction = new WebConfiguration().routes(recipeService);
        webTestClient = bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void shouldFindAllRecipes() {
        when(recipeService.findAll()).thenReturn(just(new Recipe(), new Recipe(), new Recipe()));

        webTestClient.get().uri("/api/recipes")
                .accept(APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }
}
