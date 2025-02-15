package org.johan.tracker.cargotracker.domain.model.handling;

import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

public class UnknownLocationException extends CannotCreateHandlingEventException {

  private final UnLocode unlocode;

  public UnknownLocationException(UnLocode unlocode) {
    this.unlocode = unlocode;
  }

  @Override
  public String getMessage() {
    return "No location with UN locode " + unlocode.code() + " exists in the system.";
  }
}
