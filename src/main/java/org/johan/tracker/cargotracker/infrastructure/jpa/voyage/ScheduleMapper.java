package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.inject.Singleton;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.voyage.Schedule;

@Singleton
@RequiredArgsConstructor
public class ScheduleMapper implements Function<ScheduleEntity, Schedule> {

  private final CarrierMovementMapper carrierMovementMapper;

  @Override
  public Schedule apply(ScheduleEntity entity) {
    return new Schedule(entity.getCarrierMovements().stream().map(carrierMovementMapper).toList());
  }
}
