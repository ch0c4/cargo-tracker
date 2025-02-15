package org.johan.tracker.pathfinder.api;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.annotation.QueryValue;
import jakarta.validation.constraints.NotNull;

@Introspected
public record ShortestPathQuery(
    @NotNull @QueryValue("origin") String originUnLocode,
    @NotNull @QueryValue("destination") String destinationUnLocode,
    @QueryValue("deadline") String deadline) {}
