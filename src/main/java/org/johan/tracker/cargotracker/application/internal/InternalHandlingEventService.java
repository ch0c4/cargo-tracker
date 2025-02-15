package org.johan.tracker.cargotracker.application.internal;

import jakarta.inject.Singleton;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.ApplicationEvents;
import org.johan.tracker.cargotracker.application.HandlingEventService;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.CannotCreateHandlingEventException;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventFactory;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InternalHandlingEventService implements HandlingEventService {

  private final ApplicationEvents applicationEvents;
  private final HandlingEventRepository handlingEventRepository;
  private HandlingEventFactory handlingEventFactory;

  @Override
  public void registerHandlingEvent(
      LocalDateTime completionTime,
      TrackingId trackingId,
      VoyageNumber voyageNumber,
      UnLocode unLocode,
      HandlingEvent.Type type)
      throws CannotCreateHandlingEventException {
    LocalDateTime registrationTime = LocalDateTime.now();

    HandlingEvent event =
        handlingEventFactory.createHandlingEvent(
            registrationTime, completionTime, trackingId, voyageNumber, unLocode, type);

    handlingEventRepository.store(event);

    applicationEvents.cargoWasHandled(event);

    log.info("Registered handling event");
  }
}
