package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Leg;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LegDto;

import java.util.function.Function;

@Singleton
@RequiredArgsConstructor
public class LegDtoAssembler implements Function<Leg, LegDto> {

  private final LocationDtoAssembler locationDtoAssembler;

  @Override
  public LegDto apply(Leg leg) {
    return new LegDto(
        leg.voyage().voyageNumber().number(),
        locationDtoAssembler.apply(leg.loadLocation()),
        locationDtoAssembler.apply(leg.unloadLocation()),
        leg.loadTime(),
        leg.unloadTime());
  }
}
