package guru.samples.recipe.service;

import guru.samples.recipe.converter.UnitOfMeasureToUnitOfMeasureViewConverter;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceUnitTest {

    private static final String FIRST_UNIT_OF_MEASURE_ID = "1";
    private static final String SECOND_UNIT_OF_MEASURE_ID = "2";
    private static final int UNITS_OF_MEASURE_SIZE = 2;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    private UnitOfMeasureService tested;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureViewConverter());
    }

    @Test
    public void shouldListAllUnitsOfMeasure() {
        Flux<UnitOfMeasure> unitsOfMeasure = createUnitsOfMeasure();
        when(unitOfMeasureRepository.findAll()).thenReturn(unitsOfMeasure);

        List<UnitOfMeasureView> found = tested.findAll().collectList().block();

        assertThat(found, is(notNullValue()));
        assertThat(found.size(), is(equalTo(UNITS_OF_MEASURE_SIZE)));
        verify(unitOfMeasureRepository).findAll();
    }

    private Flux<UnitOfMeasure> createUnitsOfMeasure() {
        return Flux.just(UnitOfMeasure.builder()
                    .id(FIRST_UNIT_OF_MEASURE_ID)
                    .build(),
                UnitOfMeasure.builder()
                    .id(SECOND_UNIT_OF_MEASURE_ID)
                    .build());
    }
}
