package org.johan.tracker.cargotracker.domain.model.cargo;

import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;

public record HandlingActivity(HandlingEvent.Type type, Location location, Voyage voyage) {

  public boolean isEmpty() {
    return type == null && location == null && voyage == null;
  }
}
