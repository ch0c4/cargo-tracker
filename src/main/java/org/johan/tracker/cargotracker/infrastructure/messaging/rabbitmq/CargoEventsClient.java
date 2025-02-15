package org.johan.tracker.cargotracker.infrastructure.messaging.rabbitmq;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import org.johan.tracker.cargotracker.interfaces.handling.HandlingEventRegistrationAttempt;

@RabbitClient
public interface CargoEventsClient {

  @Binding("${rabbitmq.queues.cargoHandledQueue}")
  void sendCargoHandled(String trackingId);

  @Binding("${rabbitmq.queues.misdirectedCargoQueue}")
  void sendMisdirectedCargo(String trackingId);

  @Binding("${rabbitmq.queues.deliveredCargoQueue}")
  void sendDeliveredCargo(String trackingId);

  @Binding("${rabbitmq.queues.handlingEventQueue}")
  void sendHandlingEvent(HandlingEventRegistrationAttempt attempt);
}
