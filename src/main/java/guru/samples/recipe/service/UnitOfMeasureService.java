package guru.samples.recipe.service;

import guru.samples.recipe.view.UnitOfMeasureView;

import java.util.Set;

public interface UnitOfMeasureService {

    Set<UnitOfMeasureView> findAll();
}
