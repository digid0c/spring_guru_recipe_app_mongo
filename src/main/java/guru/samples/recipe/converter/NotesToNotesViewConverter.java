package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Notes;
import guru.samples.recipe.view.NotesView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class NotesToNotesViewConverter implements Converter<Notes, NotesView> {

    @Synchronized
    @Nullable
    @Override
    public NotesView convert(@Nullable Notes notes) {
        return ofNullable(notes)
                .map(source -> NotesView.builder()
                        .id(source.getId())
                        .recipeNotes(source.getRecipeNotes())
                        .build())
                .orElse(null);
    }
}
