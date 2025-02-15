package org.johan.tracker.cargotracker.interfaces.handling.rest;

import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Post;
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import org.johan.tracker.cargotracker.application.ApplicationEvents;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;
import org.johan.tracker.cargotracker.interfaces.handling.HandlingEventRegistrationAttempt;

@Controller("/handling")
public class HandlingReportController {

  private final ApplicationEvents applicationEvents;

  public HandlingReportController(ApplicationEvents applicationEvents) {
    this.applicationEvents = applicationEvents;
  }

  @Post("/reports")
  public void report(@Valid @Body HandlingReport handlingReport) {
    LocalDateTime completionTime = LocalDateTime.now();
    VoyageNumber voyageNumber = null;

    if (handlingReport.voyageNumber() != null) {
      voyageNumber = new VoyageNumber(handlingReport.voyageNumber());
    }

    HandlingEvent.Type type = HandlingEvent.Type.valueOf(handlingReport.eventType());
    UnLocode unLocode = new UnLocode(handlingReport.unLocode());

    TrackingId trackingId = new TrackingId(handlingReport.trackingId());

    HandlingEventRegistrationAttempt attempt =
        new HandlingEventRegistrationAttempt(
            LocalDateTime.now(), completionTime, trackingId, voyageNumber, type, unLocode);

    applicationEvents.receivedHandlingEventRegistrationAttempt(attempt);
  }
}
