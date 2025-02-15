package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface VoyageCrudRepository extends CrudRepository<VoyageEntity, Long> {

  Optional<VoyageEntity> findByVoyageNumber(String voyageNumber);

}
