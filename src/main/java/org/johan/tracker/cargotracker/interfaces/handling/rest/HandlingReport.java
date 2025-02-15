package org.johan.tracker.cargotracker.interfaces.handling.rest;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HandlingReport(
    @NotBlank @Size(min = 15, max = 19) String completionTime,
    @NotBlank @Size(min = 4) String trackingId,
    @NotBlank @Size(min = 4, max = 7) String eventType,
    @NotBlank @Size(min = 5, max = 5) String unLocode,
    @Size(min = 4, max = 5) String voyageNumber) {}
