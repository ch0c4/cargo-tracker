package org.johan.tracker.cargotracker.infrastructure.jpa.cargo;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface CargoCrudRepository extends CrudRepository<CargoEntity, Long> {

  Optional<CargoEntity> findByTrackingId(String trackingId);
}
