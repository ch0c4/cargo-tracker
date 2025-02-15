package org.johan.tracker.cargotracker.domain.model.cargo;

import java.util.ArrayList;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingHistory;
import org.johan.tracker.cargotracker.domain.model.location.Location;

public class Cargo {
  private final TrackingId trackingId;
  private Location origin;
  private RouteSpecification routeSpecification;
  private Itinerary itinerary;
  private Delivery delivery;

  public Cargo(TrackingId trackingId, RouteSpecification routeSpecification) {
    this.trackingId = trackingId;
    this.origin = routeSpecification.origin();
    this.routeSpecification = routeSpecification;
    this.itinerary = new Itinerary(new ArrayList<>());
    this.delivery =
        Delivery.derivedFrom(
            routeSpecification, this.itinerary, new HandlingHistory(new ArrayList<>()));
  }

  public void specifyNewRoute(RouteSpecification routeSpecification) {
    this.routeSpecification = routeSpecification;
    this.delivery = delivery.updateOnRouting(routeSpecification, this.itinerary);
  }

  public void assignToRoute(Itinerary itinerary) {
    this.itinerary = itinerary;
    this.delivery = delivery.updateOnRouting(routeSpecification, this.itinerary);
  }

  public void deriveDeliveryProgress(HandlingHistory handlingHistory) {
    this.delivery = Delivery.derivedFrom(routeSpecification, this.itinerary, handlingHistory);
  }

  public TrackingId getTrackingId() {
    return trackingId;
  }

  public Location getOrigin() {
    return origin;
  }

  public void setOrigin(Location origin) {
    this.origin = origin;
  }

  public RouteSpecification getRouteSpecification() {
    return routeSpecification;
  }

  public Itinerary getItinerary() {
    return itinerary;
  }

  public Delivery getDelivery() {
    return delivery;
  }
}
