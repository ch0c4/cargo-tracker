package org.johan.tracker.cargotracker.domain.model.cargo;

import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.location.Location;

import java.time.LocalDateTime;
import java.util.List;

public record Itinerary(List<Leg> legs) {

  public boolean isExpected(HandlingEvent event) {
    if (legs.isEmpty()) {
      return true;
    }

    return switch (event.type()) {
      case LOAD ->
          legs.stream()
              .anyMatch(
                  leg ->
                      leg.loadLocation().equals(event.location())
                          && leg.voyage().equals(event.voyage()));
      case RECEIVE -> legs.getFirst().loadLocation().equals(event.location());
      case UNLOAD ->
          legs.stream()
              .anyMatch(
                  leg ->
                      leg.unloadLocation().equals(event.location())
                          && leg.voyage().equals(event.voyage()));
      case CLAIM -> legs.getLast().unloadLocation().equals(event.location());
      case CUSTOMS -> true;
      case null -> throw new RuntimeException("Event case is not handled");
    };
  }

  public Location getInitialDepartureLocation() {
    if (legs.isEmpty()) {
      return Location.UNKNOWN;
    }
    return legs.getFirst().loadLocation();
  }

  public Location getFinalArrivalLocation() {
    if (legs.isEmpty()) {
      return Location.UNKNOWN;
    }
    return legs.getLast().unloadLocation();
  }

  public LocalDateTime getFinalArrivalDate() {

    if (legs.isEmpty()) {
      return LocalDateTime.MAX;
    }

    if (legs.getLast() == null) {
      return LocalDateTime.MAX;
    }

    return legs.getLast().unloadTime();
  }
}
