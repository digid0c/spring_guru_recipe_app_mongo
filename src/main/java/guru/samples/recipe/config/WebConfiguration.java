package guru.samples.recipe.config;

import guru.samples.recipe.domain.Recipe;
import guru.samples.recipe.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebConfiguration {

    @Bean
    public RouterFunction<ServerResponse> routes(RecipeService recipeService) {
        return route(GET("/api/recipes"), serverRequest -> ServerResponse.ok()
                .contentType(APPLICATION_JSON)
                .body(recipeService.findAll(), Recipe.class));
    }
}
