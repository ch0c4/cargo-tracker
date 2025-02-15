package org.johan.tracker.cargotracker.domain.model.handling;

public class CannotCreateHandlingEventException extends Exception {

  public CannotCreateHandlingEventException(Exception e) {
    super(e);
  }

  public CannotCreateHandlingEventException() {
    super();
  }
}
