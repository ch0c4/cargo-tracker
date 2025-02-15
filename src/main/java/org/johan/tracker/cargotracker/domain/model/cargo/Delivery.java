package org.johan.tracker.cargotracker.domain.model.cargo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingEvent;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingHistory;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;

public class Delivery {

  private TransportStatus transportStatus;

  private Location lastKnownLocation;

  private final Voyage currentVoyage;

  private boolean misdirected;

  private final LocalDateTime eta;

  private final HandlingActivity nextExpectedActivity;

  private boolean isUnloadedAtDestination;

  private RoutingStatus routingStatus;

  private LocalDateTime calculatedAt;

  private final HandlingEvent lastEvent;

  public Delivery(
      HandlingEvent lastEvent, Itinerary itinerary, RouteSpecification routeSpecification) {
    this.calculatedAt = LocalDateTime.now();
    this.lastEvent = lastEvent;

    this.misdirected = calculateMisdirectionStatus(itinerary);
    this.routingStatus = calculateRoutingStatus(itinerary, routeSpecification);
    this.transportStatus = calculateTransportStatus();
    this.lastKnownLocation = calculateLastKnownLocation();
    this.currentVoyage = calculateCurrentVoyage();
    this.eta = calculateEta(itinerary);
    this.nextExpectedActivity = calculateNextExpectedActivity(routeSpecification, itinerary);
    this.isUnloadedAtDestination = calculateUnloadedAtDestination(routeSpecification);
  }

  public static Delivery derivedFrom(
      RouteSpecification routeSpecification, Itinerary itinerary, HandlingHistory handlingHistory) {
    HandlingEvent lastEvent = handlingHistory.getMostRecentlyCompletedEvent();

    return new Delivery(lastEvent, itinerary, routeSpecification);
  }

  public Delivery updateOnRouting(RouteSpecification routeSpecification, Itinerary itinerary) {
    return new Delivery(lastEvent, itinerary, routeSpecification);
  }

  private boolean calculateUnloadedAtDestination(RouteSpecification routeSpecification) {
    return lastEvent != null
        && HandlingEvent.Type.UNLOAD.equals(lastEvent.type())
        && routeSpecification.destination().equals(lastEvent.location());
  }

  private HandlingActivity calculateNextExpectedActivity(
      RouteSpecification routeSpecification, Itinerary itinerary) {
    if (!onTrack()) {
      return new HandlingActivity(null, null, null);
    }

    if (lastEvent == null) {
      return new HandlingActivity(HandlingEvent.Type.RECEIVE, routeSpecification.origin(), null);
    }

    return switch (lastEvent.type()) {
      case LOAD -> {
        for (Leg leg : itinerary.legs()) {
          if (leg.loadLocation().equals(lastEvent.location())) {
            yield new HandlingActivity(
                HandlingEvent.Type.UNLOAD, leg.unloadLocation(), leg.voyage());
          }
        }
        yield new HandlingActivity(null, null, null);
      }
      case UNLOAD -> {
        for (Iterator<Leg> iterator = itinerary.legs().iterator(); iterator.hasNext(); ) {
          Leg leg = iterator.next();
          if (leg.unloadLocation().equals(lastEvent.location())) {
            if (iterator.hasNext()) {
              Leg nextLeg = iterator.next();
              yield new HandlingActivity(
                  HandlingEvent.Type.LOAD, nextLeg.loadLocation(), nextLeg.voyage());
            } else {
              yield new HandlingActivity(HandlingEvent.Type.CLAIM, leg.unloadLocation(), null);
            }
          }
        }
        yield new HandlingActivity(null, null, null);
      }
      case RECEIVE ->
          new HandlingActivity(
              HandlingEvent.Type.RECEIVE,
              itinerary.legs().getFirst().loadLocation(),
              itinerary.legs().getFirst().voyage());
      default -> new HandlingActivity(null, null, null);
    };
  }

  private LocalDateTime calculateEta(Itinerary itinerary) {
    if (onTrack()) {
      return itinerary.getFinalArrivalDate();
    }
    return null;
  }

  private boolean onTrack() {
    return routingStatus.equals(RoutingStatus.ROUTED) && !misdirected;
  }

  private Voyage calculateCurrentVoyage() {
    if (this.transportStatus.equals(TransportStatus.ONBOARD_CARRIER) && lastEvent != null) {
      return lastEvent.voyage();
    }
    return null;
  }

  private Location calculateLastKnownLocation() {
    if (lastEvent != null) {
      return lastEvent.location();
    }
    return null;
  }

  private TransportStatus calculateTransportStatus() {
    if (lastEvent == null) {
      return TransportStatus.NOT_RECEIVED;
    }

    return switch (lastEvent.type()) {
      case LOAD -> TransportStatus.ONBOARD_CARRIER;
      case UNLOAD, RECEIVE, CUSTOMS -> TransportStatus.IN_PORT;
      case CLAIM -> TransportStatus.CLAIMED;
      case null -> TransportStatus.UNKNOWN;
    };
  }

  private RoutingStatus calculateRoutingStatus(
      Itinerary itinerary, RouteSpecification routeSpecification) {
    if (itinerary == null || itinerary.equals(new Itinerary(new ArrayList<>()))) {
      return RoutingStatus.NOT_ROUTED;
    } else {
      if (routeSpecification.isSatisfiedBy(itinerary)) {
        return RoutingStatus.ROUTED;
      }
      return RoutingStatus.MISROUTED;
    }
  }

  private boolean calculateMisdirectionStatus(Itinerary itinerary) {
    if (lastEvent == null) {
      return false;
    }
    return !itinerary.isExpected(lastEvent);
  }

  public TransportStatus getTransportStatus() {
    return transportStatus;
  }

  public void setTransportStatus(TransportStatus transportStatus) {
    this.transportStatus = transportStatus;
  }

  public Location getLastKnownLocation() {
    return lastKnownLocation;
  }

  public void setLastKnownLocation(Location lastKnownLocation) {
    this.lastKnownLocation = lastKnownLocation;
  }

  public Voyage getCurrentVoyage() {
    return currentVoyage == null ? new Voyage(null, null) : currentVoyage;
  }

  public boolean isMisdirected() {
    return misdirected;
  }

  public void setMisdirected(boolean misdirected) {
    this.misdirected = misdirected;
  }

  public LocalDateTime getEstimatedTimeOfArrival() {
    return eta;
  }

  public HandlingActivity getNextExpectedActivity() {
    return nextExpectedActivity;
  }

  public boolean isUnloadedAtDestination() {
    return isUnloadedAtDestination;
  }

  public void setUnloadedAtDestination(boolean unloadedAtDestination) {
    isUnloadedAtDestination = unloadedAtDestination;
  }

  public RoutingStatus getRoutingStatus() {
    return routingStatus;
  }

  public void setRoutingStatus(RoutingStatus routingStatus) {
    this.routingStatus = routingStatus;
  }

  public LocalDateTime getCalculatedAt() {
    return calculatedAt;
  }

  public void setCalculatedAt(LocalDateTime calculatedAt) {
    this.calculatedAt = calculatedAt;
  }
}
