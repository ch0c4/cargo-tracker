package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;

@Singleton
@RequiredArgsConstructor
public class CargoMapper implements Function<CargoEntity, Cargo> {

  private final RouteSpecificationMapper routeSpecificationMapper;
  private final ItineraryMapper itineraryMapper;

  @Override
  public Cargo apply(CargoEntity entity) {
    return new Cargo(
        new TrackingId(entity.getTrackingId()),
        routeSpecificationMapper.apply(entity.getRouteSpecification()));
  }
}
