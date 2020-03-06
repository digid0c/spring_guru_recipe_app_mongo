package guru.samples.recipe.service;

import guru.samples.recipe.view.UnitOfMeasureView;
import reactor.core.publisher.Flux;

public interface UnitOfMeasureService {

    Flux<UnitOfMeasureView> findAll();
}
