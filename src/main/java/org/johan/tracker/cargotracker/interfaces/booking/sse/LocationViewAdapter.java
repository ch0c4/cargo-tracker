package org.johan.tracker.cargotracker.interfaces.booking.sse;

import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.interfaces.Coordinates;
import org.johan.tracker.cargotracker.interfaces.CoordinatesFactory;

public record LocationViewAdapter(String unLocode, String name, Coordinates coordinates) {

  public static LocationViewAdapter from(Location location) {
    return new LocationViewAdapter(
        location.unLocode().code(), location.name(), CoordinatesFactory.find(location));
  }
}
