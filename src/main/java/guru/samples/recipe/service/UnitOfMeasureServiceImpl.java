package guru.samples.recipe.service;

import guru.samples.recipe.converter.UnitOfMeasureToUnitOfMeasureViewConverter;
import guru.samples.recipe.repository.reactive.UnitOfMeasureReactiveRepository;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureReactiveRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureViewConverter = unitOfMeasureToUnitOfMeasureViewConverter;
    }

    @Override
    public Flux<UnitOfMeasureView> findAll() {
        return unitOfMeasureRepository.findAll()
                .map(unitOfMeasureToUnitOfMeasureViewConverter::convert);
    }
}
