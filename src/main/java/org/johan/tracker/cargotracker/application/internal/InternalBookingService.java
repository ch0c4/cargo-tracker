package org.johan.tracker.cargotracker.application.internal;

import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.BookingService;
import org.johan.tracker.cargotracker.domain.model.cargo.*;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.service.RoutingService;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InternalBookingService implements BookingService {

  private final CargoRepository cargoRepository;
  private final LocationRepository locationRepository;
  private final RoutingService routingService;

  @Override
  public TrackingId bookNewCargo(
      UnLocode originUnLocode, UnLocode destinationUnLocode, LocalDate arrivalDeadline) {
    TrackingId trackingId = cargoRepository.nextTrackingId();
    Location origin = locationRepository.find(originUnLocode);
    Location destination = locationRepository.find(destinationUnLocode);
    RouteSpecification routeSpecification =
        new RouteSpecification(origin, destination, arrivalDeadline);

    Cargo cargo = new Cargo(trackingId, routeSpecification);
    cargoRepository.store(cargo);

    log.info("Booked new cargo with trackingId {}", trackingId.id());

    return trackingId;
  }

  @Override
  public List<Itinerary> requestPossibleRoutesForCargo(TrackingId trackingId) {
    Cargo cargo = cargoRepository.find(trackingId);

    if (cargo == null) {
      return Collections.emptyList();
    }

    return routingService.fetchRoutesForSpecification(cargo.getRouteSpecification());
  }

  @Override
  public void assignCargoRoute(Itinerary itinerary, TrackingId trackingId) {
    Cargo cargo = cargoRepository.find(trackingId);

    cargo.assignToRoute(itinerary);
    cargoRepository.store(cargo);

    log.info("Assigned cargo {} to new route", trackingId);
  }

  @Override
  public void changeDestination(TrackingId trackingId, UnLocode unLocode) {
    Cargo cargo = cargoRepository.find(trackingId);
    Location newDestination = locationRepository.find(unLocode);

    RouteSpecification routeSpecification =
        new RouteSpecification(
            cargo.getOrigin(), newDestination, cargo.getRouteSpecification().arrivalDeadline());
    cargo.specifyNewRoute(routeSpecification);

    cargoRepository.store(cargo);

    log.info(
        "Changed destination for cargo {} to {}", trackingId, routeSpecification.destination());
  }

  @Override
  public void changeDeadline(TrackingId trackingId, LocalDate newDeadline) {
    Cargo cargo = cargoRepository.find(trackingId);

    RouteSpecification routeSpecification =
        new RouteSpecification(
            cargo.getOrigin(), cargo.getRouteSpecification().destination(), newDeadline);
    cargo.specifyNewRoute(routeSpecification);

    cargoRepository.store(cargo);

    log.info("Change deadline for cargo {} to {}", trackingId, newDeadline);
  }
}
