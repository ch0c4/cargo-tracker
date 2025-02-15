package org.johan.tracker.cargotracker.domain.model.handling;

import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;

public class UnknownCargoException extends CannotCreateHandlingEventException {

  private final TrackingId trackingId;

  public UnknownCargoException(TrackingId trackingId) {
    this.trackingId = trackingId;
  }

  @Override
  public String getMessage() {
    return "No cargo with tracking id " + trackingId.id() + " exists in the system.";
  }
}
