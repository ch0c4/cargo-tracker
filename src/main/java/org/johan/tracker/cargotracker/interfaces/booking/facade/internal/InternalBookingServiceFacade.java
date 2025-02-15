package org.johan.tracker.cargotracker.interfaces.booking.facade.internal;

import jakarta.inject.Singleton;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.application.BookingService;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventRepository;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageRepository;
import org.johan.tracker.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoStatus;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LocationDto;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.RouteCandidate;
import org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler.CargoRouteDtoAssembler;
import org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler.CargoStatusDtoAssembler;
import org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler.ItineraryCandidateDtoAssembler;
import org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler.LocationDtoAssembler;

@Singleton
@RequiredArgsConstructor
public class InternalBookingServiceFacade implements BookingServiceFacade {

  private final BookingService bookingService;
  private final LocationRepository locationRepository;
  private final CargoRepository cargoRepository;
  private final VoyageRepository voyageRepository;
  private final HandlingEventRepository handlingEventRepository;
  private final CargoRouteDtoAssembler cargoRouteDtoAssembler;
  private final CargoStatusDtoAssembler cargoStatusDtoAssembler;
  private final ItineraryCandidateDtoAssembler itineraryCandidateDtoAssembler;
  private final LocationDtoAssembler locationDtoAssembler;

  @Override
  public String bookNewCargo(String origin, String destination, LocalDate arrivalDeadline) {
    TrackingId trackingId =
        bookingService.bookNewCargo(
            new UnLocode(origin), new UnLocode(destination), arrivalDeadline);
    return trackingId.id();
  }

  @Override
  public CargoRoute loadCargoForRouting(String trackingId) {
    Cargo cargo = cargoRepository.find(new TrackingId(trackingId));
    return cargoRouteDtoAssembler.apply(cargo);
  }

  @Override
  public CargoStatus loadCargoForTracking(String trackingIdValue) {
    TrackingId trackingId = new TrackingId(trackingIdValue);
    Cargo cargo = cargoRepository.find(trackingId);

    if (cargo == null) {
      return null;
    }

    List<HandlingEvent> handlingEvents =
        handlingEventRepository
            .lookupHandlingHistoryOfCargo(trackingId)
            .getDistinctEventsByCompletionTime();

    return cargoStatusDtoAssembler.apply(cargo, handlingEvents);
  }

  @Override
  public void assignCargoToRoute(String trackingIdValue, RouteCandidate route) {
    Itinerary itinerary =
        itineraryCandidateDtoAssembler.apply(route, voyageRepository, locationRepository);
    TrackingId trackingId = new TrackingId(trackingIdValue);

    bookingService.assignCargoRoute(itinerary, trackingId);
  }

  @Override
  public void changeDestination(String trackingId, String destinationUnLocode) {
    bookingService.changeDestination(new TrackingId(trackingId), new UnLocode(destinationUnLocode));
  }

  @Override
  public void changeDeadline(String trackingId, LocalDate arrivalDeadline) {
    bookingService.changeDeadline(new TrackingId(trackingId), arrivalDeadline);
  }

  @Override
  public List<RouteCandidate> requestPossibleRoutesForCargo(String trackingId) {
    List<Itinerary> itineraries =
        bookingService.requestPossibleRoutesForCargo(new TrackingId(trackingId));

    return itineraries.stream().map(itineraryCandidateDtoAssembler::apply).toList();
  }

  @Override
  public List<LocationDto> listShippingLocations() {
    List<Location> allLocations = locationRepository.findAll();
    return allLocations.stream().map(locationDtoAssembler).toList();
  }

  @Override
  public List<CargoRoute> listAllCargos() {
    List<Cargo> cargos = cargoRepository.findAll();

    return cargos.stream().map(cargoRouteDtoAssembler).toList();
  }

  @Override
  public List<String> listAllTrackingIds() {
    List<String> trackingIds = new ArrayList<>();
    cargoRepository.findAll().forEach(cargo -> trackingIds.add(cargo.getTrackingId().id()));

    return trackingIds;
  }
}
