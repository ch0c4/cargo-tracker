package org.johan.tracker.cargotracker.domain.model.cargo;

import java.time.LocalDateTime;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;

public record Leg(
    Voyage voyage,
    Location loadLocation,
    Location unloadLocation,
    LocalDateTime loadTime,
    LocalDateTime unloadTime) {}
