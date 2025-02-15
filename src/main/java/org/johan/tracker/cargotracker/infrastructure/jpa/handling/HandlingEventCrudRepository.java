package org.johan.tracker.cargotracker.infrastructure.jpa.handling;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.List;

@Repository
public interface HandlingEventCrudRepository extends CrudRepository<HandlingEventEntity, Long> {

  List<HandlingEventEntity> findByCargoTrackingId(String trackingId);
}
