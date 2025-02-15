package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.Delivery;
import org.johan.tracker.cargotracker.domain.model.cargo.HandlingActivity;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.CargoStatus;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.TrackingEvents;

@Singleton
@RequiredArgsConstructor
public class CargoStatusDtoAssembler {

  private final TrackingEventsDtoAssembler assembler;

  public CargoStatus apply(Cargo cargo, List<HandlingEvent> handlingEvents) {
    List<TrackingEvents> trackingEvents =
        handlingEvents.stream()
            .map(handlingEvent -> assembler.apply(cargo, handlingEvent))
            .toList();

    return new CargoStatus(
        cargo.getTrackingId().id(),
        cargo.getRouteSpecification().destination().name(),
        getCargoStatusText(cargo),
        cargo.getDelivery().isMisdirected(),
        getEta(cargo),
        getNextExpectedActivity(cargo),
        trackingEvents);
  }

  private String getEta(Cargo cargo) {
    LocalDateTime eta = cargo.getDelivery().getEstimatedTimeOfArrival();

    if (eta == null) {
      return "?";
    } else {
      return eta.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
  }

  private String getCargoStatusText(Cargo cargo) {
    Delivery delivery = cargo.getDelivery();

    return switch (delivery.getTransportStatus()) {
      case IN_PORT -> "In port " + delivery.getLastKnownLocation().name();
      case ONBOARD_CARRIER ->
          "Onboard voyage " + delivery.getCurrentVoyage().voyageNumber().number();
      case CLAIMED -> "Claimed";
      case NOT_RECEIVED -> "Not received";
      case UNKNOWN -> "Unknown";
      case null -> "[Unknown status]";
    };
  }

  private String getNextExpectedActivity(Cargo cargo) {
    HandlingActivity activity = cargo.getDelivery().getNextExpectedActivity();

    if ((activity == null) || (activity.isEmpty())) {
      return "";
    }

    String text = "Next expected activity is to ";
    HandlingEvent.Type type = activity.type();

    if (type.equals(HandlingEvent.Type.LOAD)) {
      return text
          + type.name().toLowerCase()
          + " cargo onto voyage "
          + activity.voyage().voyageNumber().number()
          + " in "
          + activity.location().name();
    } else if (type.equals(HandlingEvent.Type.UNLOAD)) {
      return text
          + type.name().toLowerCase()
          + " cargo off of "
          + activity.voyage().voyageNumber().number()
          + " in "
          + activity.location().name();
    } else {
      return text + type.name().toLowerCase() + " cargo in " + activity.location().name();
    }
  }
}
