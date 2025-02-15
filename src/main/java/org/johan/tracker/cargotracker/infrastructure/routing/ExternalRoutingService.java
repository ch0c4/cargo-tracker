package org.johan.tracker.cargotracker.infrastructure.routing;

import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;
import org.johan.tracker.cargotracker.domain.model.cargo.Leg;
import org.johan.tracker.cargotracker.domain.model.cargo.RouteSpecification;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageRepository;
import org.johan.tracker.cargotracker.domain.service.RoutingService;
import org.johan.tracker.pathfinder.api.TransitEdge;
import org.johan.tracker.pathfinder.api.TransitPath;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class ExternalRoutingService implements RoutingService {

  private final PathFinderClient pathFinderClient;
  private final VoyageRepository voyageRepository;
  private final LocationRepository locationRepository;

  @Override
  public List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification) {
    String origin = routeSpecification.origin().unLocode().code();
    String destination = routeSpecification.destination().unLocode().code();

    List<TransitPath> transitPaths = pathFinderClient.findShortestPath(origin, destination, null);

    List<Itinerary> itineraries = new ArrayList<>();

    transitPaths.stream()
        .map(this::toItinerary)
        .forEach(
            itinerary -> {
              if (routeSpecification.isSatisfiedBy(itinerary)) {
                itineraries.add(itinerary);
              } else {
                log.info("Received itinerary that did not satisfy the route specification");
              }
            });

    return itineraries;
  }

  private Itinerary toItinerary(TransitPath transitPath) {
    List<Leg> legs = transitPath.transitEdges().stream().map(this::toLeg).toList();
    return new Itinerary(legs);
  }

  private Leg toLeg(TransitEdge edge) {
    return new Leg(
        voyageRepository.find(new VoyageNumber(edge.voyageNumber())),
        locationRepository.find(new UnLocode(edge.fromUnLocode())),
        locationRepository.find(new UnLocode(edge.toUnLocode())),
        edge.fromDate(),
        edge.toDate());
  }
}
