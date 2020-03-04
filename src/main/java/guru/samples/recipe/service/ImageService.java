package guru.samples.recipe.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void save(String recipeId, MultipartFile image);
}
