package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.RouteSpecification;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationMapper;

@Singleton
@RequiredArgsConstructor
public class RouteSpecificationMapper
    implements Function<RouteSpecificationEntity, RouteSpecification> {

  private final LocationMapper locationMapper;

  @Override
  public RouteSpecification apply(RouteSpecificationEntity entity) {
    return new RouteSpecification(
        locationMapper.apply(entity.getOrigin()),
        locationMapper.apply(entity.getDestination()),
        entity.getArrivalDeadline());
  }
}
