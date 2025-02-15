package org.johan.tracker.cargotracker.interfaces.booking.rest;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LocationDto;

@Controller("/cargo")
public class ChangeDestinationController {

  private final BookingServiceFacade bookingServiceFacade;

  public ChangeDestinationController(BookingServiceFacade bookingServiceFacade) {
    this.bookingServiceFacade = bookingServiceFacade;
  }

  @Get("/{trackingId}/potential-destinations")
  public List<LocationDto> getPotentialDestinations(String trackingId) {
    List<LocationDto> locations = bookingServiceFacade.listShippingLocations();
    CargoRoute cargo = bookingServiceFacade.loadCargoForRouting(trackingId);

    return locations.stream()
        .filter(
            location ->
                !location.unLocode().equalsIgnoreCase(cargo.origin().unLocode())
                    && !location.unLocode().equalsIgnoreCase(cargo.finalDestination().unLocode()))
        .toList();
  }

  @Patch("/{trackingId}/change-destination")
  @Status(HttpStatus.NO_CONTENT)
  public void changeDestination(String trackingId, @Body @NotNull String destination) {
    bookingServiceFacade.changeDestination(trackingId, destination);
  }
}
