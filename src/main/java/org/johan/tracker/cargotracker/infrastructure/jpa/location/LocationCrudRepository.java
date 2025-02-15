package org.johan.tracker.cargotracker.infrastructure.jpa.location;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;

@Repository
public interface LocationCrudRepository extends CrudRepository<LocationEntity, Long> {

  Optional<LocationEntity> findByUnLocode(String unLocode);
}
