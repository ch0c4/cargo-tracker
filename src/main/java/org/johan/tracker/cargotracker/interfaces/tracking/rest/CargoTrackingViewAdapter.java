package org.johan.tracker.cargotracker.interfaces.tracking.rest;

import java.time.format.DateTimeFormatter;
import java.util.List;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.Delivery;
import org.johan.tracker.cargotracker.domain.model.cargo.HandlingActivity;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.interfaces.Coordinates;
import org.johan.tracker.cargotracker.interfaces.CoordinatesFactory;

public record CargoTrackingViewAdapter(
    String trackingId,
    String originName,
    String originCode,
    Coordinates originCoordinates,
    String destinationName,
    String destinationCode,
    Coordinates destinationCoordinates,
    String lastKnownLocationName,
    String lastKnownLocationCode,
    Coordinates lastKnownLocationCoordinates,
    String statusCode,
    String statusText,
    boolean isMisdirected,
    String nextExpectedActivity,
    List<HandlingEventViewAdapter> events) {

  public static CargoTrackingViewAdapter from(Cargo cargo, List<HandlingEvent> handlingEvents) {
    return new CargoTrackingViewAdapter(
        cargo.getTrackingId().id(),
        cargo.getRouteSpecification().origin().name(),
        cargo.getRouteSpecification().origin().unLocode().code(),
        CoordinatesFactory.find(cargo.getRouteSpecification().origin()),
        cargo.getRouteSpecification().destination().name(),
        cargo.getRouteSpecification().destination().unLocode().code(),
        CoordinatesFactory.find(cargo.getRouteSpecification().destination()),
        cargo.getDelivery().getLastKnownLocation().unLocode().code().equals("XXXXX")
            ? "Unknown"
            : cargo.getDelivery().getLastKnownLocation().name(),
        cargo.getDelivery().getLastKnownLocation().unLocode().code(),
        CoordinatesFactory.find(cargo.getDelivery().getLastKnownLocation()),
        getStatusCode(cargo),
        getStatusText(cargo),
        cargo.getDelivery().isMisdirected(),
        getNextExpectedActivity(cargo),
        handlingEvents.stream()
            .map(handlingEvent -> HandlingEventViewAdapter.from(cargo, handlingEvent))
            .toList());
  }

  private static String getStatusCode(Cargo cargo) {
    if (cargo.getItinerary().legs().isEmpty()) {
      return "NOT_ROUTED";
    }

    if (cargo.getDelivery().isUnloadedAtDestination()) {
      return "AT_DESTINATION";
    }

    if (cargo.getDelivery().isMisdirected()) {
      return "MISDIRECTED";
    }

    return cargo.getDelivery().getTransportStatus().name();
  }

  private static String getStatusText(Cargo cargo) {
    Delivery delivery = cargo.getDelivery();

    return switch (delivery.getTransportStatus()) {
      case IN_PORT -> "In port " + cargo.getRouteSpecification().destination().name();
      case ONBOARD_CARRIER ->
          "Onboard voyage " + delivery.getCurrentVoyage().voyageNumber().number();
      case CLAIMED -> "Claimed";
      case NOT_RECEIVED -> "Not received";
      case UNKNOWN -> "Unknown";
      case null -> "[Unknown status]"; // Should never happen.
    };
  }

  private static String getNextExpectedActivity(Cargo cargo) {
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

  public record HandlingEventViewAdapter(String time, boolean isExpected, String description) {

    public static HandlingEventViewAdapter from(Cargo cargo, HandlingEvent handlingEvent) {
      return new HandlingEventViewAdapter(
          handlingEvent.completionTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
          cargo.getItinerary().isExpected(handlingEvent),
          getDescription(handlingEvent));
    }

    private static String getDescription(HandlingEvent handlingEvent) {
      return switch (handlingEvent.type()) {
        case LOAD ->
            "Loaded onto voyage "
                + handlingEvent.voyage().voyageNumber().number()
                + " in "
                + handlingEvent.location().name();
        case UNLOAD ->
            "Unloaded off voyage "
                + handlingEvent.voyage().voyageNumber().number()
                + " in "
                + handlingEvent.location().name();
        case RECEIVE -> "Received in " + handlingEvent.location().name();
        case CLAIM -> "Claimed in " + handlingEvent.location().name();
        case CUSTOMS -> "Cleared customs in " + handlingEvent.location().name();
        case null -> "[Unknown]";
      };
    }
  }
}
