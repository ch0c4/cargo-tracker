package org.johan.tracker.cargotracker.interfaces.booking.sse;

import java.util.EnumMap;
import java.util.Map;
import org.johan.tracker.cargotracker.domain.model.cargo.Cargo;
import org.johan.tracker.cargotracker.domain.model.cargo.RoutingStatus;
import org.johan.tracker.cargotracker.domain.model.cargo.TransportStatus;

public record RealtimeCargoTrackingViewAdapter(
    String trackingId,
    String routingStatus,
    boolean isMisdirected,
    String transportStatus,
    boolean isAtDestination,
    LocationViewAdapter origin,
    LocationViewAdapter lastKnownLocation,
    LocationViewAdapter location,
    String statusCode) {

  private static final Map<RoutingStatus, String> routingStatusLabels =
      new EnumMap<>(RoutingStatus.class);
  private static final Map<TransportStatus, String> transportStatusLabels =
      new EnumMap<>(TransportStatus.class);

  static {
    routingStatusLabels.put(RoutingStatus.NOT_ROUTED, "Not routed");
    routingStatusLabels.put(RoutingStatus.ROUTED, "Routed");
    routingStatusLabels.put(RoutingStatus.MISROUTED, "Misrouted");

    transportStatusLabels.put(TransportStatus.NOT_RECEIVED, "Not received");
    transportStatusLabels.put(TransportStatus.IN_PORT, "In port");
    transportStatusLabels.put(TransportStatus.ONBOARD_CARRIER, "Onboard carrier");
    transportStatusLabels.put(TransportStatus.CLAIMED, "Claimed");
    transportStatusLabels.put(TransportStatus.UNKNOWN, "Unknown");
  }

  public static RealtimeCargoTrackingViewAdapter from(Cargo cargo) {
    return new RealtimeCargoTrackingViewAdapter(
        cargo.getTrackingId().id(),
        routingStatusLabels.get(cargo.getDelivery().getRoutingStatus()),
        cargo.getDelivery().isMisdirected(),
        transportStatusLabels.get(cargo.getDelivery().getTransportStatus()),
        cargo.getDelivery().isUnloadedAtDestination(),
        getOrigin(cargo),
        getLastKnownLocation(cargo),
        cargo.getDelivery().getTransportStatus().equals(TransportStatus.NOT_RECEIVED)
            ? getOrigin(cargo)
            : getLastKnownLocation(cargo),
        getStatusCode(cargo));
  }

  private static LocationViewAdapter getOrigin(Cargo cargo) {
    return LocationViewAdapter.from(cargo.getOrigin());
  }

  private static LocationViewAdapter getLastKnownLocation(Cargo cargo) {
    return LocationViewAdapter.from(cargo.getDelivery().getLastKnownLocation());
  }

  private static String getStatusCode(Cargo cargo) {
    RoutingStatus routingStatus = cargo.getDelivery().getRoutingStatus();

    if (routingStatus == RoutingStatus.NOT_ROUTED || routingStatus == RoutingStatus.MISROUTED) {
      return routingStatus.toString();
    }

    if (cargo.getDelivery().isMisdirected()) {
      return "MISDIRECTED";
    }

    if (cargo.getDelivery().isUnloadedAtDestination()) {
      return "AT_DESTINATION";
    }

    return cargo.getDelivery().getTransportStatus().toString();
  }
}
