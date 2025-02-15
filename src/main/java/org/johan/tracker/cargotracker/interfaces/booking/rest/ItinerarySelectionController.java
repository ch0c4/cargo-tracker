package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.RouteCandidate;

@Controller("/cargo")
public class ItinerarySelectionController {

  private final BookingServiceFacade bookingServiceFacade;

  public ItinerarySelectionController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/{trackingId}/route-candidates")
  List<RouteCandidate> getRouteCandidates(String trackingId) {
    return bookingServiceFacade.requestPossibleRoutesForCargo(trackingId);
  }

  @Patch("/{trackingId}/assign-itinerary")
  @Status(HttpStatus.NO_CONTENT)
  public void assignItinerary(String trackingId, @Body @NotNull Integer routeIndex) {
    RouteCandidate route =
        bookingServiceFacade.requestPossibleRoutesForCargo(trackingId).get(routeIndex);
    bookingServiceFacade.assignCargoToRoute(trackingId, route);
  }
}
