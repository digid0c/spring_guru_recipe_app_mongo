package guru.samples.recipe.service;

import guru.samples.recipe.repository.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeRepository;

    @Autowired
    public ImageServiceImpl(RecipeReactiveRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Mono<Void> save(String recipeId, MultipartFile image) {
        return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] imageBytes;
                    try {
                        imageBytes = new Byte[image.getBytes().length];
                        int i = 0;

                        for (byte b : image.getBytes()) {
                            imageBytes[i++] = b;
                        }
                    } catch (IOException e) {
                        log.error(e.getMessage(), e);
                        throw new RuntimeException(e);
                    }

                    recipe.setImage(imageBytes);
                    recipeRepository.save(recipe).block();
                    return recipe;
                }).then();
    }
}
