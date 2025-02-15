package org.johan.tracker.cargotracker.interfaces.booking.facade.internal.assembler;

import jakarta.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.johan.tracker.cargotracker.domain.model.cargo.Itinerary;
import org.johan.tracker.cargotracker.domain.model.cargo.Leg;
import org.johan.tracker.cargotracker.domain.model.location.Location;
import org.johan.tracker.cargotracker.domain.model.location.LocationRepository;
import org.johan.tracker.cargotracker.domain.model.location.UnLocode;
import org.johan.tracker.cargotracker.domain.model.voyage.Voyage;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageNumber;
import org.johan.tracker.cargotracker.domain.model.voyage.VoyageRepository;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.LegDto;
import org.johan.tracker.cargotracker.interfaces.booking.facade.dto.RouteCandidate;

@Singleton
@RequiredArgsConstructor
public class ItineraryCandidateDtoAssembler {

  private final LegDtoAssembler legDtoAssembler;

  public RouteCandidate apply(Itinerary itinerary) {
    List<LegDto> legs = itinerary.legs().stream().map(legDtoAssembler).toList();
    return new RouteCandidate(legs);
  }

  public Itinerary apply(
      RouteCandidate routeCandidate,
      VoyageRepository voyageRepository,
      LocationRepository locationRepository) {
    List<Leg> legs = new ArrayList<>(routeCandidate.legs().size());

    for (LegDto legDto : routeCandidate.legs()) {
      VoyageNumber voyageNumber = new VoyageNumber(legDto.voyageNumber());
      Voyage voyage = voyageRepository.find(voyageNumber);
      Location from = locationRepository.find(new UnLocode(legDto.from().unLocode()));
      Location to = locationRepository.find(new UnLocode(legDto.to().unLocode()));

      legs.add(new Leg(voyage, from, to, legDto.loadTime(), legDto.unloadTime()));
    }
    return new Itinerary(legs);
  }
}
