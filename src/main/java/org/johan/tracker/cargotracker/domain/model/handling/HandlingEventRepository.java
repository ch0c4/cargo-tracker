package org.johan.tracker.cargotracker.domain.model.handling;

import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;

public interface HandlingEventRepository {

  void store(HandlingEvent event);

  HandlingHistory lookupHandlingHistoryOfCargo(TrackingId trackingId);
}
