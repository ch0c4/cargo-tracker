package org.johan.tracker.cargotracker.application;

import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.interfaces.handling.HandlingEventRegistrationAttempt;

public interface ApplicationEvents {

  void cargoWasHandled(HandlingEvent event);

  void cargoWasMisdirected(Cargo cargo);

  void cargoHasArrived(Cargo cargo);

  void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt);
}
