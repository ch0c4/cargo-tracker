package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.exceptions.HttpStatusException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LocationDto;

@Controller("/cargo")
public class BookingController {

  private static final long MIN_JOURNEY_DURATION = 1;
  private List<LocationDto> locations;

  private final BookingServiceFacade bookingServiceFacade;

  public BookingController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/locations")
  public List<LocationDto> getLocations(@QueryValue("exclude") String excludeUnLocode) {
    if (locations == null) {
      locations = bookingServiceFacade.listShippingLocations();
    }

    return locations.stream()
        .filter(location -> !location.unLocode().equalsIgnoreCase(excludeUnLocode))
        .toList();
  }

  @Post("/calculate-duration")
  public BookingResponse calculateDuration(@Body BookingRequest request) {
    LocalDate today = LocalDate.now();
    long duration = ChronoUnit.DAYS.between(today, request.arrivalDeadline());
    boolean bookable = duration >= MIN_JOURNEY_DURATION;

    return new BookingResponse(duration, bookable);
  }

  @Post("/booking")
  @Status(HttpStatus.CREATED)
  public String register(@Body BookingRequest request) {
    if (request.originUnLocode().equalsIgnoreCase(request.destinationUnLocode())) {
      throw new HttpStatusException(
          HttpStatus.BAD_REQUEST, "Origin and destination cannot be the same");
    }

    return bookingServiceFacade.bookNewCargo(
        request.originUnLocode(), request.destinationUnLocode(), request.arrivalDeadline());
  }

  @Introspected
  public record BookingRequest(
      String originUnLocode, String destinationUnLocode, LocalDate arrivalDeadline) {}

  public record BookingResponse(long duration, boolean bookable) {}
}
