package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import jakarta.inject.Singleton;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationCrudRepository;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationEntity;

@Singleton
@RequiredArgsConstructor
public class CargoJpaRepository implements CargoRepository {

  private final CargoCrudRepository cargoCrudRepository;
  private final LocationCrudRepository locationCrudRepository;
  private final CargoMapper cargoMapper;

  @Override
  public Cargo find(TrackingId trackingId) {
    return cargoCrudRepository.findByTrackingId(trackingId.id()).map(cargoMapper).orElse(null);
  }

  @Override
  public List<Cargo> findAll() {
    return cargoCrudRepository.findAll().stream().map(cargoMapper).toList();
  }

  @Override
  public void store(Cargo cargo) {

    CargoEntity entity =
        cargoCrudRepository.findByTrackingId(cargo.getTrackingId().id()).orElse(null);

    // Create
    if (entity == null) {
      LocationEntity origin =
          locationCrudRepository
              .findByUnLocode(cargo.getOrigin().unLocode().code())
              .orElseThrow(() -> new IllegalStateException("Origin location not found"));

      LocationEntity spec_origin =
          locationCrudRepository
              .findByUnLocode(cargo.getRouteSpecification().origin().unLocode().code())
              .orElseThrow(() -> new IllegalStateException("Route specification origin not found"));
      LocationEntity spec_destination =
          locationCrudRepository
              .findByUnLocode(cargo.getRouteSpecification().destination().unLocode().code())
              .orElseThrow(() -> new IllegalStateException("Route specification origin not found"));

      cargoCrudRepository.save(
          new CargoEntity(
              null,
              cargo.getTrackingId().id(),
              origin,
              new RouteSpecificationEntity(
                  spec_origin, spec_destination, cargo.getRouteSpecification().arrivalDeadline()),
              null,
              null));
      return;
    }

    // Update
    LocationEntity newOrigin =
        locationCrudRepository
            .findByUnLocode(cargo.getOrigin().unLocode().code())
            .orElseThrow(() -> new IllegalStateException("Origin location not found"));

    LocationEntity newSpecOrigin =
        locationCrudRepository
            .findByUnLocode(cargo.getRouteSpecification().origin().unLocode().code())
            .orElseThrow(() -> new IllegalStateException("Route specification origin not found"));

    LocationEntity newSpecDestination =
        locationCrudRepository
            .findByUnLocode(cargo.getRouteSpecification().destination().unLocode().code())
            .orElseThrow(
                () -> new IllegalStateException("Route specification destination not found"));

    entity.setOrigin(newOrigin);
    entity.setRouteSpecification(
        new RouteSpecificationEntity(
            newSpecOrigin, newSpecDestination, cargo.getRouteSpecification().arrivalDeadline()));
    cargoCrudRepository.update(entity);
  }

  @Override
  public TrackingId nextTrackingId() {
    return new TrackingId(UUID.randomUUID().toString());
  }
}
