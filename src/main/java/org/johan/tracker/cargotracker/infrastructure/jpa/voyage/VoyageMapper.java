package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.voyage.Schedule;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;

@Singleton
@RequiredArgsConstructor
public class VoyageMapper implements Function<VoyageEntity, Voyage> {

  private final ScheduleMapper scheduleMapper;

  @Override
  public Voyage apply(VoyageEntity entity) {
    return new Voyage(
        new VoyageNumber(entity.getVoyageNumber()),
        new Schedule(scheduleMapper.apply(entity.getSchedule()).carrierMovements()));
  }
}
