package org.johan.tracker.cargotracker.domain.model.handling;

import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;

public class UnknownVoyageException extends CannotCreateHandlingEventException {

  private final VoyageNumber voyageNumber;

  public UnknownVoyageException(VoyageNumber voyageNumber) {
    this.voyageNumber = voyageNumber;
  }

  @Override
  public String getMessage() {
    return "No voyage with number " + voyageNumber.number() + " exists in the system.";
  }
}
