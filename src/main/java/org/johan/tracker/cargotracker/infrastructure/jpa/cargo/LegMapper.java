package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Leg;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationMapper;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageMapper;

@Singleton
@RequiredArgsConstructor
public class LegMapper implements Function<LegEntity, Leg> {

  private final VoyageMapper voyageMapper;
  private final LocationMapper locationMapper;

  @Override
  public Leg apply(LegEntity entity) {
    return new Leg(
        voyageMapper.apply(entity.getVoyage()),
        locationMapper.apply(entity.getLoadLocation()),
        locationMapper.apply(entity.getUnLoadLocation()),
        entity.getLoadTime(),
        entity.getUnloadTime());
  }
}
