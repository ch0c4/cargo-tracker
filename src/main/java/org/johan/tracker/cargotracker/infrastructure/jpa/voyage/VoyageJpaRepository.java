package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.inject.Singleton;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageRepository;

@Singleton
@RequiredArgsConstructor
public class VoyageJpaRepository implements VoyageRepository {

  private final VoyageCrudRepository voyageCrudRepository;
  private final VoyageMapper voyageMapper;

  @Override
  public Voyage find(VoyageNumber voyageNumber) {
    return voyageCrudRepository
        .findByVoyageNumber(voyageNumber.number())
        .map(voyageMapper)
        .orElseThrow(
            () -> new IllegalArgumentException("Voyage number " + voyageNumber + " not found"));
  }

  @Override
  public List<Voyage> findAll() {
    return voyageCrudRepository.findAll().stream().map(voyageMapper).toList();
  }
}
