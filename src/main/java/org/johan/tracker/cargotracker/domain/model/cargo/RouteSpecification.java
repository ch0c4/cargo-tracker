package org.johan.tracker.cargotracker.domain.model.cargo;

import java.time.LocalDate;
import org.johan.tracker.cargotracker.domain.model.location.Location;

public record RouteSpecification(Location origin, Location destination, LocalDate arrivalDeadline) {

  public boolean isSatisfiedBy(Itinerary itinerary) {
    return itinerary != null
        && origin().equals(itinerary.getInitialDepartureLocation())
        && destination.equals(itinerary.getFinalArrivalLocation())
        && arrivalDeadline().equals(itinerary.getFinalArrivalDate().toLocalDate());
  }
}
