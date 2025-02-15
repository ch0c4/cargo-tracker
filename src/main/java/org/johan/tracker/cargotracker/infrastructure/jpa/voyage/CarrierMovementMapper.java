package org.johan.tracker.cargotracker.infrastructure.jpa.voyage;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.voyage.CarrierMovement;
import org.johan.tracker.cargotracker.infrastructure.jpa.location.LocationMapper;

@Singleton
@RequiredArgsConstructor
public class CarrierMovementMapper implements Function<CarrierMovementEntity, CarrierMovement> {

  private final LocationMapper locationMapper;

  @Override
  public CarrierMovement apply(CarrierMovementEntity entity) {
    return new CarrierMovement(
        locationMapper.apply(entity.getDepartureLocation()),
        locationMapper.apply(entity.getArrivalLocation()),
        entity.getDepartureTime(),
        entity.getArrivalTime());
  }
}
