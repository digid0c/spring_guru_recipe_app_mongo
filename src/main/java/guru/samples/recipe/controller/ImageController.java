package guru.samples.recipe.controller;

import guru.samples.recipe.service.ImageService;
import guru.samples.recipe.service.RecipeService;
import guru.samples.recipe.view.RecipeView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;
import static org.apache.tomcat.util.http.fileupload.IOUtils.copy;

@Controller
public class ImageController {

    private final ImageService imageService;
    private final RecipeService recipeService;

    @Autowired
    public ImageController(ImageService imageService, RecipeService recipeService) {
        this.imageService = imageService;
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String getUploadForm(@PathVariable String recipeId, Model model) {
        model.addAttribute("recipe", recipeService.findViewById(recipeId).block());
        return "recipe/image-upload-form";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String upload(@PathVariable String recipeId, @RequestParam(name = "image") MultipartFile image) {
        imageService.save(recipeId, image);
        return format("redirect:/recipe/%s/details", recipeId);
    }

    @GetMapping("/recipe/{recipeId}/recipe-image")
    public void render(@PathVariable String recipeId, HttpServletResponse response) throws IOException {
        RecipeView recipe = recipeService.findViewById(recipeId).block();

        if (recipe != null && recipe.getImage() != null) {
            byte[] imageBytes = new byte[recipe.getImage().length];
            int i = 0;

            for (Byte b : recipe.getImage()) {
                imageBytes[i++] = b;
            }

            response.setContentType("image/jpeg");
            InputStream is = new ByteArrayInputStream(imageBytes);
            copy(is, response.getOutputStream());
        }
    }
}
