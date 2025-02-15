package org.johan.tracker.cargotracker.infrastructure.jpa.location;

import jakarta.inject.Singleton;
import java.util.function.Function;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

@Singleton
public class LocationMapper implements Function<LocationEntity, Location> {

  @Override
  public Location apply(LocationEntity entity) {
    return new Location(new UnLocode(entity.getUnLocode()), entity.getName());
  }
}
