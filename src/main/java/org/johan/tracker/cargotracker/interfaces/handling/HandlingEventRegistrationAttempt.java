package org.johan.tracker.cargotracker.interfaces.handling;

import java.time.LocalDateTime;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;

public record HandlingEventRegistrationAttempt(
    LocalDateTime registrationTime,
    LocalDateTime completionTime,
    TrackingId trackingId,
    VoyageNumber voyageNumber,
    HandlingEvent.Type type,
    UnLocode unLocode) {}
