package org.johan.tracker.cargotracker.interfaces.booking.facade.dto;

import java.time.LocalDate;
import java.util.List;

public record CargoRoute(
    String trackingId,
    LocationDto origin,
    LocationDto finalDestination,
    LocalDate arrivalDeadline,
    boolean misrouted,
    boolean claimed,
    LocationDto lastKnownLocation,
    String transportStatus,
    List<LegDto> legs) {

  public boolean isRouted() {
    return !legs.isEmpty();
  }
}
