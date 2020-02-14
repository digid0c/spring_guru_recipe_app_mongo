package guru.samples.recipe.service;

import guru.samples.recipe.converter.UnitOfMeasureToUnitOfMeasureViewConverter;
import guru.samples.recipe.domain.UnitOfMeasure;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UnitOfMeasureServiceUnitTest {

    private static final Long FIRST_UNIT_OF_MEASURE_ID = 1L;
    private static final Long SECOND_UNIT_OF_MEASURE_ID = 2L;

    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    private UnitOfMeasureService tested;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        tested = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, new UnitOfMeasureToUnitOfMeasureViewConverter());
    }

    @Test
    public void shouldListAllUnitsOfMeasure() {
        Set<UnitOfMeasure> unitsOfMeasure = createUnitsOfMeasure();
        when(unitOfMeasureRepository.findAll()).thenReturn(unitsOfMeasure);

        Set<UnitOfMeasureView> found = tested.findAll();

        assertThat(found.size(), is(equalTo(unitsOfMeasure.size())));
        verify(unitOfMeasureRepository).findAll();
    }

    private Set<UnitOfMeasure> createUnitsOfMeasure() {
        return Stream.of(UnitOfMeasure.builder()
                    .id(FIRST_UNIT_OF_MEASURE_ID)
                    .build(),
                UnitOfMeasure.builder()
                    .id(SECOND_UNIT_OF_MEASURE_ID)
                    .build())
                .collect(toSet());
    }
}
