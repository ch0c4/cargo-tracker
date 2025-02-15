package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.List;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoStatus;

@Controller("/cargo")
public class TrackCargoController {

  private final BookingServiceFacade bookingServiceFacade;

  public TrackCargoController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/tracking")
  public List<String> getTrackingIds() {
    return bookingServiceFacade.listAllTrackingIds();
  }

  @Get("/{trackingId}/status")
  public CargoStatus getStatus(String trackingId) {
    return bookingServiceFacade.loadCargoForTracking(trackingId);
  }
}
