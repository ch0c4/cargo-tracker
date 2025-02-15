package org.johan.tracker.cargotracker.domain.service;

import java.util.List;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;
import org.johan.tracker.cargotracker.domain.model.cargo.RouteSpecification;

public interface RoutingService {

  List<Itinerary> fetchRoutesForSpecification(RouteSpecification routeSpecification);
}
