package org.johan.tracker.cargotracker.interfaces.booking.facade;

import java.time.LocalDate;
import java.util.List;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoStatus;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LocationDto;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.RouteCandidate;

public interface BookingServiceFacade {

  String bookNewCargo(String origin, String destination, LocalDate arrivalDeadline);

  CargoRoute loadCargoForRouting(String trackingId);

  CargoStatus loadCargoForTracking(String trackingId);

  void assignCargoToRoute(String trackingId, RouteCandidate route);

  void changeDestination(String trackingId, String destinationUnLocode);

  void changeDeadline(String trackingId, LocalDate arrivalDeadline);

  List<RouteCandidate> requestPossibleRoutesForCargo(String trackingId);

  List<LocationDto> listShippingLocations();

  List<CargoRoute> listAllCargos();

  List<String> listAllTrackingIds();
}
