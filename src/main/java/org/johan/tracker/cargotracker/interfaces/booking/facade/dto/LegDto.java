package org.johan.tracker.cargotracker.interfaces.booking.facade.dto;

import java.time.LocalDateTime;

public record LegDto(
    String voyageNumber,
    LocationDto from,
    LocationDto to,
    LocalDateTime loadTime,
    LocalDateTime unloadTime) {}
