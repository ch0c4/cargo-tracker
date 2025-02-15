package org.johan.tracker.cargotracker.infrastructure.routing;

import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;
import org.johan.tracker.pathfinder.api.TransitPath;

import java.util.List;

@Client("/graph-traversal/shortest-path")
public interface PathFinderClient {

  @Get
  List<TransitPath> findShortestPath(
      @QueryValue("origin") String origin,
      @QueryValue("destination") String destination,
      @QueryValue("deadline") String deadline);
}
