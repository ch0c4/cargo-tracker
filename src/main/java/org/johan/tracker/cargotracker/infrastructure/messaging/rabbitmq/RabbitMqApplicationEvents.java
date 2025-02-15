package org.johan.tracker.cargotracker.infrastructure.messaging.rabbitmq;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.ApplicationEvents;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.interfaces.handling.HandlingEventRegistrationAttempt;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class RabbitMqApplicationEvents implements ApplicationEvents {

  private final CargoEventsClient client;

  @Override
  public void cargoWasHandled(HandlingEvent event) {
    Cargo cargo = event.cargo();
    log.info("Cargo was handled: {}", cargo);
    client.sendCargoHandled(cargo.getTrackingId().id());
  }

  @Override
  public void cargoWasMisdirected(Cargo cargo) {
    log.info("Cargo was misdirected: {}", cargo);
    client.sendMisdirectedCargo(cargo.getTrackingId().id());
  }

  @Override
  public void cargoHasArrived(Cargo cargo) {
    log.info("Cargo has arrived: {}", cargo);
    client.sendDeliveredCargo(cargo.getTrackingId().id());
  }

  @Override
  public void receivedHandlingEventRegistrationAttempt(HandlingEventRegistrationAttempt attempt) {
    log.info("Received handling event registration attempt: {}", attempt);
    client.sendHandlingEvent(attempt);
  }
}
