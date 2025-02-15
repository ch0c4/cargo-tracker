package org.johan.tracker.cargotracker.infrastructure.jpa.handling;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationMapper;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageMapper;

@Singleton
@RequiredArgsConstructor
public class HandlingEventMapper implements Function<HandlingEventEntity, HandlingEvent> {

  private final VoyageMapper voyageMapper;
  private final LocationMapper locationMapper;

  @Override
  public HandlingEvent apply(HandlingEventEntity entity) {
    return new HandlingEvent(
        entity.getType(),
        voyageMapper.apply(entity.getVoyage()),
        locationMapper.apply(entity.getLocation()),
        entity.getCompletionTime(),
        entity.getRegistrationTime(),
        null);
  }
}
