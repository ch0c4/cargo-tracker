package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Patch;
import io.micronaut.http.annotation.Status;
import java.time.LocalDate;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;

@Controller("/cargo")
public class ChangeArrivalDeadlineController {

  private final BookingServiceFacade bookingServiceFacade;

  public ChangeArrivalDeadlineController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Patch("/{trackingId}/change-deadline")
  @Status(HttpStatus.NO_CONTENT)
  public void changeArrivalDeadline(String trackingId, @Body LocalDate deadline) {
    bookingServiceFacade.changeDeadline(trackingId, deadline);
  }
}
