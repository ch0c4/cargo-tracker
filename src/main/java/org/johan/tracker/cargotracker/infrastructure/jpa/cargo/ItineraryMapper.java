package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;

@Singleton
@RequiredArgsConstructor
public class ItineraryMapper implements Function<ItineraryEntity, Itinerary> {

  private final LegMapper legMapper;

  @Override
  public Itinerary apply(ItineraryEntity entity) {
    return new Itinerary(entity.getLegs().stream().map(legMapper).toList());
  }
}
