package org.johan.tracker.cargotracker.infrastructure.messaging.rabbitmq;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RabbitListener
public class RejectedRegistrationAttemptsConsumer {

  @Queue("${rabbitmq.queues.rejectedEventQueue}")
  public void receive(String trackingId) {
    log.info("Rejected registration attempt of cargo with trackingId {}", trackingId);
  }
}
