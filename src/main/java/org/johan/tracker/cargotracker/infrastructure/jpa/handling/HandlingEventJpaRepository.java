package org.johan.tracker.cargotracker.infrastructure.jpa.handling;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventRepository;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingHistory;
import org.johan.tracker.cargotracker.infrastructure.jpa.cargo.CargoCrudRepository;
import org.johan.tracker.cargotracker.infrastructure.jpa.cargo.CargoEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationCrudRepository;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageCrudRepository;
import org.johan.tracker.cargotracker.infrastructure.jpa.voyage.VoyageEntity;

@Singleton
@RequiredArgsConstructor
public class HandlingEventJpaRepository implements HandlingEventRepository {

  private final HandlingEventCrudRepository handlingEventCrudRepository;
  private final VoyageCrudRepository voyageCrudRepository;
  private final LocationCrudRepository locationCrudRepository;
  private final CargoCrudRepository cargoCrudRepository;
  private final HandlingHistoryMapper handlingHistoryMapper;

  @Override
  public void store(HandlingEvent handlingEvent) {
    VoyageEntity voyage =
        voyageCrudRepository
            .findByVoyageNumber(handlingEvent.voyage().voyageNumber().number())
            .orElseThrow(() -> new IllegalArgumentException("Voyage number not found"));

    LocationEntity location =
        locationCrudRepository
            .findByUnLocode(handlingEvent.location().unLocode().code())
            .orElseThrow(() -> new IllegalArgumentException("Location not found"));

    CargoEntity cargo =
        cargoCrudRepository
            .findByTrackingId(handlingEvent.cargo().getTrackingId().id())
            .orElseThrow(() -> new IllegalArgumentException("Cargo not found"));

    handlingEventCrudRepository.save(
        new HandlingEventEntity(
            null,
            handlingEvent.type(),
            voyage,
            location,
            handlingEvent.completionTime(),
            handlingEvent.registrationTime(),
            cargo));
  }

  @Override
  public HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId) {
    return handlingHistoryMapper.apply(
        handlingEventCrudRepository.findByCargoTrackingId(trackingId.id()));
  }
}
