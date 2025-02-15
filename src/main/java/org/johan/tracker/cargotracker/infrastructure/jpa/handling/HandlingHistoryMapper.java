package org.johan.tracker.cargotracker.infrastructure.jpa.handling;

import jakarta.inject.Singleton;
import java.util.List;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.handling.HandlingHistory;

@Singleton
@RequiredArgsConstructor
public class HandlingHistoryMapper implements Function<List<HandlingEventEntity>, HandlingHistory> {

  private final HandlingEventMapper handlingEventMapper;

  @Override
  public HandlingHistory apply(List<HandlingEventEntity> handlingEvents) {
    return new HandlingHistory(handlingEvents.stream().map(handlingEventMapper).toList());
  }
}
