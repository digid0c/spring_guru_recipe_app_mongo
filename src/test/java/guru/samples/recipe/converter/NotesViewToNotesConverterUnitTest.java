package guru.samples.recipe.converter;

import guru.samples.recipe.domain.Notes;
import guru.samples.recipe.view.NotesView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class NotesViewToNotesConverterUnitTest {

    private static final Long ID = 1L;
    private static final String RECIPE_NOTES = "Some recipe notes";

    private NotesViewToNotesConverter tested;

    @BeforeEach
    public void setUp() {
        tested = new NotesViewToNotesConverter();
    }

    @Test
    public void shouldConvertNullNotesView() {
        assertThat(tested.convert(null), is(nullValue()));
    }

    @Test
    public void shouldConvertEmptyNotesView() {
        assertThat(tested.convert(new NotesView()), is(notNullValue()));
    }

    @Test
    public void shouldConvertNotesView() {
        NotesView notesView = NotesView.builder()
                .id(ID)
                .recipeNotes(RECIPE_NOTES)
                .build();

        Notes notes = tested.convert(notesView);

        assertThat(notes, is(notNullValue()));
        assertThat(notes.getId(), is(equalTo(ID)));
        assertThat(notes.getRecipeNotes(), is(equalTo(RECIPE_NOTES)));
    }
}
