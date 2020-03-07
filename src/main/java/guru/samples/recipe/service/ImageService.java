package guru.samples.recipe.service;

import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {

    Mono<Void> save(String recipeId, MultipartFile image);
}
