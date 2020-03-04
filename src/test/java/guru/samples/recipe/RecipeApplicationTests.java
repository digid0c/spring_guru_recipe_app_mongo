package guru.samples.recipe;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled("Temporarily disable integration tests until migration to MongoDB")
class RecipeApplicationTests {

    @Test
    void contextLoads() {
    }

}
