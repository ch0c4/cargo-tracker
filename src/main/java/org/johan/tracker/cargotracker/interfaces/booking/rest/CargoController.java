package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.QueryValue;
import java.util.ArrayList;
import java.util.List;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;

@Controller("/cargo")
public class CargoController {

  private final BookingServiceFacade bookingServiceFacade;

  public CargoController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/routes")
  public List<CargoRoute> getRoutes(@QueryValue("status") RouteStatusRequest status) {
    return switch (status) {
      case CLAIMED ->
          bookingServiceFacade.listAllCargos().stream().filter(CargoRoute::claimed).toList();
      case NOT_ROUTED ->
          bookingServiceFacade.listAllCargos().stream().filter(route -> !route.isRouted()).toList();
      case UNCLAIMED ->
          bookingServiceFacade.listAllCargos().stream()
              .filter(route -> route.isRouted() && !route.claimed())
              .toList();
      case null -> new ArrayList<>();
    };
  }

  public enum RouteStatusRequest {
    NOT_ROUTED,
    CLAIMED,
    UNCLAIMED
  }
}
