package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import java.util.function.Function;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LocationDto;

@Singleton
public class LocationDtoAssembler implements Function<Location, LocationDto> {

  @Override
  public LocationDto apply(Location location) {
    return new LocationDto(location.unLocode().code(), location.name());
  }
}
