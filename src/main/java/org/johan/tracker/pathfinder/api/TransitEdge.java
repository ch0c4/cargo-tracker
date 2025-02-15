package org.johan.tracker.pathfinder.api;

import java.time.LocalDateTime;

public record TransitEdge(
    String voyageNumber,
    String fromUnLocode,
    String toUnLocode,
    LocalDateTime fromDate,
    LocalDateTime toDate) {}
