package org.johan.tracker.cargotracker.infrastructure.messaging.rabbitmq;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.HandlingEventService;
import org.johan.tracker.cargotracker.interfaces.handling.HandlingEventRegistrationAttempt;

@Slf4j
@RabbitListener
@RequiredArgsConstructor
public class HandlingEventRegistrationAttemptConsumer {

  private final HandlingEventService handlingEventService;

  @Queue("${rabbitmq.queues.handlingEventQueue}")
  public void receive(HandlingEventRegistrationAttempt attempt) {
    try {
      handlingEventService.registerHandlingEvent(
          attempt.completionTime(),
          attempt.trackingId(),
          attempt.voyageNumber(),
          attempt.unLocode(),
          attempt.type());
    } catch (Exception e) {
      log.error("Error processing HandlingEventRegistrationAttempt", e);
    }
  }
}
