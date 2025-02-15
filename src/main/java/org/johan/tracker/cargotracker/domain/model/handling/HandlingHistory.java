package org.johan.tracker.cargotracker.domain.model.handling;

import java.util.*;

public record HandlingHistory(List<HandlingEvent> handlingEvents) {

  private static final Comparator<HandlingEvent> BY_COMPLETION_TIME_COMPARATOR =
      Comparator.comparing(HandlingEvent::completionTime);

  public HandlingEvent getMostRecentlyCompletedEvent() {
    List<HandlingEvent> distinctEvents = getDistinctEventsByCompletionTime();
    if (distinctEvents.isEmpty()) {
      return null;
    }
    return distinctEvents.getLast();
  }

  public List<HandlingEvent> getDistinctEventsByCompletionTime() {
    List<HandlingEvent> ordered = new ArrayList<>(new HashSet<>(handlingEvents));
    ordered.sort(BY_COMPLETION_TIME_COMPARATOR);

    return Collections.unmodifiableList(ordered);
  }
}
