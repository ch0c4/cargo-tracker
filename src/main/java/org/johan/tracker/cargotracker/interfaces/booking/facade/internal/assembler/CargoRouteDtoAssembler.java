package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.RoutingStatus;
import org.johan.tracker.cargotracker.domain.model.cargo.TransportStatus;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoRoute;

@Singleton
@RequiredArgsConstructor
public class CargoRouteDtoAssembler implements Function<Cargo, CargoRoute> {

  private final LocationDtoAssembler locationDtoAssembler;
  private final LegDtoAssembler legDtoAssembler;

  @Override
  public CargoRoute apply(Cargo cargo) {
    return new CargoRoute(
        cargo.getTrackingId().id(),
        locationDtoAssembler.apply(cargo.getOrigin()),
        locationDtoAssembler.apply(cargo.getRouteSpecification().destination()),
        cargo.getRouteSpecification().arrivalDeadline(),
        cargo.getDelivery().getRoutingStatus().equals(RoutingStatus.MISROUTED),
        cargo.getDelivery().getTransportStatus().equals(TransportStatus.CLAIMED),
        locationDtoAssembler.apply(cargo.getDelivery().getLastKnownLocation()),
        cargo.getDelivery().getTransportStatus().name(),
        cargo.getItinerary().legs().stream().map(legDtoAssembler).toList());
  }
}
