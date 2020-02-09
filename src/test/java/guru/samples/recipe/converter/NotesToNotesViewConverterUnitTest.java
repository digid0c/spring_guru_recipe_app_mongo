package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Notes;
import guru.samples.recipe.view.NotesView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotesToNotesViewConverterUnitTest {

    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "Some recipe notes";

    private NotesToNotesViewConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new NotesToNotesViewConverter();
    }

    @Test
    public void shouldConvertNullNotes() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyNotes() {
        assertThat(tested.convert(new Notes()), is(notNullValue()));
    }

    @Test
    public void shouldConvertNotes() {
        Notes notes = Notes.builder()
                .id(ID)
                .recipeNotes(RECIPE_NOTES)
                .build();

        NotesView notesView = tested.convert(notes);

        assertThat(notesView, is(notNullValue()));
        assertThat(notesView.getId(), is(equalTo(ID)));
        assertThat(notesView.getRecipeNotes(), is(equalTo(RECIPE_NOTES)));
    }
}
