package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;

@Controller("/cargo")
public class CargoDetailsController {

  private final BookingServiceFacade bookingServiceFacade;

  public CargoDetailsController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/{trackingId}")
  public CargoRoute loadCargoForRouting(String trackingId) {
    return bookingServiceFacade.loadCargoForRouting(trackingId);
  }
}
