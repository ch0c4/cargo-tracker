package org.johan.tracker.cargotracker.application;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;
import org.johan.tracker.cargotracker.domain.model.cargo.TrackingId;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;

public interface BookingService {

  TrackingId bookNewCargo(
      @NotNull UnLocode origin,
      @NotNull UnLocode destination,
      @NotNull @Future LocalDate arrivalDeadline);

  List<Itinerary> requestPossibleRoutesForCargo(@NotNull TrackingId trackingId);

  void assignCargoRoute(@NotNull Itinerary itinerary, @NotNull TrackingId trackingId);

  void changeDestination(@NotNull TrackingId trackingId, @NotNull UnLocode unLocode);

  void changeDeadline(@NotNull TrackingId trackingId, @NotNull @Future LocalDate arrivalDeadline);
}
