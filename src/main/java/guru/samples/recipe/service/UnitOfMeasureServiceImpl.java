package guru.samples.recipe.service;

import guru.samples.recipe.converter.UnitOfMeasureToUnitOfMeasureViewConverter;
import guru.samples.recipe.repository.UnitOfMeasureRepository;
import guru.samples.recipe.view.UnitOfMeasureView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toSet;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter;

    @Autowired
    public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository,
                                    UnitOfMeasureToUnitOfMeasureViewConverter unitOfMeasureToUnitOfMeasureViewConverter) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureToUnitOfMeasureViewConverter = unitOfMeasureToUnitOfMeasureViewConverter;
    }

    @Override
    public Set<UnitOfMeasureView> findAll() {
        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureToUnitOfMeasureViewConverter::convert)
                .collect(toSet());
    }
}
