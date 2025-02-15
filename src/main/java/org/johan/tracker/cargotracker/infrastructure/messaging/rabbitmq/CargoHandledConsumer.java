package org.johan.tracker.cargotracker.infrastructure.messaging.rabbitmq;

import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.CargoInspectionService;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;

@Slf4j
@RabbitListener
@RequiredArgsConstructor
public class CargoHandledConsumer {

  private final CargoInspectionService cargoInspectionService;

  @Queue("${rabbitmq.queues.cargoHandledQueue}")
  public void receive(String trackingId) {
    try {
      log.info("Received CargoHandled event with trackingId: {}", trackingId);
      cargoInspectionService.inspectCargo(new TrackingId(trackingId));
    } catch (Exception e) {
      log.error("Error processing CargoHandledEvent", e);
    }
  }
}
