package org.johan.tracker.cargotracker.application;

import jakarta.validation.constraints.NotNull;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;

public interface CargoInspectionService {

  void inspectCargo(@NotNull TrackingId trackingId);
}
