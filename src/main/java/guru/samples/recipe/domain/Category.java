package guru.samples.recipe.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "recipes")
@ToString(exclude = "recipes")
@Document
public class Category {

    @Id
    private String id;
    private String description;
    @DBRef
    private Set<Recipe> recipes = new HashSet<>();
}
