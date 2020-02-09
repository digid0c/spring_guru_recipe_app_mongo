package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Notes;
import guru.samples.recipe.view.NotesView;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import static java.util.Optional.ofNullable;

@Component
public class NotesViewToNotesConverter implements Converter<NotesView, Notes> {

    @Synchronized
    @Nullable
    @Override
    public Notes convert(@Nullable NotesView notesView) {
        return ofNullable(notesView)
                .map(view -> Notes.builder()
                        .id(view.getId())
                        .recipeNotes(view.getRecipeNotes())
                        .build())
                .orElse(null);
    }
}
