package org.johan.tracker.cargotracker.application.internal;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.johan.tracker.cargotracker.application.ApplicationEvents;
import org.johan.tracker.cargotracker.application.CargoInspectionService;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.CargoRepository;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEventRepository;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingHistory;

@Slf4j
@Singleton
@RequiredArgsConstructor
public class InternalCargoInspectionService implements CargoInspectionService {

  private final ApplicationEvents applicationEvents;
  private final CargoRepository cargoRepository;
  private final HandlingEventRepository handlingEventRepository;

  @Override
  public void inspectCargo(TrackingId trackingId) {
    Cargo cargo = cargoRepository.find(trackingId);

    if (cargo == null) {
      log.warn("Can't inspect non-existent cargo {}", trackingId);
      return;
    }

    HandlingHistory handlingHistory =
        handlingEventRepository.lookupHandlingHistoryOfCargo(trackingId);

    cargo.deriveDeliveryProgress(handlingHistory);

    if (cargo.getDelivery().isMisdirected()) {
      applicationEvents.cargoWasMisdirected(cargo);
    }

    if (cargo.getDelivery().isUnloadedAtDestination()) {
      applicationEvents.cargoHasArrived(cargo);
    }

    cargoRepository.store(cargo);
  }
}
