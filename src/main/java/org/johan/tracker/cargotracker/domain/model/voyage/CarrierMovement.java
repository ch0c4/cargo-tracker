package org.johan.tracker.cargotracker.domain.model.voyage;

import java.time.LocalDateTime;
import org.johan.tracker.cargotracker.domain.model.location.Location;

public record CarrierMovement(
    Location departureLocation,
    Location arrivalLocation,
    LocalDateTime departureTime,
    LocalDateTime arrivalTime) {}
