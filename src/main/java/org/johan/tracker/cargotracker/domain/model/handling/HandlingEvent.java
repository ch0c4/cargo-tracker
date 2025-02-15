package org.johan.tracker.cargotracker.domain.model.handling;

import java.time.LocalDateTime;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;

public record HandlingEvent(
    Type type,
    Voyage voyage,
    Location location,
    LocalDateTime completionTime,
    LocalDateTime registrationTime,
    Cargo cargo) {

  public HandlingEvent {
    if (type().prohibitsVoyage()) {
      throw new IllegalArgumentException("Voyage is not allowed with event type " + type);
    }
  }

  public enum Type {
    LOAD(true),
    UNLOAD(true),
    RECEIVE(false),
    CLAIM(false),
    CUSTOMS(false);

    private final boolean voyageRequired;

    Type(boolean voyageRequired) {
      this.voyageRequired = voyageRequired;
    }

    public boolean requiresVoyage() {
      return voyageRequired;
    }

    public boolean prohibitsVoyage() {
      return !requiresVoyage();
    }
  }
}
