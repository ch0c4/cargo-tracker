package org.johan.tracker.cargotracker.domain.model.handling;

import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageRepository;

import java.time.LocalDateTime;

public class HandlingEventFactory {

  private final CargoRepository cargoRepository;
  private final VoyageRepository voyageRepository;
  private final LocationRepository locationRepository;

  public HandlingEventFactory(
      CargoRepository cargoRepository,
      VoyageRepository voyageRepository,
      LocationRepository locationRepository) {
    this.cargoRepository = cargoRepository;
    this.voyageRepository = voyageRepository;
    this.locationRepository = locationRepository;
  }

  public HandlingEvent createHandlingEvent(
      LocalDateTime registrationTime,
      LocalDateTime completionTime,
      TrackingId trackingId,
      VoyageNumber voyageNumber,
      UnLocode unLocode,
      HandlingEvent.Type type)
      throws CannotCreateHandlingEventException {
    Cargo cargo = findCargo(trackingId);
    Voyage voyage = findVoyage(voyageNumber);
    Location location = findLocation(unLocode);

    try {
      if (voyage == null) {
        return new HandlingEvent(type, null, location, completionTime, registrationTime, cargo);
      }
      return new HandlingEvent(type, voyage, location, completionTime, registrationTime, cargo);
    } catch (Exception e) {
      throw new CannotCreateHandlingEventException(e);
    }
  }

  private Cargo findCargo(TrackingId trackingId) throws UnknownCargoException {
    Cargo cargo = cargoRepository.find(trackingId);

    if (cargo == null) {
      throw new UnknownCargoException(trackingId);
    }
    return cargo;
  }

  private Voyage findVoyage(VoyageNumber voyageNumber) throws UnknownVoyageException {
    if (voyageNumber == null) {
      return null;
    }

    Voyage voyage = voyageRepository.find(voyageNumber);

    if (voyage == null) {
      throw new UnknownVoyageException(voyageNumber);
    }
    return voyage;
  }

  private Location findLocation(UnLocode unLocode) throws UnknownLocationException {
    Location location = locationRepository.find(unLocode);
    if (location == null) {
      throw new UnknownLocationException(unLocode);
    }
    return location;
  }
}
