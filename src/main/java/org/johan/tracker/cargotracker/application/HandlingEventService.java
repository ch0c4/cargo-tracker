package org.johan.tracker.cargotracker.application;

import jakarta.validation.constraints.NotNull;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.CannotCreateHandlingEventException;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;

import java.time.LocalDateTime;

public interface HandlingEventService {

  void registerHandlingEvent(
      @NotNull LocalDateTime completionTime,
      @NotNull TrackingId trackingId,
      VoyageNumber voyageNumber,
      @NotNull UnLocode unLocode,
      @NotNull HandlingEvent.Type type)
      throws CannotCreateHandlingEventException;
}
