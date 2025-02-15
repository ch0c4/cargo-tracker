package org.johan.tracker.cargotracker.interfaces.tracking.rest;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.List;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventRepository;

@Controller("/track")
public class TrackController {

  private final CargoRepository cargoRepository;
  private final HandlingEventRepository handlingEventRepository;

  public TrackController(
      CargoRepository cargoRepository, HandlingEventRepository handlingEventRepository) {
    this.cargoRepository = cargoRepository;
    this.handlingEventRepository = handlingEventRepository;
  }

  @Get("/{trackingId}")
  public CargoTrackingViewAdapter trackById(String trackingId) {
    Cargo cargo = cargoRepository.find(new TrackingId(trackingId));

    if (cargo == null) {
      return null;
    }

    List<HandlingEvent> handlingEvents =
        handlingEventRepository
            .lookupHandlingHistoryOfCargo(new TrackingId(trackingId))
            .getDistinctEventsByCompletionTime();

    return CargoTrackingViewAdapter.from(cargo, handlingEvents);
  }
}
