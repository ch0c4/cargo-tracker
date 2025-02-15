package org.johan.tracker.cargotracker.infrastructure.jpa.location;

import jakarta.inject.Singleton;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

@Singleton
@RequiredArgsConstructor
public class LocationJpaRepository implements LocationRepository {

  private final LocationCrudRepository locationCrudRepository;
  private final LocationMapper locationMapper;

  @Override
  public Location find(UnLocode unLocode) {
    return locationCrudRepository
        .findByUnLocode(unLocode.code())
        .map(locationMapper)
        .orElseThrow(() -> new IllegalArgumentException("Invalid UnLocode: " + unLocode));
  }

  @Override
  public List<Location> findAll() {
    return locationCrudRepository.findAll().stream().map(locationMapper).toList();
  }
}
